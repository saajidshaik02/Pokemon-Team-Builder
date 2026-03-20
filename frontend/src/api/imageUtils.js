export function resolvePokemonImage(pokemon, mode = 'artwork-first') {
  if (!pokemon) {
    return { primarySrc: '', fallbackSrc: '', alt: 'Pokemon image unavailable' }
  }

  const artwork = pokemon.officialArtworkUrl ?? ''
  const sprite = pokemon.spriteUrl ?? ''

  if (mode === 'sprite-first') {
    return {
      primarySrc: sprite || artwork,
      fallbackSrc: artwork || sprite,
      alt: `${pokemon.name ?? 'Pokemon'} sprite`,
    }
  }

  return {
    primarySrc: artwork || sprite,
    fallbackSrc: sprite || artwork,
    alt: `${pokemon.name ?? 'Pokemon'} artwork`,
  }
}
