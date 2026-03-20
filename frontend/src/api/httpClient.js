import axios from 'axios'

const baseURL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080'

function normalizeApiError(error) {
  if (error.response?.data?.message) {
    return {
      status: error.response.status,
      message: error.response.data.message,
      error: error.response.data.error ?? 'Request failed',
    }
  }

  if (error.request) {
    return {
      status: 0,
      message: 'Backend is unreachable. Check that the Spring Boot server is running.',
      error: 'Network Error',
    }
  }

  return {
    status: 0,
    message: error.message || 'Unexpected frontend error.',
    error: 'Unexpected Error',
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
