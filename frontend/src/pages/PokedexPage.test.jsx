import { screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import PokedexPage from './PokedexPage.jsx'
import renderWithRouter from '../test/renderWithRouter.jsx'
import { getPokemonByName } from '../api/index.js'

vi.mock('../api/index.js', () => ({
  getPokemonByName: vi.fn(),
  analyzeTeam: vi.fn(),
}))

const pikachu = {
  id: 25,
  name: 'pikachu',
  types: ['electric'],
  abilities: ['static', 'lightning-rod'],
  stats: {
    hp: 35,
    attack: 55,
    defense: 40,
    specialAttack: 50,
    specialDefense: 50,
    speed: 90,
  },
  officialArtworkUrl: 'https://example.com/pikachu-art.png',
  spriteUrl: 'https://example.com/pikachu-sprite.png',
}

describe('PokedexPage', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('shows blank-name validation before making a lookup', async () => {
    const user = userEvent.setup()

    renderWithRouter(<PokedexPage />)

    await user.click(screen.getByRole('button', { name: 'Search' }))

    expect(screen.getAllByRole('alert')).toHaveLength(2)
    expect(screen.getByText('Invalid Pokemon lookup')).toBeInTheDocument()
    expect(screen.getAllByText('Enter a Pokemon name before searching.')).toHaveLength(2)
    expect(getPokemonByName).not.toHaveBeenCalled()
  })

  it('renders fetched Pokemon details after a successful lookup', async () => {
    const user = userEvent.setup()
    getPokemonByName.mockResolvedValue(pikachu)

    renderWithRouter(<PokedexPage />)

    await user.type(screen.getByLabelText(/search pokemon by name/i), 'pikachu')
    await user.click(screen.getByRole('button', { name: 'Search' }))

    expect(await screen.findByRole('heading', { name: 'Quick Lookup Preview' })).toBeInTheDocument()
    expect(screen.getByRole('heading', { name: 'Pikachu' })).toBeInTheDocument()
    expect(getPokemonByName).toHaveBeenCalledWith('pikachu')
  })

  it('renders the guided no-result state for a 404 lookup failure', async () => {
    const user = userEvent.setup()
    getPokemonByName.mockRejectedValue({
      status: 404,
      message: 'Pokemon not found.',
      error: 'Not Found',
    })

    renderWithRouter(<PokedexPage />)

    await user.type(screen.getByLabelText(/search pokemon by name/i), 'missingno')
    await user.click(screen.getByRole('button', { name: 'Search' }))

    expect(await screen.findByText('No Search Result')).toBeInTheDocument()
    expect(screen.getByText(/No Pokemon matched/i)).toHaveTextContent('missingno')
    await waitFor(() =>
      expect(screen.queryByText('Pokemon lookup failed')).not.toBeInTheDocument(),
    )
  })
})
