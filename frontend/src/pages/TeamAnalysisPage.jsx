import ImageLoader from '../components/common/ImageLoader.jsx'
import { resolvePokemonImage } from '../api/imageUtils.js'

const analyzedTeam = [
  {
    name: 'venusaur',
    spriteUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png',
    officialArtworkUrl:
      'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png',
  },
  {
    name: 'arcanine',
    spriteUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/59.png',
    officialArtworkUrl:
      'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/59.png',
  },
]

function TeamAnalysisPage() {
  return (
    <section className="page-grid">
      <div className="section-card hero-card">
        <p className="eyebrow">Route Ready</p>
        <h2>Team Analysis</h2>
        <p>
          This page is prepared for `POST /api/team/analyze` and uses the artwork-first
          mode for cleaner summary presentation.
        </p>
        <ul className="feature-list">
          <li>Analysis route and shared layout are live</li>
          <li>Artwork-first image mode is ready for polished team summaries</li>
          <li>Section cards are ready for weaknesses, roles, stats, and recommendations</li>
        </ul>
      </div>

      <div className="section-card">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Artwork-First Summary</p>
            <h3>Analysis Header Preview</h3>
          </div>
        </div>
        <div className="analysis-team">
          {analyzedTeam.map((pokemon) => (
            <div className="analysis-member" key={pokemon.name}>
              <ImageLoader {...resolvePokemonImage(pokemon, 'artwork-first')} className="analysis-image" />
              <strong>{pokemon.name}</strong>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

export default TeamAnalysisPage
