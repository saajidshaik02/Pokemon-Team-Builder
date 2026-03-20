import { useCallback, useState } from 'react'

function useApi(apiFunction) {
  const [data, setData] = useState(null)
  const [error, setError] = useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const execute = useCallback(
    async (...args) => {
      setIsLoading(true)
      setError(null)

      try {
        const result = await apiFunction(...args)
        setData(result)
        return result
      } catch (apiError) {
        setError(apiError)
        throw apiError
      } finally {
        setIsLoading(false)
      }
    },
    [apiFunction],
  )

  return {
    data,
    error,
    isLoading,
    execute,
    setData,
    setError,
  }
}

export default useApi
