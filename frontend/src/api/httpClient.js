import axios from 'axios'

const baseURL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'

const DEFAULT_ERROR_DETAILS = {
  400: {
    error: 'Bad Request',
    title: 'Invalid request',
    message: 'The backend rejected this request. Check the submitted input and try again.',
  },
  404: {
    error: 'Not Found',
    title: 'Pokemon not found',
    message: 'The requested Pokemon could not be found.',
  },
  502: {
    error: 'Bad Gateway',
    title: 'Upstream service unavailable',
    message: 'Pokemon data could not be loaded from the upstream service right now.',
  },
}

/**
 * Converts Axios failures into the stable frontend error shape consumed by pages.
 * This keeps view code focused on user-visible state instead of transport details.
 */
function buildNormalizedError(status, responseData = {}) {
  const defaults = DEFAULT_ERROR_DETAILS[status] ?? {
    error: 'Request failed',
    title: 'Request failed',
    message: 'The backend could not complete this request.',
  }

  return {
    status,
    error: responseData.error ?? defaults.error,
    title: defaults.title,
    message: responseData.message ?? defaults.message,
    path: responseData.path ?? '',
    timestamp: responseData.timestamp ?? '',
  }
}

function normalizeApiError(error) {
  if (error.response) {
    return buildNormalizedError(error.response.status, error.response.data)
  }

  if (error.request) {
    return {
      status: 0,
      title: 'Backend unavailable',
      message: 'Backend is unreachable. Check that the Spring Boot server is running.',
      error: 'Network Error',
      path: '',
      timestamp: '',
    }
  }

  return {
    status: 0,
    title: 'Unexpected frontend error',
    message: error.message || 'Unexpected frontend error.',
    error: 'Unexpected Error',
    path: '',
    timestamp: '',
  }
}

const httpClient = axios.create({
  baseURL,
  timeout: 10000,
})

httpClient.interceptors.response.use(
  (response) => response,
  (error) => Promise.reject(normalizeApiError(error)),
)

export { baseURL }
export default httpClient
