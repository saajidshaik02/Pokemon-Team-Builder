import { render } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'

function renderWithRouter(ui, { route = '/', entries, ...options } = {}) {
  const initialEntries = entries ?? [route]

  return render(<MemoryRouter initialEntries={initialEntries}>{ui}</MemoryRouter>, options)
}

export default renderWithRouter
