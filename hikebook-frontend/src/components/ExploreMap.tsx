'use client';
import { useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './ExploreMap.css';

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

interface ExploreMapProps {
  destinations: Destination[];
  selectedDestination: Destination | null;
  onDestinationClick: (destinationId: number) => void;
  onTrailClick: (trailId: number) => void;
}

export default function ExploreMap({ 
  destinations, 
  selectedDestination, 
  onDestinationClick, 
  onTrailClick 
}: ExploreMapProps) {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstanceRef = useRef<L.Map | null>(null);
  const markersRef = useRef<L.Marker[]>([]);
  const trailMarkersRef = useRef<L.Marker[]>([]);

  useEffect(() => {
    if (!mapRef.current || typeof window === 'undefined') return;

    // Initialize map
    const map = L.map(mapRef.current).setView([44.7866, 20.4489], 10); // Belgrade center
    mapInstanceRef.current = map;

    // Add tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors',
      maxZoom: 18,
    }).addTo(map);

    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, []);

  useEffect(() => {
    if (!mapInstanceRef.current || typeof window === 'undefined') return;

    // Clear existing markers
    markersRef.current.forEach(marker => marker.remove());
    markersRef.current = [];

    // Add destination markers
    if (!Array.isArray(destinations)) {
      console.warn('Destinations is not an array:', destinations);
      return;
    }
    
    destinations.forEach(destination => {
      // Validate destination coordinates
      if (!destination || 
          typeof destination.latitude !== 'number' || 
          typeof destination.longitude !== 'number' ||
          isNaN(destination.latitude) || 
          isNaN(destination.longitude)) {
        console.warn('Invalid destination coordinates:', destination);
        return;
      }

      const marker = L.marker([destination.latitude, destination.longitude], {
        icon: createDestinationIcon(destination.trailCount),
      }).addTo(mapInstanceRef.current!);

      // Create popup content
      const popupContent = createDestinationPopup(destination);
      marker.bindPopup(popupContent);

      // Add click handler
      marker.on('click', () => {
        onDestinationClick(destination.id);
      });

      markersRef.current.push(marker);
    });
  }, [destinations, onDestinationClick]);

  useEffect(() => {
    if (!mapInstanceRef.current || !selectedDestination || typeof window === 'undefined') return;

    // Clear existing trail markers
    trailMarkersRef.current.forEach(marker => marker.remove());
    trailMarkersRef.current = [];

    // Add trail markers for selected destination
    if (selectedDestination.trails && Array.isArray(selectedDestination.trails)) {
      selectedDestination.trails.forEach(trail => {
        // Validate trail coordinates
        if (!trail || 
            typeof trail.latitude !== 'number' || 
            typeof trail.longitude !== 'number' ||
            isNaN(trail.latitude) || 
            isNaN(trail.longitude)) {
          console.warn('Invalid trail coordinates:', trail);
          return;
        }

        const marker = L.marker([trail.latitude, trail.longitude], {
          icon: createTrailIcon(trail.difficulty),
        }).addTo(mapInstanceRef.current!);

        // Create popup content
        const popupContent = createTrailPopup(trail);
        marker.bindPopup(popupContent);

        // Add click handler
        marker.on('click', () => {
          onTrailClick(trail.id);
        });

        trailMarkersRef.current.push(marker);
      });

      // Fit map to show all trails
      if (selectedDestination.trails.length > 0) {
        const group = L.featureGroup(trailMarkersRef.current);
        mapInstanceRef.current.fitBounds(group.getBounds().pad(0.1));
      }
    }
  }, [selectedDestination, onTrailClick]);

  const createDestinationIcon = (trailCount: number) => {
    const count = typeof trailCount === 'number' && !isNaN(trailCount) ? trailCount : 0;
    return L.divIcon({
      className: 'destination-marker',
      html: `
        <div class="marker-content">
          <div class="marker-count">${count}</div>
          <div class="marker-label">trails</div>
        </div>
      `,
      iconSize: [40, 40],
      iconAnchor: [20, 20],
    });
  };

  const createTrailIcon = (difficulty: string) => {
    const colors = {
      easy: '#22c55e',
      moderate: '#f59e0b',
      hard: '#ef4444',
    };
    
    const validDifficulty = typeof difficulty === 'string' && colors[difficulty as keyof typeof colors] ? difficulty : 'moderate';
    
    return L.divIcon({
      className: 'trail-marker',
      html: `
        <div class="trail-marker-content" style="background-color: ${colors[validDifficulty as keyof typeof colors]}">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
      `,
      iconSize: [24, 24],
      iconAnchor: [12, 12],
    });
  };

  const createDestinationPopup = (destination: Destination) => {
    const name = destination?.name || 'Unknown Destination';
    const region = destination?.region || 'Unknown Region';
    const trailCount = typeof destination?.trailCount === 'number' ? destination.trailCount : 0;
    const id = destination?.id || 0;
    
    return `
      <div class="destination-popup">
        <h3>${name}</h3>
        <p class="region">${region}</p>
        <p class="trail-count">${trailCount} trails available</p>
        <button class="explore-btn" onclick="window.exploreDestination(${id})">
          Explore trails
        </button>
      </div>
    `;
  };

  const createTrailPopup = (trail: TrailMarkerData) => {
    const name = trail?.name || 'Unknown Trail';
    const difficulty = trail?.difficulty || 'moderate';
    const length = typeof trail?.length === 'number' ? trail.length : 0;
    const rating = typeof trail?.rating === 'number' ? trail.rating : 0;
    const activities = Array.isArray(trail?.activities) ? trail.activities : [];
    const id = trail?.id || 0;
    
    return `
      <div class="trail-popup">
        <h4>${name}</h4>
        <div class="trail-info">
          <span class="difficulty ${difficulty}">${difficulty}</span>
          <span class="length">${length}km</span>
          <span class="rating">★ ${rating}</span>
        </div>
        <div class="activities">
          ${activities.map(activity => `<span class="activity-tag">${activity}</span>`).join('')}
        </div>
        <button class="view-trail-btn" onclick="window.viewTrail(${id})">
          View details
        </button>
      </div>
    `;
  };

  // Add global functions for popup buttons
  useEffect(() => {
    if (typeof window === 'undefined') return;
    
    (window as unknown as Record<string, unknown>).exploreDestination = onDestinationClick;
    (window as unknown as Record<string, unknown>).viewTrail = onTrailClick;

    return () => {
      delete (window as unknown as Record<string, unknown>).exploreDestination;
      delete (window as unknown as Record<string, unknown>).viewTrail;
    };
  }, [onDestinationClick, onTrailClick]);

  return <div ref={mapRef} className="explore-map" />;
} 