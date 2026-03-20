import LoadingState from '../components/common/LoadingState.jsx'
import ErrorNotice from '../components/common/ErrorNotice.jsx'
import ImageLoader from '../components/common/ImageLoader.jsx'
import { resolvePokemonImage } from '../api/imageUtils.js'

const demoPokemon = {
  name: 'pikachu',
  spriteUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png',
  officialArtworkUrl:
    'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png',
}

function PokedexPage() {
  const spriteFirst = resolvePokemonImage(demoPokemon, 'sprite-first')
  const artworkFirst = resolvePokemonImage(demoPokemon, 'artwork-first')

  return (
    <section className="page-grid">
      <div className="section-card hero-card">
        <p className="eyebrow">Route Ready</p>
        <h2>Pokedex</h2>
        <p>
          This page is wired for independent routing and prepared for `GET /api/pokemon/{'{name}'}`.
          Phase 2 can focus on the actual lookup form and result rendering instead of setup work.
        </p>
        <ul className="feature-list">
          <li>Axios helper ready for Pokemon lookup</li>
          <li>Shared loading and error components available</li>
          <li>Sprite-first and artwork-first image modes supported</li>
        </ul>
      </div>

      <div className="section-card split-card">
        <div>
          <p className="eyebrow">Sprite-First</p>
          <h3>Quick Lookup Preview</h3>
          <ImageLoader {...spriteFirst} />
        </div>
        <div>
          <p className="eyebrow">Artwork-First</p>
          <h3>Detailed Profile View</h3>
          <ImageLoader {...artworkFirst} />
        </div>
      </div>

      <LoadingState label="Example loading state for Pokemon lookup requests" />
      <ErrorNotice
        title="Example API error state"
        message="Pokemon 'missingno' was not found. Check the name and try again."
      />
    </section>
  )
}

export default PokedexPage
