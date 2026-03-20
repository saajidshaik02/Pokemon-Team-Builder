function ErrorNotice({ title = 'Something went wrong', message }) {
  return (
    <div className="status-card status-card--error" role="alert">
      <h3>{title}</h3>
      <p>{message}</p>
    </div>
  )
}

export default ErrorNotice
