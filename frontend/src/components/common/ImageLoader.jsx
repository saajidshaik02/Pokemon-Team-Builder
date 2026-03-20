import { useState } from 'react'

function ImageLoader(props) {
  const sourceKey = `${props.primarySrc ?? ''}|${props.fallbackSrc ?? ''}`
  return <ImageLoaderStateful key={sourceKey} {...props} />
}

function ImageLoaderStateful({ primarySrc, fallbackSrc, alt, className = '' }) {
  const [currentSrc, setCurrentSrc] = useState(primarySrc || fallbackSrc || '')
  const [hasTriedFallback, setHasTriedFallback] = useState(false)
  const [isLoaded, setIsLoaded] = useState(false)

  function handleLoad() {
    setIsLoaded(true)
  }

  function handleError() {
    if (!hasTriedFallback && fallbackSrc && fallbackSrc !== currentSrc) {
      setCurrentSrc(fallbackSrc)
      setHasTriedFallback(true)
      setIsLoaded(false)
      return
    }

    setCurrentSrc('')
    setIsLoaded(true)
  }

  if (!currentSrc) {
    return (
      <div className={`image-frame image-frame--empty ${className}`}>
        <span>No image</span>
      </div>
    )
  }

  return (
    <div className={`image-frame ${className}`}>
      {!isLoaded && <div className="image-skeleton" aria-hidden="true" />}
      <img
        src={currentSrc}
        alt={alt}
        className={`image-frame__img ${isLoaded ? 'image-frame__img--ready' : ''}`}
        onLoad={handleLoad}
        onError={handleError}
      />
    </div>
  )
}

export default ImageLoader
