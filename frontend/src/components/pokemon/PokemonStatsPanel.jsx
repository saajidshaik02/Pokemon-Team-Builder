const statLabels = [
  { key: 'hp', label: 'HP' },
  { key: 'attack', label: 'Attack' },
  { key: 'defense', label: 'Defense' },
  { key: 'specialAttack', label: 'Sp. Atk' },
  { key: 'specialDefense', label: 'Sp. Def' },
  { key: 'speed', label: 'Speed' },
]

function PokemonStatsPanel({ stats }) {
  return (
    <section className="pokemon-detail-block">
      <div className="pokemon-detail-block__heading">
        <p className="eyebrow">Base Stats</p>
        <h4>Readable stat spread</h4>
      </div>
      <div className="stats-panel">
        {statLabels.map(({ key, label }) => {
          const value = stats[key]
          const percent = Math.min(100, Math.round((value / 180) * 100))

          return (
            <div className="stat-row" key={key}>
              <span className="stat-row__label">{label}</span>
              <span className="stat-row__value">{value}</span>
              <div className="stat-row__bar" aria-hidden="true">
                <span className="stat-row__fill" style={{ width: `${percent}%` }} />
              </div>
            </div>
          )
        })}
      </div>
    </section>
  )
}

export default PokemonStatsPanel
