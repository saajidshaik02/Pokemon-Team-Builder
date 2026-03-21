function PokemonSearchForm({
  name,
  isSubmitting,
  validationMessage,
  onNameChange,
  onSubmit,
}) {
  return (
    <form className="pokemon-search-form" onSubmit={onSubmit} noValidate>
      <label className="pokemon-search-form__label" htmlFor="pokemon-name">
        Search Pokemon by name
      </label>
      <div className="pokemon-search-form__controls">
        <input
          id="pokemon-name"
          name="name"
          type="text"
          value={name}
          onChange={onNameChange}
          placeholder="Try pikachu or charizard"
          className="pokemon-search-form__input"
          autoComplete="off"
          aria-invalid={validationMessage ? 'true' : 'false'}
          aria-describedby={validationMessage ? 'pokemon-search-error' : undefined}
        />
        <button type="submit" className="pokemon-search-form__button" disabled={isSubmitting}>
          {isSubmitting ? 'Searching...' : 'Search'}
        </button>
      </div>
      {validationMessage ? (
        <p id="pokemon-search-error" className="pokemon-search-form__error" role="alert">
          {validationMessage}
        </p>
      ) : (
        <p className="pokemon-search-form__hint">
          The frontend calls the Spring Boot backend, which normalizes the name before lookup.
        </p>
      )}
    </form>
  )
}

export default PokemonSearchForm
