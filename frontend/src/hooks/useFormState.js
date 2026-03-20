import { useState } from 'react'

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
