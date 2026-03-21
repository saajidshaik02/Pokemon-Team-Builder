function TypeBadge({ type }) {
  return (
    <span className={`type-badge type-badge--${type}`} aria-label={`${type} type`}>
      {type}
    </span>
  )
}

export default TypeBadge
