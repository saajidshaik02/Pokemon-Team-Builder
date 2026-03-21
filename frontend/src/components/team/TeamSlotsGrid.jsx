import EmptyTeamSlot from './EmptyTeamSlot.jsx'
import TeamSlotCard from './TeamSlotCard.jsx'

const TOTAL_TEAM_SLOTS = 6

function TeamSlotsGrid({ team, onRemovePokemon }) {
  return (
    <div className="slot-grid">
      {Array.from({ length: TOTAL_TEAM_SLOTS }, (_, index) => {
        const pokemon = team[index]
        const slotNumber = index + 1

        if (!pokemon) {
          return <EmptyTeamSlot key={`slot-${slotNumber}`} slotNumber={slotNumber} />
        }

        return (
          <TeamSlotCard
            key={`slot-${slotNumber}-${pokemon.name}`}
            pokemon={pokemon}
            slotNumber={slotNumber}
            onRemove={() => onRemovePokemon(index)}
          />
        )
      })}
    </div>
  )
}

export default TeamSlotsGrid
