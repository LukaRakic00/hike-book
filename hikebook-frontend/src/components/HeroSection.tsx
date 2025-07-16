'use client';
import { useState, useEffect } from 'react';
import Image from 'next/image';
import './HeroSection.css';

const heroImages = [
  '/heroSectionPic/s01.jpg',
  '/heroSectionPic/s02.jpg', 
  '/heroSectionPic/s03.jpg'
];

export default function HeroSection() {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  // Auto-rotate images every 5 seconds
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImageIndex((prevIndex) => 
        prevIndex === heroImages.length - 1 ? 0 : prevIndex + 1
      );
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="hero-section">
      {/* Background Images */}
      <div className="hero-background">
        {heroImages.map((image, index) => (
          <div
            key={image}
            className={`hero-image-container ${index === currentImageIndex ? 'active' : ''}`}
          >
            <Image
              src={image}
              alt={`Hero image ${index + 1}`}
              fill
              className="hero-image"
              priority={index === 0}
            />
            <div className="hero-overlay"></div>
          </div>
        ))}
      </div>

      {/* Content */}
      <div className="hero-content">
        <div className="hero-text">
          <h1 className="hero-title">Find your adventure</h1>
          <p className="hero-subtitle">Discover amazing hiking trails and outdoor experiences</p>
        </div>

        {/* Search Bar */}
        <div className="hero-search">
          <div className="search-container">
            <div className="search-icon">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                <circle cx="11" cy="11" r="8" stroke="currentColor" strokeWidth="2"/>
                <path d="m21 21-4.35-4.35" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </div>
            <input
              type="text"
              placeholder="Search by city, park, or trail name"
              className="search-input"
            />
            <button className="search-button">
              Search
            </button>
          </div>
        </div>

        {/* Explore Link */}
        <div className="hero-explore">
          <a href="/destinations" className="explore-link">
            Explore nearby trails
          </a>
        </div>
      </div>

      {/* Image Indicators */}
      <div className="hero-indicators">
        {heroImages.map((_, index) => (
          <button
            key={index}
            className={`indicator ${index === currentImageIndex ? 'active' : ''}`}
            onClick={() => setCurrentImageIndex(index)}
          />
        ))}
      </div>
    </div>
  );
} 