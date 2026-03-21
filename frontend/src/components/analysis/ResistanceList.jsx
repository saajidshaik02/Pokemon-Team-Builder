function ResistanceList({ resistances }) {
  if (resistances.length === 0) {
    return <p className="analysis-empty-copy">No grouped resistances were returned.</p>
  }

  return (
    <div className="analysis-list">
      {resistances.map((coverage) => (
        <article className="analysis-list-card" key={coverage.type}>
          <div className="analysis-list-card__header">
            <strong className="analysis-list-card__title">{coverage.type}</strong>
          </div>
          <p className="analysis-list-card__copy">{coverage.pokemon.join(', ')}</p>
        </article>
      ))}
    </div>
  )
}

export default ResistanceList
