import { NavLink, Outlet } from 'react-router-dom'

function AppShell() {
  return (
    <div className="app-shell">
      <header className="app-header">
        <div className="brand-block">
          <p className="eyebrow">Pokemon Team Analysis Tool</p>
          <h1>Frontend Phase 1</h1>
          <p className="brand-copy">
            React, Vite, routing, Axios, and shared UI primitives are in place for the
            next feature phases.
          </p>
        </div>
        <nav className="main-nav" aria-label="Primary">
          <NavLink to="/pokedex" className="nav-pill">
            Pokedex
          </NavLink>
          <NavLink to="/team-builder" className="nav-pill">
            Team Builder
          </NavLink>
          <NavLink to="/team-analysis" className="nav-pill">
            Team Analysis
          </NavLink>
        </nav>
      </header>

      <main className="app-main">
        <Outlet />
      </main>

      <footer className="app-footer">
        <span>Backend API base URL is configured through `VITE_API_BASE_URL`.</span>
        <span>Sprites and official artwork are both supported by the image utility.</span>
      </footer>
    </div>
  )
}

export default AppShell
