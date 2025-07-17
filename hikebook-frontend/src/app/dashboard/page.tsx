'use client';
import { useAuth } from '../../hooks/useAuth';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '../../components/Navbar';
import HeroSection from '../../components/HeroSection';
import LocalFavorites from '../../components/LocalFavorites';
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
      <Navbar />
      <HeroSection />
      <LocalFavorites />
      <BrowseByActivity />
      <Footer />
    </div>
  );
} 