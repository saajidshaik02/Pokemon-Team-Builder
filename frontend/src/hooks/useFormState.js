import { useState } from 'react'

/**
 * Small controlled-form helper used across pages and form components.
 * Accepts either an input event or an explicit `name, value` pair in `updateValue`.
 */
function useFormState(initialState) {
  const [values, setValues] = useState(initialState)

  function updateValue(eventOrName, nextValue) {
    if (typeof eventOrName === 'string') {
      setValues((current) => ({ ...current, [eventOrName]: nextValue }))
      return
    }

    const { name, value } = eventOrName.target
    setValues((current) => ({ ...current, [name]: value }))
  }

  function resetValues(nextState = initialState) {
    setValues(nextState)
  }

  return {
    values,
    setValues,
    updateValue,
    resetValues,
  }
}

export default useFormState
