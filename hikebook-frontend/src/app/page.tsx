'use client';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import HeroSection from '@/components/HeroSection';
import LocalFavorites from '@/components/LocalFavorites';
import BrowseByActivity from '@/components/BrowseByActivity';
import Footer from '@/components/Footer';

export default function Home() {
  const router = useRouter();
  
  // For now we'll show the main page instead of redirecting to auth
  // useEffect(() => {
  //   router.replace('/auth');
  // }, [router]);
  
  return (
    <main>
      <HeroSection />
      <LocalFavorites />
      <BrowseByActivity />
      <Footer />
    </main>
  );
}
