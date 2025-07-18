'use client';
import { useState, useEffect, useRef } from 'react';
import { apiService, Activity } from '../services/api';
import './BrowseByActivity.css';

export default function BrowseByActivity() {
  const [activities, setActivities] = useState<Activity[]>([]);
  const [selectedActivity, setSelectedActivity] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [canScrollLeft, setCanScrollLeft] = useState(false);
  const [canScrollRight, setCanScrollRight] = useState(true);
  const scrollContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    loadActivities();
  }, []);

  const loadActivities = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await apiService.getActivities();
      if (response.success) {
        setActivities(response.data);
      } else {
        setError(response.message || 'Failed to load activities');
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load activities');
    } finally {
      setLoading(false);
    }
  };

  const handleActivityClick = (activityId: number) => {
    setSelectedActivity(selectedActivity === activityId ? null : activityId);
    // Here you can add logic for filtering trails by activity
    // You can call API with activity_id parameter
  };

  const checkScrollButtons = () => {
    if (scrollContainerRef.current) {
      const { scrollLeft, scrollWidth, clientWidth } = scrollContainerRef.current;
      setCanScrollLeft(scrollLeft > 0);
      setCanScrollRight(scrollLeft < scrollWidth - clientWidth - 1);
    }
  };

  const scrollLeft = () => {
    if (scrollContainerRef.current) {
      scrollContainerRef.current.scrollBy({ left: -300, behavior: 'smooth' });
    }
  };

  const scrollRight = () => {
    if (scrollContainerRef.current) {
      scrollContainerRef.current.scrollBy({ left: 300, behavior: 'smooth' });
    }
  };

  useEffect(() => {
    const container = scrollContainerRef.current;
    if (container) {
      container.addEventListener('scroll', checkScrollButtons);
      checkScrollButtons();
      
      return () => {
        container.removeEventListener('scroll', checkScrollButtons);
      };
    }
  }, []);

  if (loading) {
    return (
      <div className="browse-by-activity">
        <div className="browse-by-activity-container">
          <div className="browse-by-activity-header">
            <h2 className="browse-by-activity-title">Browse by activity</h2>
            <p className="browse-by-activity-subtitle">Find trails that match your interests</p>
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
      <div className="browse-by-activity">
        <div className="browse-by-activity-container">
          <div className="browse-by-activity-header">
            <h2 className="browse-by-activity-title">Browse by activity</h2>
            <p className="browse-by-activity-subtitle">Find trails that match your interests</p>
          </div>
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <p style={{ color: '#ef4444', fontSize: '1.1rem' }}>{error}</p>
            <button 
              onClick={loadActivities}
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
    <div className="browse-by-activity">
      <div className="browse-by-activity-container">
        <div className="browse-by-activity-header">
          <h2 className="browse-by-activity-title">Browse by activity</h2>
          <p className="browse-by-activity-subtitle">Find trails that match your interests</p>
        </div>
        
        <div className="activities-container">
          <button 
            className={`scroll-button-left ${!canScrollLeft ? 'disabled' : ''}`}
            onClick={scrollLeft}
            disabled={!canScrollLeft}
          >
            <svg viewBox="0 0 24 24">
              <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"/>
            </svg>
          </button>
          
          <div 
            className="activities-list"
            ref={scrollContainerRef}
          >
            {Array.isArray(activities) && activities.map((activity) => (
              <div
                key={activity.id}
                className={`activity-item ${selectedActivity === activity.id ? 'active' : ''}`}
                onClick={() => handleActivityClick(activity.id)}
              >
                <div className="activity-image">
                  <span style={{ display: 'block', fontSize: '2rem' }}>
                    {activity.emoji}
                  </span>
                </div>
                <div className="activity-text">{activity.name}</div>
              </div>
            ))}
          </div>
          
          <button 
            className={`scroll-button-right ${!canScrollRight ? 'disabled' : ''}`}
            onClick={scrollRight}
            disabled={!canScrollRight}
          >
            <svg viewBox="0 0 24 24">
              <path d="M8.59 16.59L13.17 12 8.59 7.41 10 6l6 6-6 6-1.41-1.41z"/>
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
} 