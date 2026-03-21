import { useCallback, useRef, useState } from 'react'

/**
 * Shared request-state hook for route-level API calls.
 * When `ignoreStaleResponses` is enabled, only the latest request is allowed to
 * update visible state.
 */
function useApi(apiFunction, options = {}) {
  const [data, setData] = useState(null)
  const [error, setError] = useState(null)
  const [isLoading, setIsLoading] = useState(false)
  const latestRequestId = useRef(0)
  const ignoreStaleResponses = options.ignoreStaleResponses ?? false

  const execute = useCallback(
    async (...args) => {
      const requestId = latestRequestId.current + 1
      latestRequestId.current = requestId
      setIsLoading(true)
      setError(null)

      try {
        const result = await apiFunction(...args)

        if (!ignoreStaleResponses || requestId === latestRequestId.current) {
          setData(result)
        }

        return result
      } catch (apiError) {
        if (!ignoreStaleResponses || requestId === latestRequestId.current) {
          setError(apiError)
        }

        if (ignoreStaleResponses && requestId !== latestRequestId.current) {
          throw { ...apiError, isStale: true }
        }

        throw apiError
      } finally {
        if (!ignoreStaleResponses || requestId === latestRequestId.current) {
          setIsLoading(false)
        }
      }
    },
    [apiFunction, ignoreStaleResponses],
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
