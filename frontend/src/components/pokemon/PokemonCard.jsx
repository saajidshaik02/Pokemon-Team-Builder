import ImageLoader from '../common/ImageLoader.jsx'
import { resolvePokemonImage } from '../../api/imageUtils.js'
import TypeBadge from './TypeBadge.jsx'
import PokemonAbilitiesList from './PokemonAbilitiesList.jsx'
import PokemonStatsPanel from './PokemonStatsPanel.jsx'

function formatPokemonName(name) {
  return name.charAt(0).toUpperCase() + name.slice(1)
}

function PokemonCard({ pokemon }) {
  const artworkImage = resolvePokemonImage(pokemon, 'artwork-first')

  return (
    <article className="section-card pokemon-card">
      <div className="pokemon-card__hero">
        <div className="pokemon-card__copy">
          <p className="eyebrow">Detailed View</p>
          <div className="pokemon-card__title-row">
            <h3>{formatPokemonName(pokemon.name)}</h3>
            <span className="pokemon-card__dex">#{pokemon.id}</span>
          </div>
          <p className="pokemon-card__summary">
            A cleaner profile view with typing, abilities, and the base stats that shape how this
            Pokemon plays.
          </p>
          <div className="type-badge-list">
            {pokemon.types.map((type) => (
              <TypeBadge key={type} type={type} />
            ))}
          </div>
        </div>
        <div className="pokemon-card__artwork">
          <ImageLoader {...artworkImage} />
        </div>
      </div>

      <div className="pokemon-card__details">
        <PokemonAbilitiesList abilities={pokemon.abilities} />
        <PokemonStatsPanel stats={pokemon.stats} />
      </div>
    </article>
  )
}

export default PokemonCard
