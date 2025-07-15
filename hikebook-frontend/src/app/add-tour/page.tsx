'use client';
import { useAuth } from '../../hooks/useAuth';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Navbar from '../../components/Navbar';

export default function AddTour() {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.push('/auth');
    }
  }, [isAuthenticated, isLoading, router]);

  if (isLoading) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <p>Loading...</p>
      </div>
    );
  }

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div>
      <Navbar />
      <div style={{ padding: '40px 20px', maxWidth: '1200px', margin: '0 auto' }}>
        <h1 style={{ textAlign: 'center', marginBottom: '20px' }}>Add Tour</h1>
        <p style={{ textAlign: 'center', fontSize: '1.1rem', color: '#666' }}>
          This is a test page - we will upgrade it later
        </p>
        <div style={{ 
          background: 'white', 
          padding: '40px', 
          borderRadius: '12px', 
          marginTop: '30px',
          textAlign: 'center',
          boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
        }}>
          <h2>Create Your Hiking Tour</h2>
          <p>Share your hiking experiences and create amazing tours for others.</p>
          <p>Coming soon with tour creation tools and photo uploads!</p>
        </div>
      </div>
    </div>
  );
} 