'use client';
import { useAuth } from '../hooks/useAuth';
import Link from 'next/link';
import Image from 'next/image';
import { useState, useEffect, useRef } from 'react';
import './Navbar.css';

export default function Navbar() {
  const { user, isAuthenticated, signOut } = useAuth();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);
  const buttonRef = useRef<HTMLButtonElement>(null);

  // Close menu when clicking outside
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        menuRef.current &&
        !menuRef.current.contains(event.target as Node) &&
        buttonRef.current &&
        !buttonRef.current.contains(event.target as Node)
      ) {
        setIsMenuOpen(false);
      }
    }

    if (isMenuOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isMenuOpen]);

  // Close menu when pressing Escape key
  useEffect(() => {
    function handleEscapeKey(event: KeyboardEvent) {
      if (event.key === 'Escape') {
        setIsMenuOpen(false);
      }
    }

    if (isMenuOpen) {
      document.addEventListener('keydown', handleEscapeKey);
    }

    return () => {
      document.removeEventListener('keydown', handleEscapeKey);
    };
  }, [isMenuOpen]);

  if (!isAuthenticated || !user) {
    return null;
  }

  const handleSignOut = async () => {
    await signOut();
    window.location.href = '/auth';
  };

  const handleMenuToggle = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLinkClick = () => {
    setIsMenuOpen(false);
  };

  return (
    <nav className="navbar">
        <div className="navbar-container">
          {/* Left Navigation */}
          <div className="navbar-left">
            <Link href="/explore" className="navbar-link">
              Explore
            </Link>
            <Link href="/destinations" className="navbar-link">
              Destinations
            </Link>
            <Link href="/my-reservations" className="navbar-link">
              My Reservations
            </Link>
            <Link href="/favorite-tours" className="navbar-link">
              Favorite Tours
            </Link>
          </div>

          {/* Center Logo */}
          <div className="navbar-logo">
            <Link href="/dashboard">
              <div className="navbar-logo-icon">
                <Image 
                  src="/logoDesign/logoWhite.svg" 
                  alt="Hike&Book" 
                  width={32} 
                  height={32} 
                  className="navbar-logo-img"
                />
              </div>
              <span className="navbar-logo-text">Hike&Book</span>
            </Link>
          </div>

          {/* Right Navigation */}
          <div className="navbar-right">
            <Link href="/add-tour" className="navbar-link">
              Add Tour
            </Link>
            <div className="navbar-user">
              <span className="navbar-user-name">Account</span>
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
            ref={buttonRef}
            className="navbar-mobile-btn"
            onClick={handleMenuToggle}
            aria-label="Toggle mobile menu"
          >
            <span className={`navbar-mobile-icon ${isMenuOpen ? 'open' : ''}`}></span>
          </button>
        </div>

        {/* Mobile Menu */}
        {isMenuOpen && (
          <div ref={menuRef} className="navbar-mobile-menu">
            <Link href="/explore" className="navbar-mobile-link" onClick={handleLinkClick}>
              Explore
            </Link>
            <Link href="/destinations" className="navbar-mobile-link" onClick={handleLinkClick}>
              Destinations
            </Link>
            <Link href="/my-reservations" className="navbar-mobile-link" onClick={handleLinkClick}>
              My Reservations
            </Link>
            <Link href="/favorite-tours" className="navbar-mobile-link" onClick={handleLinkClick}>
              Favorite Tours
            </Link>
            <Link href="/add-tour" className="navbar-mobile-link" onClick={handleLinkClick}>
              Add Tour
            </Link>
            <button 
              onClick={() => {
                handleLinkClick();
                handleSignOut();
              }}
              className="navbar-mobile-signout"
            >
              Sign Out
            </button>
          </div>
        )}
      </nav>
  );
} 