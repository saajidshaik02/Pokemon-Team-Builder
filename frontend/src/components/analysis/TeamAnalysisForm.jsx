function TeamAnalysisForm({
  value,
  isSubmitting,
  validationMessage,
  onChange,
  onSubmit,
}) {
  return (
    <form className="team-analysis-form" onSubmit={onSubmit} noValidate>
      <label className="pokemon-search-form__label" htmlFor="team-analysis-input">
        Enter 1 to 6 Pokemon names
      </label>
      <textarea
        id="team-analysis-input"
        name="teamInput"
        value={value}
        onChange={onChange}
        className="team-analysis-form__textarea"
        placeholder={'pikachu, charizard, blastoise\nor one name per line'}
        aria-invalid={validationMessage ? 'true' : 'false'}
        aria-describedby={validationMessage ? 'team-analysis-error' : undefined}
      />
      <div className="team-analysis-form__actions">
        <button type="submit" className="pokemon-search-form__button" disabled={isSubmitting}>
          {isSubmitting ? 'Analyzing...' : 'Analyze Team'}
        </button>
        <p className="pokemon-search-form__hint">
          Use commas or line breaks to compare different combinations quickly.
        </p>
      </div>
      {validationMessage ? (
        <p id="team-analysis-error" className="pokemon-search-form__error" role="alert">
          {validationMessage}
        </p>
      ) : null}
    </form>
  )
}

export default TeamAnalysisForm
