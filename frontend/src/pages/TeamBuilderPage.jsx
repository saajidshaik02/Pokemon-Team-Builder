import ImageLoader from '../components/common/ImageLoader.jsx'
import { resolvePokemonImage } from '../api/imageUtils.js'

const teamSlots = [
  {
    name: 'charizard',
    spriteUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png',
    officialArtworkUrl:
      'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png',
  },
  {
    name: 'blastoise',
    spriteUrl: 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png',
    officialArtworkUrl:
      'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/9.png',
  },
  null,
  null,
  null,
  null,
]

function TeamBuilderPage() {
  return (
    <section className="page-grid">
      <div className="section-card hero-card">
        <p className="eyebrow">Route Ready</p>
        <h2>Team Builder</h2>
        <p>
          The shell for team composition is in place. Phase 3 can focus on add or remove
          actions, validation, and submission flow instead of bootstrapping.
        </p>
        <ul className="feature-list">
          <li>Six-slot layout prepared</li>
          <li>Sprite-first image strategy documented for team slots</li>
          <li>Room reserved for max-team and duplicate validation messages</li>
        </ul>
      </div>

      <div className="section-card">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Sprite-First Team Slots</p>
            <h3>Current Team Layout</h3>
          </div>
          <span className="pill-muted">2 / 6 Pokemon</span>
        </div>

        <div className="slot-grid">
          {teamSlots.map((pokemon, index) => (
            <div className={`slot-card ${pokemon ? 'slot-card--filled' : ''}`} key={`slot-${index + 1}`}>
              {pokemon ? (
                <>
                  <ImageLoader {...resolvePokemonImage(pokemon, 'sprite-first')} className="slot-image" />
                  <strong>{pokemon.name}</strong>
                </>
              ) : (
                <>
                  <div className="slot-plus" aria-hidden="true">
                    +
                  </div>
                  <span>Empty slot</span>
                </>
              )}
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

export default TeamBuilderPage
