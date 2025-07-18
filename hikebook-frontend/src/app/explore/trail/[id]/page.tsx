'use client';
import { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { useAuth } from '../../../../hooks/useAuth';
import LoadingSpinner from '../../../../components/LoadingSpinner';
import { getApiUrl } from '../../../../utils/apiConfig';
import './trail-details.css';

interface TrailDetails {
  id: number;
  name: string;
  description: string;
  difficulty: string;
  length: number;
  estimatedTime: number;
  rating: number;
  mainImage: string;
  activities: string[];
  destination: {
    id: number;
    name: string;
    region: string;
  };
  photos: Photo[];
  coordinates: Coordinate[];
}

interface Photo {
  id: number;
  url: string;
  caption: string;
  isMain: boolean;
}

interface Coordinate {
  latitude: number;
  longitude: number;
}

export default function TrailDetailsPage() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();
  const params = useParams();
  const trailId = params.id as string;
  
  const [trail, setTrail] = useState<TrailDetails | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isFavorite, setIsFavorite] = useState(false);
  const [activePhotoIndex, setActivePhotoIndex] = useState(0);

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.replace('/auth');
    }
  }, [isAuthenticated, isLoading, router]);

  useEffect(() => {
    if (trailId) {
      fetchTrailDetails();
      checkFavoriteStatus();
    }
  }, [trailId]);

  const fetchTrailDetails = async () => {
    try {
      setLoading(true);
      const response = await fetch(getApiUrl(`/api/explore/trail/${trailId}`));
      if (!response.ok) {
        throw new Error('Failed to fetch trail details');
      }
      const data = await response.json();
      setTrail(data.trail);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  const checkFavoriteStatus = async () => {
    try {
      const response = await fetch(getApiUrl('/api/favorites'));
      if (response.ok) {
        const data = await response.json();
        const favoriteIds = data.map((t: { id: number }) => t.id);
        setIsFavorite(favoriteIds.includes(parseInt(trailId)));
      }
    } catch (err) {
      console.log('Could not check favorite status:', err);
    }
  };

  const toggleFavorite = async () => {
    try {
      if (isFavorite) {
        await fetch(getApiUrl(`/api/favorites/${trailId}`), {
          method: 'DELETE',
        });
        setIsFavorite(false);
      } else {
        await fetch(getApiUrl('/api/favorites'), {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ trailId: parseInt(trailId) }),
        });
        setIsFavorite(true);
      }
    } catch (err) {
      console.log('Failed to toggle favorite:', err);
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
      case 'easy': return 'difficulty-easy';
      case 'moderate': return 'difficulty-moderate';
      case 'hard': return 'difficulty-hard';
      default: return 'difficulty-moderate';
    }
  };

  const getDifficultyText = (difficulty: string) => {
    switch (difficulty) {
      case 'easy': return 'Easy';
      case 'moderate': return 'Moderate';
      case 'hard': return 'Hard';
      default: return 'Moderate';
    }
  };

  if (isLoading || !isAuthenticated) {
    return (
      <div className="trail-details-loading">
        <LoadingSpinner size="lg" />
        <p>Loading...</p>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="trail-details-loading">
        <LoadingSpinner size="lg" />
        <p>Loading trail details...</p>
      </div>
    );
  }

  if (error || !trail) {
    return (
      <div className="trail-details-error">
        <h2>Error loading trail</h2>
        <p>{error || 'Trail not found'}</p>
        <button onClick={() => router.back()} className="back-btn">
          Go Back
        </button>
      </div>
    );
  }

  return (
    <div className="trail-details-container">
      {/* Header */}
      <div className="trail-header">
        <button onClick={() => router.back()} className="back-button">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
            <path d="M19 12H5M12 19l-7-7 7-7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
          Back
        </button>
        <button onClick={toggleFavorite} className={`favorite-button ${isFavorite ? 'favorited' : ''}`}>
          <svg width="20" height="20" viewBox="0 0 24 24" fill={isFavorite ? 'currentColor' : 'none'}>
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z" stroke="currentColor" strokeWidth="2"/>
          </svg>
        </button>
      </div>

      {/* Main Content */}
      <div className="trail-content">
        {/* Photo Gallery */}
        <div className="photo-gallery">
          {trail.photos && trail.photos.length > 0 ? (
            <>
              <div className="main-photo">
                <img 
                  src={trail.photos[activePhotoIndex].url} 
                  alt={trail.photos[activePhotoIndex].caption || trail.name}
                />
              </div>
              {trail.photos.length > 1 && (
                <div className="photo-thumbnails">
                  {trail.photos.map((photo, index) => (
                    <button
                      key={photo.id}
                      className={`thumbnail ${index === activePhotoIndex ? 'active' : ''}`}
                      onClick={() => setActivePhotoIndex(index)}
                    >
                      <img src={photo.url} alt={photo.caption || `Photo ${index + 1}`} />
                    </button>
                  ))}
                </div>
              )}
            </>
          ) : (
            <div className="main-photo placeholder">
              <div className="placeholder-content">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none">
                  <path d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1m4 0h1m-6 4h8m-9-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                <p>No photos available</p>
              </div>
            </div>
          )}
        </div>

        {/* Trail Info */}
        <div className="trail-info">
          <div className="trail-header-info">
            <h1>{trail.name}</h1>
            <div className="trail-meta">
              <span className="destination">{trail.destination.name}, {trail.destination.region}</span>
              <div className="rating">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                </svg>
                <span>{trail.rating}</span>
              </div>
            </div>
          </div>

          <div className="trail-stats">
            <div className="stat">
              <span className="stat-label">Difficulty</span>
              <span className={`stat-value difficulty ${getDifficultyClass(trail.difficulty)}`}>
                {getDifficultyText(trail.difficulty)}
              </span>
            </div>
            <div className="stat">
              <span className="stat-label">Length</span>
              <span className="stat-value">{trail.length} km</span>
            </div>
            <div className="stat">
              <span className="stat-label">Time</span>
              <span className="stat-value">{formatTime(trail.estimatedTime)}</span>
            </div>
          </div>

          <div className="activities">
            <h3>Activities</h3>
            <div className="activity-tags">
              {trail.activities.map((activity, index) => (
                <span key={index} className="activity-tag">{activity}</span>
              ))}
            </div>
          </div>

          <div className="description">
            <h3>Description</h3>
            <p>{trail.description}</p>
          </div>

          {trail.coordinates && trail.coordinates.length > 0 && (
            <div className="trail-map">
              <h3>Trail Route</h3>
              <div className="map-placeholder">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none">
                  <path d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-1.447-.894L15 4m0 13V4m-6 3l6-3" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                <p>Interactive map coming soon</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
} 