'use client';
import Link from 'next/link';
import './ExploreSection.css';

export default function ExploreSection() {
  return (
    <div className="explore-section">
      <div className="explore-section-container">
        <div className="explore-content">
          <div className="explore-text">
            <h2>Explore trails on the map</h2>
            <p>Discover amazing hiking trails, biking routes, and outdoor adventures near you. Use our interactive map to find the perfect trail for your next adventure.</p>
            <div className="explore-features">
              <div className="feature">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                  <path d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-1.447-.894L15 4m0 13V4m-6 3l6-3" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                <span>Interactive map with trail locations</span>
              </div>
              <div className="feature">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                  <path d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                <span>Filter by difficulty and activity</span>
              </div>
              <div className="feature">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                  <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                </svg>
                <span>Detailed trail information and ratings</span>
              </div>
            </div>
          </div>
          <div className="explore-visual">
            <div className="map-preview">
              <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                <path d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-1.447-.894L15 4m0 13V4m-6 3l6-3" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </div>
            <Link href="/explore" className="explore-cta">
              <span>Open Map</span>
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                <path d="M5 12h14M12 5l7 7-7 7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
} 