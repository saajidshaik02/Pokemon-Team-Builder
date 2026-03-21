function TeamValidationMessage({ message, tone = 'warning' }) {
  if (!message) {
    return null
  }

  return (
    <div className={`team-validation-message team-validation-message--${tone}`} role="status">
      <p>{message}</p>
    </div>
  )
}

export default TeamValidationMessage
