'use client';
import { useAuth } from '../../hooks/useAuth';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import HeroSection from '../../components/HeroSection';
import LocalFavorites from '../../components/LocalFavorites';
import ExploreSection from '../../components/ExploreSection';
import BrowseByActivity from '../../components/BrowseByActivity';
import Footer from '../../components/Footer';
import './dashboard.css';

export default function Dashboard() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.replace('/auth');
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
      <HeroSection />
      <LocalFavorites />
      <ExploreSection />
      <BrowseByActivity />
      <Footer />
    </div>
  );
} 