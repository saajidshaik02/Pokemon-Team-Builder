import { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { useLocation } from 'react-router-dom'
import { analyzeTeam, getPokemonByName } from '../api/index.js'
import ErrorNotice from '../components/common/ErrorNotice.jsx'
import LoadingState from '../components/common/LoadingState.jsx'
import AnalysisSummaryPanel from '../components/analysis/AnalysisSummaryPanel.jsx'
import RecommendationsSection from '../components/analysis/RecommendationsSection.jsx'
import RoleAnalysisSection from '../components/analysis/RoleAnalysisSection.jsx'
import StatSummarySection from '../components/analysis/StatSummarySection.jsx'
import TeamAnalysisForm from '../components/analysis/TeamAnalysisForm.jsx'
import TypeAnalysisSection from '../components/analysis/TypeAnalysisSection.jsx'
import useApi from '../hooks/useApi.js'
import useFormState from '../hooks/useFormState.js'

const MAX_TEAM_SIZE = 6

function parsePokemonNames(teamInput) {
  return teamInput
    .split(/[\n,]+/)
    .map((entry) => entry.trim())
    .filter(Boolean)
}

function buildTeamVisualsMap(teamEntries) {
  return Object.fromEntries(
    (teamEntries ?? []).map((pokemon) => [
      pokemon.name,
      {
        name: pokemon.name,
        types: pokemon.types ?? [],
        spriteUrl: pokemon.spriteUrl ?? '',
        officialArtworkUrl: pokemon.officialArtworkUrl ?? '',
      },
    ]),
  )
}

async function resolveTeamVisuals(teamEntries, cachedTeam) {
  const cachedByName = new Map(
    (cachedTeam ?? []).map((pokemon) => [pokemon.name, pokemon]),
  )
  const missingNames = teamEntries
    .map((pokemon) => pokemon.name)
    .filter((name) => !cachedByName.has(name))

  if (missingNames.length > 0) {
    const fetchedPokemon = await Promise.all(missingNames.map((name) => getPokemonByName(name)))
    for (const pokemon of fetchedPokemon) {
      cachedByName.set(pokemon.name, pokemon)
    }
  }

  return buildTeamVisualsMap(
    teamEntries.map((pokemon) => {
      const teamVisual = cachedByName.get(pokemon.name)
      return {
        name: pokemon.name,
        types: teamVisual?.types ?? pokemon.types ?? [],
        spriteUrl: teamVisual?.spriteUrl ?? '',
        officialArtworkUrl: teamVisual?.officialArtworkUrl ?? '',
      }
    }),
  )
}

function TeamAnalysisPage() {
  const location = useLocation()
  const initialRouteTeam = useMemo(() => location.state?.team ?? [], [location.state])
  const initialInput = useMemo(
    () => initialRouteTeam.map((pokemon) => pokemon.name).join(', '),
    [initialRouteTeam],
  )
  const { values, updateValue, setValues } = useFormState({ teamInput: initialInput })
  const [validationMessage, setValidationMessage] = useState('')
  const [teamVisualsByName, setTeamVisualsByName] = useState(() => buildTeamVisualsMap(initialRouteTeam))
  const [hasAttemptedAnalysis, setHasAttemptedAnalysis] = useState(initialRouteTeam.length > 0)
  const [isResolvingSummary, setIsResolvingSummary] = useState(false)
  const analysisRequest = useApi(analyzeTeam)
  const autoSubmittedLocationKey = useRef(null)

  const submitTeamAnalysis = useCallback(async (teamInput, cachedTeam = []) => {
    const pokemonNames = parsePokemonNames(teamInput)
    setHasAttemptedAnalysis(true)

    if (pokemonNames.length === 0) {
      analysisRequest.setData(null)
      analysisRequest.setError(null)
      setTeamVisualsByName({})
      setValidationMessage('Enter at least one Pokemon name before analyzing the team.')
      return
    }

    if (pokemonNames.length > MAX_TEAM_SIZE) {
      analysisRequest.setData(null)
      analysisRequest.setError(null)
      setTeamVisualsByName({})
      setValidationMessage('Enter no more than 6 Pokemon names for a single team analysis.')
      return
    }

    setValidationMessage('')

    try {
      const analysis = await analysisRequest.execute(pokemonNames)
      setIsResolvingSummary(true)
      const resolvedTeamVisuals = await resolveTeamVisuals(analysis.team, cachedTeam)
      setTeamVisualsByName(resolvedTeamVisuals)
    } catch {
      setTeamVisualsByName({})
    } finally {
      setIsResolvingSummary(false)
    }
  }, [analysisRequest])

  async function handleSubmit(event) {
    event.preventDefault()
    await submitTeamAnalysis(values.teamInput, Object.values(teamVisualsByName))
  }

  function handleTeamInputChange(event) {
    if (validationMessage) {
      setValidationMessage('')
    }
    analysisRequest.setError(null)
    updateValue(event)
  }

  useEffect(() => {
    if (initialRouteTeam.length === 0 || autoSubmittedLocationKey.current === location.key) {
      return
    }

    autoSubmittedLocationKey.current = location.key
    const routeInput = initialRouteTeam.map((pokemon) => pokemon.name).join(', ')
    setValues({ teamInput: routeInput })
    setTeamVisualsByName(buildTeamVisualsMap(initialRouteTeam))
    void submitTeamAnalysis(routeInput, initialRouteTeam)
  }, [initialRouteTeam, location.key, setValues, submitTeamAnalysis])

  const isLoading = analysisRequest.isLoading || isResolvingSummary
  const summarizedTeam = useMemo(
    () =>
      (analysisRequest.data?.team ?? []).map((pokemon) => ({
        name: pokemon.name,
        types: teamVisualsByName[pokemon.name]?.types ?? pokemon.types ?? [],
        spriteUrl: teamVisualsByName[pokemon.name]?.spriteUrl ?? '',
        officialArtworkUrl: teamVisualsByName[pokemon.name]?.officialArtworkUrl ?? '',
      })),
    [analysisRequest.data?.team, teamVisualsByName],
  )

  return (
    <section className="page-grid page-grid--team-analysis">
      <div className="section-card hero-card team-analysis-hero">
        <p className="eyebrow">Team Analysis</p>
        <h2>Team Analysis</h2>
        <p>
          Analyze a team of up to six Pokemon and read the biggest pressure points first, from
          shared weaknesses to role balance and stat spread.
        </p>
        <TeamAnalysisForm
          value={values.teamInput}
          isSubmitting={isLoading}
          validationMessage={validationMessage}
          onChange={handleTeamInputChange}
          onSubmit={handleSubmit}
        />
      </div>

      {isLoading ? <LoadingState label="Running backend team analysis..." /> : null}

      {!isLoading && analysisRequest.error ? (
        <ErrorNotice title="Team analysis failed" message={analysisRequest.error.message} />
      ) : null}

      {!isLoading && analysisRequest.data ? (
        <>
          <AnalysisSummaryPanel team={summarizedTeam} />
          <TypeAnalysisSection typeAnalysis={analysisRequest.data.typeAnalysis} />
          <RoleAnalysisSection roleAnalysis={analysisRequest.data.roleAnalysis} />
          <StatSummarySection statSummary={analysisRequest.data.statSummary} />
          <RecommendationsSection recommendations={analysisRequest.data.recommendations} />
        </>
      ) : null}

      {!isLoading && !analysisRequest.data && !analysisRequest.error && !validationMessage ? (
        <div className="section-card empty-state-card analysis-empty-state">
          <p className="eyebrow">{hasAttemptedAnalysis ? 'No Result' : 'Empty State'}</p>
          <h3>
            {hasAttemptedAnalysis
              ? 'Try another combination of Pokemon names'
              : 'Analyze a team from scratch or from the builder'}
          </h3>
          <p>
            Enter Pokemon names separated by commas or line breaks, or bring a lineup over from
            the Team Builder.
          </p>
        </div>
      ) : null}
    </section>
  )
}

export default TeamAnalysisPage
