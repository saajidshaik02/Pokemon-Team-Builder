function formatAbility(ability) {
  return ability
    .split('-')
    .map((part) => part.charAt(0).toUpperCase() + part.slice(1))
    .join(' ')
}

function PokemonAbilitiesList({ abilities }) {
  return (
    <section className="pokemon-detail-block">
      <div className="pokemon-detail-block__heading">
        <p className="eyebrow">Abilities</p>
        <h4>What it can do</h4>
      </div>
      <ul className="ability-list">
        {abilities.map((ability) => (
          <li key={ability} className="ability-pill">
            {formatAbility(ability)}
          </li>
        ))}
      </ul>
    </section>
  )
}

export default PokemonAbilitiesList
