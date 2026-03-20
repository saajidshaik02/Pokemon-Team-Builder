import httpClient from './httpClient.js'

export async function analyzeTeam(pokemonNames) {
  const response = await httpClient.post('/api/team/analyze', { pokemonNames })
  return response.data
}
