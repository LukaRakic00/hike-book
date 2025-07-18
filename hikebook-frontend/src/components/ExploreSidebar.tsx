'use client';
import { useState } from 'react';
import './ExploreSidebar.css';

interface Destination {
  id: number;
  name: string;
  region: string;
  latitude: number;
  longitude: number;
  trailCount: number;
  imageUrl: string;
  trails?: TrailMarkerData[];
}

interface TrailMarkerData {
  id: number;
  name: string;
  latitude: number;
  longitude: number;
  difficulty: string;
  length: number;
  rating: number;
  mainImage: string;
  activities: string[];
}

interface Filters {
  activity: string;
  difficulty: string;
  minLength: string;
  maxLength: string;
}

interface ExploreSidebarProps {
  destinations: Destination[];
  selectedDestination: Destination | null;
  filters: Filters;
  onFilterChange: (filters: Filters) => void;
  onDestinationClick: (destinationId: number) => void;
  isOpen: boolean;
  onClose: () => void;
}

export default function ExploreSidebar({
  destinations,
  selectedDestination,
  filters,
  onFilterChange,
  onDestinationClick,
  isOpen,
  onClose
}: ExploreSidebarProps) {
  const [searchTerm, setSearchTerm] = useState('');

  const filteredDestinations = destinations.filter(destination =>
    destination.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    destination.region.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleFilterChange = (key: keyof Filters, value: string) => {
    onFilterChange({
      ...filters,
      [key]: value
    });
  };

  const clearFilters = () => {
    onFilterChange({
      activity: '',
      difficulty: '',
      minLength: '',
      maxLength: ''
    });
  };

  const hasActiveFilters = Object.values(filters).some(value => value !== '');

  return (
    <div className={`explore-sidebar ${isOpen ? 'open' : ''}`}>
      {/* Header */}
      <div className="sidebar-header">
        <div className="header-content">
          <h2>Explore trails</h2>
          <span className="destination-count">{destinations.length} destinations</span>
        </div>
        <button className="close-sidebar" onClick={onClose}>
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
            <path d="M18 6L6 18M6 6l12 12" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
        </button>
      </div>

      {/* Search */}
      <div className="search-section">
        <div className="search-input-wrapper">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
            <circle cx="11" cy="11" r="8" stroke="currentColor" strokeWidth="2"/>
            <path d="m21 21-4.35-4.35" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
          </svg>
          <input
            type="text"
            placeholder="Search destinations..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>
      </div>

      {/* Filters */}
      <div className="filters-section">
        <div className="filters-header">
          <h3>Filters</h3>
          {hasActiveFilters && (
            <button onClick={clearFilters} className="clear-filters">
              Clear all
            </button>
          )}
        </div>

        <div className="filter-group">
          <label>Activity</label>
          <select
            value={filters.activity}
            onChange={(e) => handleFilterChange('activity', e.target.value)}
            className="filter-select"
          >
            <option value="">All activities</option>
            <option value="hiking">Hiking</option>
            <option value="biking">Biking</option>
            <option value="running">Running</option>
            <option value="walking">Walking</option>
          </select>
        </div>

        <div className="filter-group">
          <label>Difficulty</label>
          <select
            value={filters.difficulty}
            onChange={(e) => handleFilterChange('difficulty', e.target.value)}
            className="filter-select"
          >
            <option value="">All difficulties</option>
            <option value="easy">Easy</option>
            <option value="moderate">Moderate</option>
            <option value="hard">Hard</option>
          </select>
        </div>

        <div className="filter-group">
          <label>Length (km)</label>
          <div className="length-inputs">
            <input
              type="number"
              placeholder="Min"
              value={filters.minLength}
              onChange={(e) => handleFilterChange('minLength', e.target.value)}
              className="length-input"
            />
            <span>-</span>
            <input
              type="number"
              placeholder="Max"
              value={filters.maxLength}
              onChange={(e) => handleFilterChange('maxLength', e.target.value)}
              className="length-input"
            />
          </div>
        </div>
      </div>

      {/* Destinations List */}
      <div className="destinations-section">
        <h3>Destinations</h3>
        <div className="destinations-list">
          {filteredDestinations.length === 0 ? (
            <div className="no-results">
              <p>No destinations found</p>
            </div>
          ) : (
            filteredDestinations.map(destination => (
              <DestinationCard
                key={destination.id}
                destination={destination}
                isSelected={selectedDestination?.id === destination.id}
                onClick={() => onDestinationClick(destination.id)}
              />
            ))
          )}
        </div>
      </div>
    </div>
  );
}

interface DestinationCardProps {
  destination: Destination;
  isSelected: boolean;
  onClick: () => void;
}

function DestinationCard({ destination, isSelected, onClick }: DestinationCardProps) {
  const handleImageError = (e: React.SyntheticEvent<HTMLImageElement, Event>) => {
    const target = e.target as HTMLImageElement;
    target.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iODAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA4MCA2MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjgwIiBoZWlnaHQ9IjYwIiBmaWxsPSIjRjNGNEY2Ii8+CjxwYXRoIGQ9Ik0yMCAyMEg2MFY0MEgyMFYyMFoiIGZpbGw9IiNEMUQ1REIiLz4KPHN2ZyB4PSIyOCIgeT0iMjgiIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIj4KPHBhdGggZD0iTTE0LjgyOCAxNC44MjhhNCA0IDAgMDEtNS42NTYgME05IDEwaDFtNCAwaDFtLTYgNGg4bS05LTJoLjAxTTIxIDEyYTkgOSAwIDExLTE4IDAgOSA5IDAgMDExOCAweiIgc3Ryb2tlPSIjNjc3NDhCIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPgo8L3N2Zz4KPC9zdmc+';
  };

  return (
    <div 
      className={`destination-card ${isSelected ? 'selected' : ''}`}
      onClick={onClick}
    >
      <div className="destination-image">
        <img
          src={destination.imageUrl || 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iODAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA4MCA2MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHJlY3Qgd2lkdGg9IjgwIiBoZWlnaHQ9IjYwIiBmaWxsPSIjRjNGNEY2Ii8+CjxwYXRoIGQ9Ik0yMCAyMEg2MFY0MEgyMFYyMFoiIGZpbGw9IiNEMUQ1REIiLz4KPHN2ZyB4PSIyOCIgeT0iMjgiIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIj4KPHBhdGggZD0iTTE0LjgyOCAxNC44MjhhNCA0IDAgMDEtNS42NTYgME05IDEwaDFtNCAwaDFtLTYgNGg4bS05LTJoLjAxTTIxIDEyYTkgOSAwIDExLTE4IDAgOSA5IDAgMDExOCAweiIgc3Ryb2tlPSIjNjc3NDhCIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIvPgo8L3N2Zz4KPC9zdmc+'}
          alt={destination.name}
          width={80}
          height={60}
          className="card-image"
          onError={handleImageError}
        />
      </div>
      <div className="destination-info">
        <h4 className="destination-name">{destination.name}</h4>
        <p className="destination-region">{destination.region}</p>
        <div className="destination-stats">
          <span className="trail-count">{destination.trailCount} trails</span>
        </div>
      </div>
      <div className="destination-arrow">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
          <path d="M9 18l6-6-6-6" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
      </div>
    </div>
  );
} 