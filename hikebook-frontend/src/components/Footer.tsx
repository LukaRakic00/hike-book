'use client';
import './Footer.css';

export default function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-container">
        {/* Top section with logo and navigation */}
        <div className="footer-top">
          <div>
            <div className="footer-logo">
              <div className="footer-logo-icon">H</div>
              <div className="footer-logo-text">Hike&Book</div>
            </div>
          </div>
          
          <div className="footer-nav">
            <div>
              <h3>Explore</h3>
              <ul className="footer-nav-links">
                <li><a href="#">Countries</a></li>
                <li><a href="#">Regions</a></li>
                <li><a href="#">Cities</a></li>
                <li><a href="#">Parks</a></li>
                <li><a href="#">Trails</a></li>
                <li><a href="#">Points of Interest</a></li>
                <li><a href="#">Trail Features</a></li>
              </ul>
            </div>
            
            <div>
              <h3>Maps</h3>
              <ul className="footer-nav-links">
                <li><a href="#">My maps</a></li>
                <li><a href="#">Create map</a></li>
                <li><a href="#">Print maps</a></li>
                <li><a href="#">Route converter</a></li>
              </ul>
            </div>
            
            <div>
              <h3>Company</h3>
              <ul className="footer-nav-links">
                <li><a href="#">About</a></li>
                <li><a href="#">Jobs</a></li>
                <li><a href="#">Press</a></li>
                <li><a href="#">Ambassadors</a></li>
                <li><a href="#">Affiliates</a></li>
                <li><a href="#">Influencers</a></li>
              </ul>
            </div>
            
            <div>
              <h3>Community</h3>
              <ul className="footer-nav-links">
                <li><a href="#">Support</a></li>
                <li><a href="#">Gift membership</a></li>
                <li><a href="#">Hike&Book Gear</a></li>
              </ul>
            </div>
          </div>
        </div>
        
        {/* Middle section with apps, partners, and social */}
        <div className="footer-middle">
          <div className="footer-section">
            <h4>An app for the outdoors</h4>
            <div className="footer-app-buttons">
              <a href="#" className="app-button">
                <div className="app-button-icon">A</div>
                Download on the App Store
              </a>
              <a href="#" className="app-button">
                <div className="app-button-icon">G</div>
                GET IT ON Google Play
              </a>
            </div>
          </div>
          
          <div className="footer-section">
            <h4>Members for the planet</h4>
            <div className="footer-partners">
              <div className="partner-logo" title="1% FOR THE PLANET">1%</div>
              <div className="partner-logo" title="Leave No Trace">LNT</div>
              <div className="partner-logo" title="ONETREEPLANTED">🌳</div>
            </div>
          </div>
          
          <div className="footer-section">
            <h4>Connect with us</h4>
            <div className="footer-social">
              <div className="social-icon" title="Instagram">
                <svg viewBox="0 0 24 24">
                  <path d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z"/>
                </svg>
              </div>
              <div className="social-icon" title="TikTok">
                <svg viewBox="0 0 24 24">
                  <path d="M12.525.02c1.31-.02 2.61-.01 3.91-.02.08 1.53.63 3.09 1.75 4.17 1.12 1.11 2.7 1.62 4.24 1.79v4.03c-1.44-.05-2.89-.35-4.2-.97-.57-.26-1.1-.59-1.62-.93-.01 2.92.01 5.84-.02 8.75-.08 1.4-.54 2.79-1.35 3.94-1.31 1.92-3.58 3.17-5.91 3.21-1.43.08-2.86-.31-4.08-1.03-2.02-1.19-3.44-3.37-3.65-5.71-.02-.5-.03-1-.01-1.49.18-1.9 1.12-3.72 2.58-4.96 1.66-1.44 3.98-2.13 6.15-1.72.02 1.48-.04 2.96-.04 4.44-.99-.32-2.15-.23-3.02.37-.63.41-1.11 1.04-1.36 1.75-.21.51-.15 1.07-.14 1.61.24 1.64 1.82 3.02 3.5 2.87 1.12-.01 2.19-.66 2.77-1.61.19-.33.4-.67.41-1.06.1-1.79.06-3.57.07-5.36.01-4.03-.01-8.05.02-12.07z"/>
                </svg>
              </div>
              <div className="social-icon" title="Facebook">
                <svg viewBox="0 0 24 24">
                  <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
                </svg>
              </div>
              <div className="social-icon" title="X (Twitter)">
                <svg viewBox="0 0 24 24">
                  <path d="M18.244 2.25h3.308l-7.227 8.26 8.502 11.24H16.17l-5.214-6.817L4.99 21.75H1.68l7.73-8.835L1.254 2.25H8.08l4.713 6.231zm-1.161 17.52h1.833L7.084 4.126H5.117z"/>
                </svg>
              </div>
              <div className="social-icon" title="LinkedIn">
                <svg viewBox="0 0 24 24">
                  <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/>
                </svg>
              </div>
            </div>
            
            <div className="language-selector">
              <span>English (US)</span>
              <svg viewBox="0 0 24 24">
                <path d="M7 10l5 5 5-5z"/>
              </svg>
            </div>
          </div>
        </div>
        
        {/* Bottom section with copyright */}
        <div className="footer-bottom">
          <div className="footer-copyright">
            © 2010-{currentYear} Hike&Book, LLC
          </div>
          <div className="footer-trademarks">
            HIKE&BOOK®, Hike&Book Mountain Design, and FIND YOUR OUTSIDE™ are registered trademarks of Hike&Book, LLC.
          </div>
        </div>
      </div>
    </footer>
  );
} 