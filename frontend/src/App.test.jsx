import { screen } from '@testing-library/react'
import App from './App.jsx'
import renderWithRouter from './test/renderWithRouter.jsx'

describe('App routes', () => {
  it('renders the Pokedex route', () => {
    renderWithRouter(<App />, { route: '/pokedex' })

    expect(screen.getByRole('heading', { name: 'Pokedex' })).toBeInTheDocument()
  })

  it('renders the Team Builder route', () => {
    renderWithRouter(<App />, { route: '/team-builder' })

    expect(screen.getByRole('heading', { name: 'Team Builder' })).toBeInTheDocument()
  })

  it('renders the Team Analysis route', () => {
    renderWithRouter(<App />, { route: '/team-analysis' })

    expect(screen.getByRole('heading', { name: 'Team Analysis' })).toBeInTheDocument()
  })

  it('redirects unknown routes back to the Pokedex view', () => {
    renderWithRouter(<App />, { route: '/missing-route' })

    expect(screen.getByRole('heading', { name: 'Pokedex' })).toBeInTheDocument()
  })
})
