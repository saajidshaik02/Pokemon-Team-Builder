import httpClient from './httpClient.js'

export async function getPokemonByName(name) {
  const response = await httpClient.get(`/api/pokemon/${encodeURIComponent(name)}`)
  return response.data
}

export async function getHealthStatus() {
  const response = await httpClient.get('/api/health')
  return response.data
}
