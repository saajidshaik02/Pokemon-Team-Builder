import ImageLoader from '../common/ImageLoader.jsx'
import { resolvePokemonImage } from '../../api/imageUtils.js'
import TypeBadge from '../pokemon/TypeBadge.jsx'

function TeamSlotCard({ pokemon, slotNumber, onRemove }) {
  return (
    <div className="slot-card slot-card--filled">
      <div className="slot-card__index">Slot {slotNumber}</div>
      <ImageLoader {...resolvePokemonImage(pokemon, 'sprite-first')} className="slot-image" />
      <strong className="slot-card__name">{pokemon.name}</strong>
      <div className="type-badge-list slot-card__types">
        {pokemon.types.map((type) => (
          <TypeBadge key={`${pokemon.name}-${type}`} type={type} />
        ))}
      </div>
      <button type="button" className="slot-card__remove" onClick={onRemove}>
        Remove
      </button>
    </div>
  )
}

export default TeamSlotCard
