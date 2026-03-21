import heroImage from '../assets/hero.png'
import { NavLink, Outlet } from 'react-router-dom'

function AppShell() {
  return (
    <div className="app-shell">
      <header className="app-header">
        <div className="app-header__content">
          <div className="brand-block">
            <p className="eyebrow">Pokemon Team Analysis Tool</p>
            <h1>Build a team, surface the weak spots, and tighten the balance.</h1>
            <p className="brand-copy">
              The React frontend stays compact and readable while the Spring Boot backend
              remains the source of truth for Pokemon lookup and deterministic team analysis.
            </p>
            <div className="brand-highlights" aria-label="Application highlights">
              <span className="brand-chip">Pokedex lookup</span>
              <span className="brand-chip">6-slot builder</span>
              <span className="brand-chip">Weakness-first analysis</span>
            </div>
          </div>
          <div className="hero-orbital" aria-hidden="true">
            <div className="hero-orbital__frame">
              <img src={heroImage} alt="" className="hero-orbital__image" />
            </div>
          </div>
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
        <span>Sprites stay compact for quick views, while official artwork drives detailed views.</span>
      </footer>
    </div>
  )
}

export default AppShell
