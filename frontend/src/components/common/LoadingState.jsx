function LoadingState({ label = 'Loading data...' }) {
  return (
    <div className="status-card" role="status" aria-live="polite">
      <div className="spinner" aria-hidden="true" />
      <p>{label}</p>
    </div>
  )
}

export default LoadingState
