function RoleAnalysisSection({ roleAnalysis }) {
  return (
    <section className="section-card analysis-section">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Role Analysis</p>
          <h3>Role labels and team balance</h3>
        </div>
      </div>

      <div className="analysis-two-column">
        <div>
          <h4 className="analysis-section__title">Assigned roles</h4>
          <div className="analysis-list">
            {Object.entries(roleAnalysis.roles).map(([pokemonName, role]) => (
              <article className="analysis-list-card" key={pokemonName}>
                <div className="analysis-list-card__header">
                  <strong className="analysis-list-card__title">{pokemonName}</strong>
                </div>
                <p className="analysis-list-card__copy">{role}</p>
              </article>
            ))}
          </div>
        </div>
        <div>
          <h4 className="analysis-section__title">Role counts</h4>
          <div className="analysis-chip-list">
            {Object.entries(roleAnalysis.roleCounts).map(([role, count]) => (
              <span className="analysis-chip" key={role}>
                {role}: {count}
              </span>
            ))}
          </div>
          <h4 className="analysis-section__title">Summary</h4>
          <ul className="analysis-bullet-list">
            {roleAnalysis.summary.map((note) => (
              <li key={note}>{note}</li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  )
}

export default RoleAnalysisSection
