function WeaknessList({ weaknesses }) {
  if (weaknesses.length === 0) {
    return <p className="analysis-empty-copy">No major shared weaknesses were returned.</p>
  }

  return (
    <div className="analysis-list">
      {weaknesses.map((weakness) => (
        <article className="analysis-list-card" key={weakness.type}>
          <div className="analysis-list-card__header">
            <strong className="analysis-list-card__title">{weakness.type}</strong>
            <span className="analysis-severity">{weakness.severity}</span>
          </div>
          <p className="analysis-list-card__copy">
            Affected: {weakness.affectedPokemon.join(', ')}
          </p>
          <p className="analysis-list-card__copy">
            Coverage: {weakness.coveringPokemon.length > 0 ? weakness.coveringPokemon.join(', ') : 'none'}
          </p>
        </article>
      ))}
    </div>
  )
}

export default WeaknessList
