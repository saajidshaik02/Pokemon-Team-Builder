function AddPokemonForm({
  name,
  isSubmitting,
  onNameChange,
  onSubmit,
}) {
  return (
    <form className="pokemon-search-form team-builder-form" onSubmit={onSubmit} noValidate>
      <label className="pokemon-search-form__label" htmlFor="team-pokemon-name">
        Add Pokemon to your team
      </label>
      <div className="pokemon-search-form__controls">
        <input
          id="team-pokemon-name"
          name="name"
          type="text"
          value={name}
          onChange={onNameChange}
          placeholder="Add a Pokemon like garchomp"
          className="pokemon-search-form__input"
          autoComplete="off"
        />
        <button type="submit" className="pokemon-search-form__button" disabled={isSubmitting}>
          {isSubmitting ? 'Adding...' : 'Add Pokemon'}
        </button>
      </div>
      <p className="pokemon-search-form__hint">
        Team slots use sprite-first rendering to stay compact. Official artwork is still kept in
        state for later analysis views.
      </p>
    </form>
  )
}

export default AddPokemonForm
