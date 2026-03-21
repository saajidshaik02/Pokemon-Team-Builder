import { screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import TeamBuilderPage from './TeamBuilderPage.jsx'
import renderWithRouter from '../test/renderWithRouter.jsx'
import { getPokemonByName } from '../api/index.js'

vi.mock('../api/index.js', () => ({
  getPokemonByName: vi.fn(),
  analyzeTeam: vi.fn(),
}))

function createPokemon(name, types = ['normal']) {
  return {
    id: name.length,
    name,
    types,
    officialArtworkUrl: `https://example.com/${name}-art.png`,
    spriteUrl: `https://example.com/${name}-sprite.png`,
  }
}

describe('TeamBuilderPage', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders the initial empty team state with six visible empty slots', () => {
    renderWithRouter(<TeamBuilderPage />)

    expect(screen.getByText('No Team Members Yet')).toBeInTheDocument()
    expect(screen.getAllByText(/Empty slot/i)).toHaveLength(6)
  })

  it('shows blank-entry validation before trying to add a Pokemon', async () => {
    const user = userEvent.setup()

    renderWithRouter(<TeamBuilderPage />)

    await user.click(screen.getByRole('button', { name: 'Add Pokemon' }))

    expect(screen.getByText('Enter a Pokemon name before adding it to the team.')).toBeInTheDocument()
    expect(getPokemonByName).not.toHaveBeenCalled()
  })

  it('adds and removes a Pokemon through the team builder form and slot actions', async () => {
    const user = userEvent.setup()
    getPokemonByName.mockResolvedValue(createPokemon('garchomp', ['dragon', 'ground']))

    renderWithRouter(<TeamBuilderPage />)

    await user.type(screen.getByLabelText(/add pokemon to your team/i), 'garchomp')
    await user.click(screen.getByRole('button', { name: 'Add Pokemon' }))

    expect(await screen.findByText('garchomp added to the team.')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Analyze Team' })).toBeEnabled()
    expect(screen.getByText('garchomp')).toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: 'Remove' }))

    expect(await screen.findByText('Pokemon removed from the team.')).toBeInTheDocument()
    expect(screen.getAllByText(/Empty slot/i)).toHaveLength(6)
  })

  it('blocks adding a seventh Pokemon and preserves the six filled slots', async () => {
    const user = userEvent.setup()
    const teamNames = ['pikachu', 'charizard', 'blastoise', 'venusaur', 'snorlax', 'gengar']

    getPokemonByName.mockImplementation(async (name) => createPokemon(name))

    renderWithRouter(<TeamBuilderPage />)

    const input = screen.getByLabelText(/add pokemon to your team/i)

    for (const name of teamNames) {
      await user.clear(input)
      await user.type(input, name)
      await user.click(screen.getByRole('button', { name: 'Add Pokemon' }))
      await screen.findByText(`${name} added to the team.`)
    }

    await user.clear(input)
    await user.type(input, 'dragonite')
    await user.click(screen.getByRole('button', { name: 'Add Pokemon' }))

    expect(screen.getByText('Your team already has 6 Pokemon. Remove one before adding another.')).toBeInTheDocument()
    expect(screen.getByText('6 / 6 Pokemon')).toBeInTheDocument()
    expect(getPokemonByName).toHaveBeenCalledTimes(6)

    await waitFor(() => {
      const filledSlotNames = screen.getAllByText(
        (_, element) => element?.classList.contains('slot-card__name') ?? false,
      )
      expect(filledSlotNames).toHaveLength(6)
    })
  })
})
