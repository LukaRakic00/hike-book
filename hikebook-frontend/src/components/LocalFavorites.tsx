'use client';
import { useState, useEffect } from 'react';
import { apiService, Trail, PhotoDto } from '../services/api';
import { useToast } from './Toast';
import { getPlaceholderImage } from '../utils/cloudinary';
import './LocalFavorites.css';

export default function LocalFavorites() {
  const [trails, setTrails] = useState<Trail[]>([]);
  const [trailPhotos, setTrailPhotos] = useState<Map<number, PhotoDto[]>>(new Map());
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [favorites, setFavorites] = useState<Set<number>>(new Set());
  const { showSuccess, showError } = useToast();

  useEffect(() => {
    loadTrails();
    loadFavorites();
  }, []);

  const loadTrails = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await apiService.getTrails({ limit: 10 });
      console.log('API Response:', response); // Debug log
      if (response.success) {
        setTrails(response.data);
        // Load photos for each trail
        await loadTrailPhotos(response.data);
      } else {
        setError(response.message || 'Failed to load trails');
        showError('Error', response.message || 'Failed to load trails');
      }
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Failed to load trails';
      setError(errorMessage);
      showError('Error', errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const loadTrailPhotos = async (trailsData: Trail[]) => {
    const photosMap = new Map<number, PhotoDto[]>();
    
    for (const trail of trailsData) {
      try {
        console.log(`Loading photos for trail ${trail.id}: ${trail.name}`);
        const photosResponse = await apiService.getTrailPhotos(trail.id);
        console.log(`Photos response for trail ${trail.id}:`, photosResponse);
        if (photosResponse.success && photosResponse.data) {
          photosMap.set(trail.id, photosResponse.data);
          console.log(`Found ${photosResponse.data.length} photos for trail ${trail.id}`);
        }
      } catch (err) {
        console.log(`Could not load photos for trail ${trail.id}:`, err);
      }
    }
    
    console.log('Final photos map:', photosMap);
    setTrailPhotos(photosMap);
  };

  const getMainPhoto = (trailId: number): PhotoDto | null => {
    const photos = trailPhotos.get(trailId);
    console.log(`Getting main photo for trail ${trailId}, photos:`, photos);
    if (photos && photos.length > 0) {
      const mainPhoto = photos.find(photo => photo.isMain) || photos[0];
      console.log(`Main photo for trail ${trailId}:`, mainPhoto);
      return mainPhoto;
    }
    console.log(`No photos found for trail ${trailId}`);
    return null;
  };

  const loadFavorites = async () => {
    try {
      const response = await apiService.getFavorites();
      if (response.success) {
        const favoriteIds = new Set(response.data.map(trail => trail.id));
        setFavorites(favoriteIds);
      }
    } catch (err) {
      // Silently fail for favorites - user might not be logged in
      console.log('Could not load favorites:', err);
    }
  };

  const toggleFavorite = async (trailId: number) => {
    try {
      const isFavorite = favorites.has(trailId);
      const trail = trails.find(t => t.id === trailId);
      
      // Optimistic update
      setFavorites(prev => {
        const newFavorites = new Set(prev);
        if (isFavorite) {
          newFavorites.delete(trailId);
        } else {
          newFavorites.add(trailId);
        }
        return newFavorites;
      });

      // API call
      if (isFavorite) {
        await apiService.removeFromFavorites(trailId);
        showSuccess('Removed from favorites', `${trail?.name} has been removed from your favorites`);
      } else {
        await apiService.addToFavorites(trailId);
        showSuccess('Added to favorites', `${trail?.name} has been added to your favorites`);
      }
    } catch (err) {
      // Revert optimistic update on error
      setFavorites(prev => {
        const newFavorites = new Set(prev);
        if (favorites.has(trailId)) {
          newFavorites.delete(trailId);
        } else {
          newFavorites.add(trailId);
        }
        return newFavorites;
      });
      
      const errorMessage = err instanceof Error ? err.message : 'Failed to update favorites';
      showError('Error', errorMessage);
    }
  };

  const formatTime = (minutes: number) => {
    const hours = Math.floor(minutes / 60);
    const mins = minutes % 60;
    if (hours > 0) {
      return `${hours}h ${mins}m`;
    }
    return `${mins}m`;
  };

  const getDifficultyClass = (difficulty: string) => {
    switch (difficulty) {
      case 'moderate':
        return 'difficulty-moderate';
      case 'hard':
        return 'difficulty-hard';
      case 'easy':
        return 'difficulty-easy';
      default:
        return 'difficulty-moderate';
    }
  };

  const getDifficultyText = (difficulty: string) => {
    switch (difficulty) {
      case 'moderate':
        return 'Moderate';
      case 'hard':
        return 'Hard';
      case 'easy':
        return 'Easy';
      default:
        return 'Moderate';
    }
  };

  const scrollRight = () => {
    const container = document.querySelector('.local-favorites-cards');
    if (container) {
      container.scrollBy({ left: 300, behavior: 'smooth' });
    }
  };

  if (loading) {
    return (
      <div className="local-favorites">
        <div className="local-favorites-container">
          <div className="local-favorites-header">
            <h2 className="local-favorites-title">Local favorites near Belgrade</h2>
            <p className="local-favorites-subtitle">Discover the best trails in your area</p>
          </div>
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <div style={{
              width: '40px',
              height: '40px',
              border: '4px solid #f3f3f3',
              borderTop: '4px solid #22c55e',
              borderRadius: '50%',
              animation: 'spin 1s linear infinite',
              margin: '0 auto'
            }} />
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="local-favorites">
        <div className="local-favorites-container">
          <div className="local-favorites-header">
            <h2 className="local-favorites-title">Local favorites near Belgrade</h2>
            <p className="local-favorites-subtitle">Discover the best trails in your area</p>
          </div>
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <p style={{ color: '#ef4444', fontSize: '1.1rem' }}>{error}</p>
            <button 
              onClick={loadTrails}
              style={{
                marginTop: '20px',
                padding: '10px 20px',
                backgroundColor: '#22c55e',
                color: 'white',
                border: 'none',
                borderRadius: '8px',
                cursor: 'pointer'
              }}
            >
              Try Again
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="local-favorites">
      <div className="local-favorites-container">
        <div className="local-favorites-header">
          <h2 className="local-favorites-title">
            Local favorites near <span style={{ textDecoration: 'underline' }}>Belgrade</span>
          </h2>
          <p className="local-favorites-subtitle">Discover the best trails in your area</p>
        </div>
        
        <div style={{ position: 'relative' }}>
          <div className="local-favorites-cards">
            {Array.isArray(trails) && trails.map((trail) => {
              const mainPhoto = getMainPhoto(trail.id);
              return (
                <div key={trail.id} className="trail-card">
                  <div className="trail-card-image">
                    {mainPhoto ? (
                      <img 
                        src={mainPhoto.url.startsWith('http') ? mainPhoto.url : `https://res.cloudinary.com/dfefl6j9b/image/upload/${mainPhoto.url}`}
                        alt={trail.name}
                        onLoad={() => console.log(`Image loaded successfully for trail ${trail.id}:`, mainPhoto.url)}
                        onError={(e) => {
                          console.log(`Image failed to load for trail ${trail.id}:`, mainPhoto.url);
                          // Fallback to placeholder if image fails to load
                          const target = e.target as HTMLImageElement;
                          target.src = getPlaceholderImage(400, 300);
                        }}
                      />
                    ) : trail.main_image ? (
                      <img 
                        src={trail.main_image.startsWith('http') ? trail.main_image : `https://res.cloudinary.com/dfefl6j9b/image/upload/${trail.main_image}`}
                        alt={trail.name}
                        onError={(e) => {
                          // Fallback to placeholder if image fails to load
                          const target = e.target as HTMLImageElement;
                          target.src = getPlaceholderImage(400, 300);
                        }}
                      />
                    ) : (
                      <img 
                        src={getPlaceholderImage(400, 300)} 
                        alt={trail.name}
                      />
                    )}
                    <button 
                      className="bookmark-icon"
                      onClick={(e) => {
                        e.stopPropagation();
                        toggleFavorite(trail.id);
                      }}
                      style={{
                        backgroundColor: favorites.has(trail.id) ? '#22c55e' : 'rgba(255, 255, 255, 0.9)'
                      }}
                    >
                      <svg viewBox="0 0 24 24" style={{ fill: favorites.has(trail.id) ? 'white' : '#64748b' }}>
                        <path d="M17 3H7c-1.1 0-2 .9-2 2v16l7-3 7 3V5c0-1.1-.9-2-2-2z"/>
                      </svg>
                    </button>
                  </div>
                  
                  <div className="trail-card-content">
                    <h3 className="trail-card-title">{trail.name}</h3>
                    
                    <div className="trail-card-location">
                      <svg className="location-icon" viewBox="0 0 24 24">
                        <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"/>
                      </svg>
                      {trail.destination.name}
                    </div>
                    
                    <div className="trail-card-rating">
                      <svg className="star-icon" viewBox="0 0 24 24">
                        <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                      </svg>
                      <span className="rating-text">{trail.rating}</span>
                    </div>
                    
                    <div className="trail-card-details">
                      <span className={`difficulty-badge ${getDifficultyClass(trail.difficulty)}`}>
                        {getDifficultyText(trail.difficulty)}
                      </span>
                      <span>•</span>
                      <span>{trail.length} km</span>
                      <span>•</span>
                      <span>Est. {formatTime(trail.estimated_time)}</span>
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
          
          {trails.length > 0 && (
            <button className="scroll-button" onClick={scrollRight}>
              <svg viewBox="0 0 24 24">
                <path d="M8.59 16.59L13.17 12 8.59 7.41 10 6l6 6-6 6-1.41-1.41z"/>
              </svg>
            </button>
          )}
        </div>
      </div>
    </div>
  );
} 