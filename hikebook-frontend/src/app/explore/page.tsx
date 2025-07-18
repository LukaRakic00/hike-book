'use client';
import { useState, useEffect } from 'react';
import { useAuth } from '../../hooks/useAuth';
import { useRouter } from 'next/navigation';
import dynamic from 'next/dynamic';
import ExploreSidebar from '../../components/ExploreSidebar';

const ExploreMap = dynamic(() => import('../../components/ExploreMap'), {
  ssr: false,
  loading: () => (
    <div className="map-loading">
      <div className="loading-spinner"></div>
      <p>Loading map...</p>
    </div>
  )
});
import LoadingSpinner from '../../components/LoadingSpinner';
import './explore.css';

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

interface ExploreResponse {
  destinations: Destination[];
  availableActivities: string[];
  availableDifficulties: string[];
}

export default function ExplorePage() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();
  const [exploreData, setExploreData] = useState<ExploreResponse | null>(null);
  const [selectedDestination, setSelectedDestination] = useState<Destination | null>(null);
  const [isLoadingData, setIsLoadingData] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [filters, setFilters] = useState({
    activity: '',
    difficulty: '',
    minLength: '',
    maxLength: ''
  });
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.replace('/auth');
    }
  }, [isAuthenticated, isLoading, router]);

  useEffect(() => {
    fetchExploreData();
  }, []);

  const fetchExploreData = async () => {
    try {
      setIsLoadingData(true);
      const response = await fetch('http://localhost:8080/api/explore');
      if (!response.ok) {
        throw new Error('Failed to fetch explore data');
      }
      const data = await response.json();
      setExploreData(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setIsLoadingData(false);
    }
  };

  const handleDestinationClick = async (destinationId: number) => {
    try {
      const response = await fetch(`http://localhost:8080/api/explore/${destinationId}`);
      if (!response.ok) {
        throw new Error('Failed to fetch destination details');
      }
      const data = await response.json();
      setSelectedDestination(data.destination);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    }
  };

  const handleTrailClick = (trailId: number) => {
    // Navigate to trail details page
    router.push(`/explore/trail/${trailId}`);
  };

  const handleFilterChange = (newFilters: typeof filters) => {
    setFilters(newFilters);
    // TODO: Implement filtering logic
  };

  if (isLoading || !isAuthenticated) {
    return (
      <div className="explore-loading">
        <LoadingSpinner size="lg" />
        <p>Loading...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="explore-error">
        <h2>Error loading explore data</h2>
        <p>{error}</p>
        <button onClick={fetchExploreData} className="retry-btn">
          Try Again
        </button>
      </div>
    );
  }

  return (
    <div className="explore-container">
      {/* Mobile Menu Button */}
      <button 
        className="explore-sidebar-toggle"
        onClick={() => setIsSidebarOpen(!isSidebarOpen)}
        aria-label="Toggle sidebar"
      >
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
          <path d="M3 12h18M3 6h18M3 18h18" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
        </svg>
      </button>

      {/* Sidebar */}
      <ExploreSidebar
        destinations={exploreData?.destinations || []}
        selectedDestination={selectedDestination}
        filters={filters}
        onFilterChange={handleFilterChange}
        onDestinationClick={handleDestinationClick}
        isOpen={isSidebarOpen}
        onClose={() => setIsSidebarOpen(false)}
      />

      {/* Map */}
      <div className="explore-map-container">
        {isLoadingData ? (
          <div className="map-loading">
            <LoadingSpinner size="lg" />
            <p>Loading map data...</p>
          </div>
        ) : (
          <ExploreMap
            destinations={exploreData?.destinations || []}
            selectedDestination={selectedDestination}
            onDestinationClick={handleDestinationClick}
            onTrailClick={handleTrailClick}
          />
        )}
      </div>
    </div>
  );
} 