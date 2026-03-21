import { screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import PokemonSearchForm from './PokemonSearchForm.jsx'
import renderWithRouter from '../../test/renderWithRouter.jsx'

describe('PokemonSearchForm', () => {
  it('calls the change and submit handlers with the entered name', async () => {
    const user = userEvent.setup()
    const handleNameChange = vi.fn()
    const handleSubmit = vi.fn((event) => event.preventDefault())

    renderWithRouter(
      <PokemonSearchForm
        name=""
        isSubmitting={false}
        validationMessage=""
        onNameChange={handleNameChange}
        onSubmit={handleSubmit}
      />,
    )

    await user.type(screen.getByLabelText(/search pokemon by name/i), 'pikachu')
    await user.click(screen.getByRole('button', { name: 'Search' }))

    expect(handleNameChange).toHaveBeenCalled()
    expect(handleSubmit).toHaveBeenCalledTimes(1)
  })

  it('surfaces the validation message accessibly when one is provided', () => {
    renderWithRouter(
      <PokemonSearchForm
        name=""
        isSubmitting={false}
        validationMessage="Enter a Pokemon name before searching."
        onNameChange={() => {}}
        onSubmit={(event) => event.preventDefault()}
      />,
    )

    expect(screen.getByRole('alert')).toHaveTextContent('Enter a Pokemon name before searching.')
    expect(screen.getByLabelText(/search pokemon by name/i)).toHaveAttribute('aria-invalid', 'true')
  })
})
