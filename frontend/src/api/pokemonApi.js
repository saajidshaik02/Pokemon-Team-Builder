import httpClient from './httpClient.js'

/**
 * Preserves the backend Pokemon stats shape while ensuring missing fields degrade safely.
 */
function normalizePokemonStats(stats) {
  return {
    hp: stats?.hp ?? 0,
    attack: stats?.attack ?? 0,
    defense: stats?.defense ?? 0,
    specialAttack: stats?.specialAttack ?? 0,
    specialDefense: stats?.specialDefense ?? 0,
    speed: stats?.speed ?? 0,
  }
}

/**
 * Normalizes backend Pokemon details without renaming DTO fields such as
 * `officialArtworkUrl` or `spriteUrl`.
 */
function normalizePokemonDetails(data) {
  return {
    id: data.id ?? 0,
    name: data.name ?? '',
    types: data.types ?? [],
    abilities: data.abilities ?? [],
    stats: normalizePokemonStats(data.stats),
    officialArtworkUrl: data.officialArtworkUrl ?? '',
    spriteUrl: data.spriteUrl ?? '',
  }
}

/**
 * Fetches one Pokemon by name from the backend API.
 */
export async function getPokemonByName(name) {
  const response = await httpClient.get(`/api/pokemon/${encodeURIComponent(name)}`)
  return normalizePokemonDetails(response.data)
}

/**
 * Reads the backend health endpoint used by local verification and diagnostics.
 */
export async function getHealthStatus() {
  const response = await httpClient.get('/api/health')
  return response.data
}

export { normalizePokemonDetails }
