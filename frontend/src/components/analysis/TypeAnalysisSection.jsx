import ImmunityList from './ImmunityList.jsx'
import ResistanceList from './ResistanceList.jsx'
import WeaknessList from './WeaknessList.jsx'

function TypeAnalysisSection({ typeAnalysis }) {
  return (
    <section className="section-card analysis-section">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Type Analysis</p>
          <h3>Weaknesses, resistances, and immunities</h3>
        </div>
      </div>

      <div className="analysis-section__stack">
        <div>
          <h4 className="analysis-section__title">Weaknesses first</h4>
          <WeaknessList weaknesses={typeAnalysis.weaknesses} />
        </div>
        <div>
          <h4 className="analysis-section__title">Resistances</h4>
          <ResistanceList resistances={typeAnalysis.resistances} />
        </div>
        <div>
          <h4 className="analysis-section__title">Immunities</h4>
          <ImmunityList immunities={typeAnalysis.immunities} />
        </div>
        <div>
          <h4 className="analysis-section__title">Synergy notes</h4>
          {typeAnalysis.synergyNotes.length > 0 ? (
            <ul className="analysis-bullet-list">
              {typeAnalysis.synergyNotes.map((note) => (
                <li key={note}>{note}</li>
              ))}
            </ul>
          ) : (
            <p className="analysis-empty-copy">No extra synergy notes were returned.</p>
          )}
        </div>
      </div>
    </section>
  )
}

export default TypeAnalysisSection
