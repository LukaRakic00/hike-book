'use client';
import { useAuth } from '../hooks/useAuth';
import Link from 'next/link';
import Image from 'next/image';
import { useState } from 'react';
import './Navbar.css';

export default function Navbar() {
  const { user, isAuthenticated, signOut } = useAuth();
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  if (!isAuthenticated || !user) {
    return null;
  }

  const handleSignOut = async () => {
    await signOut();
    window.location.href = '/auth';
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        {/* Logo */}
        <div className="navbar-logo">
          <Link href="/dashboard">
            <Image 
              src="/logo.svg" 
              alt="Hike&Book" 
              width={40} 
              height={40} 
              className="navbar-logo-img"
            />
            <span className="navbar-logo-text">Hike&Book</span>
          </Link>
        </div>

        {/* Desktop Navigation */}
        <div className="navbar-nav">
          <Link href="/destinations" className="navbar-link">
            Destinations
          </Link>
          <Link href="/my-reservations" className="navbar-link">
            My Reservations
          </Link>
          <Link href="/favorite-tours" className="navbar-link">
            Favorite Tours
          </Link>
          <Link href="/add-tour" className="navbar-link">
            Add Tour
          </Link>
        </div>

        {/* User Profile Section */}
        <div className="navbar-profile">
          <div className="navbar-user">
            <span className="navbar-user-name">Welcome, {user.name}</span>
            <button 
              onClick={handleSignOut}
              className="navbar-signout-btn"
            >
              Sign Out
            </button>
          </div>
        </div>

        {/* Mobile Menu Button */}
        <button 
          className="navbar-mobile-btn"
          onClick={() => setIsMenuOpen(!isMenuOpen)}
        >
          <span className="navbar-mobile-icon"></span>
        </button>
      </div>

      {/* Mobile Menu */}
      {isMenuOpen && (
        <div className="navbar-mobile-menu">
          <Link href="/destinations" className="navbar-mobile-link">
            Destinations
          </Link>
          <Link href="/my-reservations" className="navbar-mobile-link">
            My Reservations
          </Link>
          <Link href="/favorite-tours" className="navbar-mobile-link">
            Favorite Tours
          </Link>
          <Link href="/add-tour" className="navbar-mobile-link">
            Add Tour
          </Link>
          <button 
            onClick={handleSignOut}
            className="navbar-mobile-signout"
          >
            Sign Out
          </button>
        </div>
      )}
    </nav>
  );
} 