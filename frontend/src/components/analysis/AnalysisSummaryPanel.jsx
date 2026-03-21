import ImageLoader from '../common/ImageLoader.jsx'
import { resolvePokemonImage } from '../../api/imageUtils.js'
import TypeBadge from '../pokemon/TypeBadge.jsx'

function AnalysisSummaryPanel({ team }) {
  return (
    <section className="section-card">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Artwork-First Summary</p>
          <h3>Analyzed Team</h3>
        </div>
        <span className="pill-muted">{team.length} Pokemon</span>
      </div>
      <div className="analysis-team">
        {team.map((pokemon) => (
          <div className="analysis-member" key={pokemon.name}>
            <ImageLoader {...resolvePokemonImage(pokemon, 'artwork-first')} className="analysis-image" />
            <strong className="analysis-member__name">{pokemon.name}</strong>
            <div className="type-badge-list analysis-member__types">
              {pokemon.types.map((type) => (
                <TypeBadge key={`${pokemon.name}-${type}`} type={type} />
              ))}
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}

export default AnalysisSummaryPanel
