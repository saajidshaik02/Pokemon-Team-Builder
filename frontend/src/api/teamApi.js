import httpClient from './httpClient.js'

/**
 * Normalizes the backend team-analysis payload into a stable frontend shape with
 * empty defaults for optional sections.
 */
function normalizeTeamAnalysisResponse(data) {
  return {
    team: data.team ?? [],
    typeAnalysis: {
      weaknesses: data.typeAnalysis?.weaknesses ?? [],
      resistances: data.typeAnalysis?.resistances ?? [],
      immunities: data.typeAnalysis?.immunities ?? [],
      synergyNotes: data.typeAnalysis?.synergyNotes ?? [],
    },
    roleAnalysis: {
      roles: data.roleAnalysis?.roles ?? {},
      roleCounts: data.roleAnalysis?.roleCounts ?? {},
      summary: data.roleAnalysis?.summary ?? [],
    },
    statSummary: {
      totalHp: data.statSummary?.totalHp ?? 0,
      totalAttack: data.statSummary?.totalAttack ?? 0,
      totalDefense: data.statSummary?.totalDefense ?? 0,
      totalSpecialAttack: data.statSummary?.totalSpecialAttack ?? 0,
      totalSpecialDefense: data.statSummary?.totalSpecialDefense ?? 0,
      totalSpeed: data.statSummary?.totalSpeed ?? 0,
      averageHp: data.statSummary?.averageHp ?? 0,
      averageAttack: data.statSummary?.averageAttack ?? 0,
      averageDefense: data.statSummary?.averageDefense ?? 0,
      averageSpecialAttack: data.statSummary?.averageSpecialAttack ?? 0,
      averageSpecialDefense: data.statSummary?.averageSpecialDefense ?? 0,
      averageSpeed: data.statSummary?.averageSpeed ?? 0,
      strengths: data.statSummary?.strengths ?? [],
      weaknesses: data.statSummary?.weaknesses ?? [],
    },
    recommendations: data.recommendations ?? [],
  }
}

/**
 * Submits an ordered Pokemon name list to the backend team-analysis endpoint.
 */
export async function analyzeTeam(pokemonNames) {
  const response = await httpClient.post('/api/team/analyze', { pokemonNames })
  return normalizeTeamAnalysisResponse(response.data)
}
