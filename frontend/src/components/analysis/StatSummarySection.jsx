function StatSummarySection({ statSummary }) {
  const totalStats = [
    ['HP', statSummary.totalHp],
    ['Attack', statSummary.totalAttack],
    ['Defense', statSummary.totalDefense],
    ['Sp. Atk', statSummary.totalSpecialAttack],
    ['Sp. Def', statSummary.totalSpecialDefense],
    ['Speed', statSummary.totalSpeed],
  ]

  const averageStats = [
    ['HP', statSummary.averageHp],
    ['Attack', statSummary.averageAttack],
    ['Defense', statSummary.averageDefense],
    ['Sp. Atk', statSummary.averageSpecialAttack],
    ['Sp. Def', statSummary.averageSpecialDefense],
    ['Speed', statSummary.averageSpeed],
  ]

  return (
    <section className="section-card analysis-section">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Stat Summary</p>
          <h3>Totals, averages, strengths, and weaknesses</h3>
        </div>
      </div>

      <div className="analysis-three-column">
        <div>
          <h4 className="analysis-section__title">Totals</h4>
          <div className="analysis-stat-grid">
            {totalStats.map(([label, value]) => (
              <div className="analysis-stat-card" key={`total-${label}`}>
                <span>{label}</span>
                <strong>{value}</strong>
              </div>
            ))}
          </div>
        </div>
        <div>
          <h4 className="analysis-section__title">Averages</h4>
          <div className="analysis-stat-grid">
            {averageStats.map(([label, value]) => (
              <div className="analysis-stat-card" key={`average-${label}`}>
                <span>{label}</span>
                <strong>{value}</strong>
              </div>
            ))}
          </div>
        </div>
        <div>
          <h4 className="analysis-section__title">Strengths</h4>
          {statSummary.strengths.length > 0 ? (
            <ul className="analysis-bullet-list">
              {statSummary.strengths.map((entry) => (
                <li key={entry}>{entry}</li>
              ))}
            </ul>
          ) : (
            <p className="analysis-empty-copy">No special strengths were highlighted.</p>
          )}
          <h4 className="analysis-section__title">Weaknesses</h4>
          {statSummary.weaknesses.length > 0 ? (
            <ul className="analysis-bullet-list">
              {statSummary.weaknesses.map((entry) => (
                <li key={entry}>{entry}</li>
              ))}
            </ul>
          ) : (
            <p className="analysis-empty-copy">No extra stat weaknesses were highlighted.</p>
          )}
        </div>
      </div>
    </section>
  )
}

export default StatSummarySection
