function RecommendationsSection({ recommendations }) {
  return (
    <section className="section-card analysis-section">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Recommendations</p>
          <h3>Concise next steps</h3>
        </div>
      </div>
      {recommendations.length > 0 ? (
        <div className="analysis-list">
          {recommendations.map((recommendation) => (
            <article className="analysis-list-card analysis-list-card--callout" key={recommendation}>
              <p className="analysis-list-card__copy">{recommendation}</p>
            </article>
          ))}
        </div>
      ) : (
        <div className="empty-state-card empty-state-card--analysis">
          <p className="eyebrow">No Recommendations</p>
          <h4>The backend did not flag an obvious next move.</h4>
          <p className="analysis-empty-copy">
            This usually means the team does not trigger the current deterministic recommendation rules.
          </p>
        </div>
      )}
    </section>
  )
}

export default RecommendationsSection
