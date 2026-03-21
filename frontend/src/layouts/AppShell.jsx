import heroImage from '../assets/pokeball.png'
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
              Explore Pokemon, shape a six-slot lineup, and get a clear read on the strengths,
              gaps, and matchup pressure in your team.
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
        <span>Search individual Pokemon, build a full team, and compare how the pieces fit together.</span>
        <span>Quick views stay snappy, while detailed cards and summaries focus on cleaner artwork.</span>
      </footer>
    </div>
  )
}

export default AppShell
