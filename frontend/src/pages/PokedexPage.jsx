import { useState } from 'react'
import ErrorNotice from '../components/common/ErrorNotice.jsx'
import { getPokemonByName } from '../api/index.js'
import { resolvePokemonImage } from '../api/imageUtils.js'
import ImageLoader from '../components/common/ImageLoader.jsx'
import LoadingState from '../components/common/LoadingState.jsx'
import PokemonCard from '../components/pokemon/PokemonCard.jsx'
import PokemonSearchForm from '../components/pokemon/PokemonSearchForm.jsx'
import TypeBadge from '../components/pokemon/TypeBadge.jsx'
import useApi from '../hooks/useApi.js'
import useFormState from '../hooks/useFormState.js'

function PokedexPage() {
  const { values, updateValue } = useFormState({ name: '' })
  const [validationMessage, setValidationMessage] = useState('')
  const [hasSearched, setHasSearched] = useState(false)
  const [lastSubmittedName, setLastSubmittedName] = useState('')
  const pokemonLookup = useApi(getPokemonByName, { ignoreStaleResponses: true })

  async function handleSubmit(event) {
    event.preventDefault()

    const trimmedName = values.name.trim()
    setHasSearched(true)

    if (!trimmedName) {
      pokemonLookup.setData(null)
      pokemonLookup.setError(null)
      setValidationMessage('Enter a Pokemon name before searching.')
      return
    }

    setValidationMessage('')
    setLastSubmittedName(trimmedName)

    try {
      await pokemonLookup.execute(trimmedName)
    } catch (apiError) {
      if (apiError?.isStale) {
        return
      }

      pokemonLookup.setData(null)
    }
  }

  function handleNameChange(event) {
    if (validationMessage) {
      setValidationMessage('')
    }
    pokemonLookup.setError(null)
    updateValue(event)
  }

  const pokemon = pokemonLookup.data
  const quickPreview = pokemon ? resolvePokemonImage(pokemon, 'sprite-first') : null
  const isValidationError = validationMessage.length > 0
  const isNotFoundError = pokemonLookup.error?.status === 404

  return (
    <section className="page-grid page-grid--pokedex">
      <div className="section-card hero-card pokedex-hero">
        <p className="eyebrow">Pokedex</p>
        <h2>Pokedex</h2>
        <p>
          Look up one Pokemon at a time, scan the core details quickly, and open a cleaner profile
          view with typing, abilities, and base stats.
        </p>
        <PokemonSearchForm
          name={values.name}
          isSubmitting={pokemonLookup.isLoading}
          validationMessage={validationMessage}
          onNameChange={handleNameChange}
          onSubmit={handleSubmit}
        />
      </div>

      {pokemonLookup.isLoading ? (
        <LoadingState label="Looking up Pokemon details through the backend..." />
      ) : null}

      {!pokemonLookup.isLoading && isValidationError ? (
        <ErrorNotice title="Invalid Pokemon lookup" message={validationMessage} />
      ) : null}

      {!pokemonLookup.isLoading && isNotFoundError ? (
        <div className="section-card empty-state-card empty-state-card--search">
          <p className="eyebrow">No Search Result</p>
          <h3>No Pokemon matched "{lastSubmittedName || values.name.trim()}"</h3>
          <p>
            Try a species name like <code>pikachu</code>, <code>gengar</code>, or{' '}
            <code>dragonite</code>. Check the spelling and try another Pokemon.
          </p>
        </div>
      ) : null}

      {!pokemonLookup.isLoading && pokemonLookup.error && !isValidationError && !isNotFoundError ? (
        <ErrorNotice title="Pokemon lookup failed" message={pokemonLookup.error.message} />
      ) : null}

      {!pokemonLookup.isLoading && pokemon ? (
        <>
          <div className="section-card split-card pokemon-preview-card">
            <div className="pokemon-preview-card__media">
              <p className="eyebrow">Sprite-First</p>
              <h3>Quick Lookup Preview</h3>
              <ImageLoader {...quickPreview} className="pokemon-preview-image" />
            </div>
            <div className="pokemon-preview-card__body">
              <p className="eyebrow">Live Result</p>
              <h3>{pokemon.name}</h3>
              <p>
                Get a quick first look here, then scroll down for a fuller profile with abilities,
                typing, and base stats.
              </p>
              <div className="type-badge-list">
                {pokemon.types.map((type) => (
                  <TypeBadge key={type} type={type} />
                ))}
              </div>
            </div>
          </div>

          <PokemonCard pokemon={pokemon} />
        </>
      ) : null}

      {!pokemonLookup.isLoading && !pokemon && !pokemonLookup.error && !validationMessage ? (
        <div className="section-card empty-state-card pokedex-empty-state">
          <p className="eyebrow">{hasSearched ? 'No Active Result' : 'Empty State'}</p>
          <h3>{hasSearched ? 'Search again with another Pokemon name' : 'Search for any Pokemon'}</h3>
          <p>
            Start with a simple name like <code>pikachu</code>, <code>gengar</code>, or{' '}
            <code>dragonite</code> to see typing, abilities, and base stats in one place.
          </p>
        </div>
      ) : null}
    </section>
  )
}

export default PokedexPage
