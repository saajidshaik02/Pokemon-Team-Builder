function EmptyTeamSlot({ slotNumber }) {
  return (
    <div className="slot-card">
      <div className="slot-card__index">Slot {slotNumber}</div>
      <div className="slot-plus" aria-hidden="true">
        +
      </div>
      <strong>Empty slot</strong>
      <span className="slot-card__helper">Add a Pokemon to fill this position.</span>
    </div>
  )
}

export default EmptyTeamSlot
