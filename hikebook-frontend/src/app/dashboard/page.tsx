'use client';
import { useAuth } from '../../hooks/useAuth';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '../../components/Navbar';
import './dashboard.css';

export default function Dashboard() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.push('/auth');
    }
  }, [isAuthenticated, isLoading, router]);

  if (isLoading) {
    return (
      <div className="dashboard-loading">
        <div className="loading-spinner"></div>
        <p>Loading...</p>
      </div>
    );
  }

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="dashboard">
      <Navbar />
      <div className="dashboard-content">
        <div className="dashboard-header">
          <h1>Welcome to Hike&Book Dashboard</h1>
          <p>This is a test page - we will upgrade it later</p>
        </div>
        
        <div className="dashboard-grid">
          <div className="dashboard-card">
            <h3>Destinations</h3>
            <p>Explore amazing hiking destinations around the world</p>
            <button className="dashboard-btn">View Destinations</button>
          </div>
          
          <div className="dashboard-card">
            <h3>My Reservations</h3>
            <p>Manage your upcoming hiking tours and reservations</p>
            <button className="dashboard-btn">View Reservations</button>
          </div>
          
          <div className="dashboard-card">
            <h3>Favorite Tours</h3>
            <p>Quick access to your saved and favorite tours</p>
            <button className="dashboard-btn">View Favorites</button>
          </div>
          
          <div className="dashboard-card">
            <h3>Add Tour</h3>
            <p>Create and share your own hiking tour experiences</p>
            <button className="dashboard-btn">Add New Tour</button>
          </div>
        </div>
      </div>
    </div>
  );
} 