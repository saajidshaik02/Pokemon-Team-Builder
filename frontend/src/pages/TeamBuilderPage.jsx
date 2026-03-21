import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import ErrorNotice from '../components/common/ErrorNotice.jsx'
import AddPokemonForm from '../components/team/AddPokemonForm.jsx'
import TeamSlotsGrid from '../components/team/TeamSlotsGrid.jsx'
import TeamValidationMessage from '../components/team/TeamValidationMessage.jsx'
import { getPokemonByName } from '../api/index.js'
import useApi from '../hooks/useApi.js'
import useFormState from '../hooks/useFormState.js'

const MAX_TEAM_SIZE = 6

function TeamBuilderPage() {
  const navigate = useNavigate()
  const { values, updateValue, resetValues } = useFormState({ name: '' })
  const [team, setTeam] = useState([])
  const [validationMessage, setValidationMessage] = useState('')
  const [validationTone, setValidationTone] = useState('warning')
  const pokemonLookup = useApi(getPokemonByName)

  async function handleAddPokemon(event) {
    event.preventDefault()

    const trimmedName = values.name.trim()
    const normalizedName = trimmedName.toLowerCase()
    pokemonLookup.setError(null)

    if (!trimmedName) {
      setValidationTone('warning')
      setValidationMessage('Enter a Pokemon name before adding it to the team.')
      return
    }

    if (team.length >= MAX_TEAM_SIZE) {
      setValidationTone('warning')
      setValidationMessage('Your team already has 6 Pokemon. Remove one before adding another.')
      return
    }

    if (team.some((pokemon) => pokemon.name === normalizedName)) {
      setValidationTone('error')
      setValidationMessage('Duplicate Pokemon are not allowed in the current backend implementation.')
      return
    }

    setValidationMessage('')

    try {
      const pokemon = await pokemonLookup.execute(trimmedName)
      setTeam((currentTeam) => [
        ...currentTeam,
        {
          id: pokemon.id,
          name: pokemon.name,
          types: pokemon.types,
          spriteUrl: pokemon.spriteUrl,
          officialArtworkUrl: pokemon.officialArtworkUrl,
        },
      ])
      setValidationTone('success')
      setValidationMessage(`${pokemon.name} added to the team.`)
      resetValues({ name: '' })
    } catch {
      // Error state is surfaced through the shared error component below.
    }
  }

  function handleNameChange(event) {
    if (validationMessage) {
      setValidationMessage('')
    }
    pokemonLookup.setError(null)
    updateValue(event)
  }

  function handleRemovePokemon(indexToRemove) {
    setTeam((currentTeam) => currentTeam.filter((_, index) => index !== indexToRemove))
    pokemonLookup.setError(null)
    setValidationTone('success')
    setValidationMessage('Pokemon removed from the team.')
  }

  function handleAnalysisAction() {
    navigate('/team-analysis', {
      state: {
        team,
      },
    })
  }

  return (
    <section className="page-grid page-grid--team-builder">
      <div className="section-card hero-card team-builder-hero">
        <p className="eyebrow">Team Builder</p>
        <h2>Team Builder</h2>
        <p>
          Build a six-slot lineup, test different cores, and shape the order you want to carry
          into team analysis.
        </p>
        <AddPokemonForm
          name={values.name}
          isSubmitting={pokemonLookup.isLoading}
          onNameChange={handleNameChange}
          onSubmit={handleAddPokemon}
        />
        <TeamValidationMessage message={validationMessage} tone={validationTone} />
        {pokemonLookup.error ? (
          <ErrorNotice title="Could not add Pokemon" message={pokemonLookup.error.message} />
        ) : null}
      </div>

      <div className="section-card">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Sprite-First Team Slots</p>
            <h3>Current Team Layout</h3>
          </div>
          <span className="pill-muted">
            {team.length} / {MAX_TEAM_SIZE} Pokemon
          </span>
        </div>

        <p className="team-builder-summary">
          Keep the order you want to analyze and adjust the lineup until the mix feels right.
        </p>
        {team.length === 0 ? (
          <div className="empty-state-card empty-state-card--team-builder">
            <p className="eyebrow">No Team Members Yet</p>
            <h4>All six slots are open.</h4>
            <p>
              Start with a core Pokemon and build outward. The fixed six-slot grid stays visible so
              team shape is always easy to scan.
            </p>
          </div>
        ) : null}
        <TeamSlotsGrid team={team} onRemovePokemon={handleRemovePokemon} />
      </div>

      <div className="section-card team-builder-action-card">
        <div>
          <p className="eyebrow">Next Step</p>
          <h3>Analyze Team</h3>
          <p>
            Take this lineup straight into analysis to check shared weaknesses, role balance, and
            overall stat pressure.
          </p>
        </div>
        <button
          type="button"
          className="team-builder-action"
          disabled={team.length === 0}
          onClick={handleAnalysisAction}
        >
          Analyze Team
        </button>
      </div>
    </section>
  )
}

export default TeamBuilderPage
