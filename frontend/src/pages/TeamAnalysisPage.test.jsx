import { screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import TeamAnalysisPage from './TeamAnalysisPage.jsx'
import renderWithRouter from '../test/renderWithRouter.jsx'
import { analyzeTeam, getPokemonByName } from '../api/index.js'

vi.mock('../api/index.js', () => ({
  getPokemonByName: vi.fn(),
  analyzeTeam: vi.fn(),
  getHealthStatus: vi.fn(),
}))

const analysisResponse = {
  team: [
    { name: 'pikachu', types: ['electric'] },
    { name: 'charizard', types: ['fire', 'flying'] },
  ],
  typeAnalysis: {
    weaknesses: [],
    resistances: [],
    immunities: [],
    synergyNotes: [],
  },
  roleAnalysis: {
    roles: { pikachu: 'Fast attacker', charizard: 'Special attacker' },
    roleCounts: { 'Fast attacker': 1, 'Special attacker': 1 },
    summary: ['The team leans offensive.'],
  },
  statSummary: {
    totalHp: 156,
    totalAttack: 139,
    totalDefense: 121,
    totalSpecialAttack: 159,
    totalSpecialDefense: 135,
    totalSpeed: 190,
    averageHp: 78,
    averageAttack: 69.5,
    averageDefense: 60.5,
    averageSpecialAttack: 79.5,
    averageSpecialDefense: 67.5,
    averageSpeed: 95,
    strengths: ['Strong overall speed.'],
    weaknesses: [],
  },
  recommendations: ['Add a sturdier switch-in for Rock attacks.'],
}

function createPokemonDetails(name, types) {
  return {
    id: name.length,
    name,
    types,
    abilities: [],
    stats: {
      hp: 0,
      attack: 0,
      defense: 0,
      specialAttack: 0,
      specialDefense: 0,
      speed: 0,
    },
    officialArtworkUrl: `https://example.com/${name}-art.png`,
    spriteUrl: `https://example.com/${name}-sprite.png`,
  }
}

describe('TeamAnalysisPage', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders analysis results after a successful backend submission', async () => {
    const user = userEvent.setup()
    analyzeTeam.mockResolvedValue(analysisResponse)
    getPokemonByName.mockImplementation(async (name) => {
      if (name === 'pikachu') {
        return createPokemonDetails(name, ['electric'])
      }

      return createPokemonDetails(name, ['fire', 'flying'])
    })

    renderWithRouter(<TeamAnalysisPage />)

    await user.type(screen.getByLabelText(/enter 1 to 6 pokemon names/i), 'pikachu, charizard')
    await user.click(screen.getByRole('button', { name: 'Analyze Team' }))

    expect(await screen.findByRole('heading', { name: 'Analyzed Team' })).toBeInTheDocument()
    expect(screen.getByText('Add a sturdier switch-in for Rock attacks.')).toBeInTheDocument()
    expect(analyzeTeam).toHaveBeenCalledWith(['pikachu', 'charizard'])
  })

  it('surfaces backend analysis failures in the shared error notice', async () => {
    const user = userEvent.setup()
    analyzeTeam.mockRejectedValue({
      status: 400,
      message: 'Pokemon team must contain between 1 and 6 Pokemon.',
      error: 'Bad Request',
    })

    renderWithRouter(<TeamAnalysisPage />)

    await user.type(screen.getByLabelText(/enter 1 to 6 pokemon names/i), 'pikachu')
    await user.click(screen.getByRole('button', { name: 'Analyze Team' }))

    expect(await screen.findByText('Team analysis failed')).toBeInTheDocument()
    expect(screen.getByText('Pokemon team must contain between 1 and 6 Pokemon.')).toBeInTheDocument()
  })

  it('renders the empty recommendations state when the backend returns no recommendations', async () => {
    const user = userEvent.setup()
    analyzeTeam.mockResolvedValue({
      ...analysisResponse,
      recommendations: [],
    })
    getPokemonByName.mockImplementation(async (name) => {
      if (name === 'pikachu') {
        return createPokemonDetails(name, ['electric'])
      }

      return createPokemonDetails(name, ['fire', 'flying'])
    })

    renderWithRouter(<TeamAnalysisPage />)

    await user.type(screen.getByLabelText(/enter 1 to 6 pokemon names/i), 'pikachu, charizard')
    await user.click(screen.getByRole('button', { name: 'Analyze Team' }))

    expect(await screen.findByText('No Recommendations')).toBeInTheDocument()
    expect(screen.getByText(/did not flag an obvious next move/i)).toBeInTheDocument()
  })
})
