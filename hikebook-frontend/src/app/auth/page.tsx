/* eslint-disable @next/next/no-img-element */
'use client';
import { useState, useEffect, useRef } from 'react';
import { useAuth } from '../../hooks/useAuth';
import LoadingSpinner from '../../components/LoadingSpinner';
import './auth.css';

const images = [
  '/loginPic/mountain01.jpg',
  '/loginPic/mountain03.jpg',
  '/loginPic/mountain04.jpg',
  '/loginPic/mountain02.jpg',
];

function useIsMobile() {
  const [isMobile, setIsMobile] = useState(false);
  useEffect(() => {
    function onResize() {
      setIsMobile(window.innerWidth <= 600);
    }
    onResize();
    window.addEventListener('resize', onResize);
    return () => window.removeEventListener('resize', onResize);
  }, []);
  return isMobile;
}

function LockIcon() {
  return (
    <svg width="20" height="20" fill="none" viewBox="0 0 24 24"><path stroke="#6b7280" strokeWidth="2" d="M7 10V7a5 5 0 0110 0v3"/><rect width="16" height="10" x="4" y="10" fill="#fff" stroke="#6b7280" strokeWidth="2" rx="2"/><circle cx="12" cy="15" r="1.5" fill="#6b7280"/></svg>
  );
}
function EyeIcon({ open }: { open: boolean }) {
  return open ? (
    <svg width="20" height="20" fill="none" viewBox="0 0 24 24"><path stroke="#6b7280" strokeWidth="2" d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"/><circle cx="12" cy="12" r="3" stroke="#6b7280" strokeWidth="2"/></svg>
  ) : (
    <svg width="20" height="20" fill="none" viewBox="0 0 24 24"><path stroke="#6b7280" strokeWidth="2" d="M17.94 17.94A10.94 10.94 0 0112 19c-7 0-11-7-11-7a21.77 21.77 0 015.06-6.06M1 1l22 22"/><circle cx="12" cy="12" r="3" stroke="#6b7280" strokeWidth="2"/></svg>
  );
}
function UserIcon() {
  return (
    <svg width="20" height="20" fill="none" viewBox="0 0 24 24"><circle cx="12" cy="8" r="4" stroke="#6b7280" strokeWidth="2"/><path stroke="#6b7280" strokeWidth="2" d="M4 20c0-2.5 3.5-4 8-4s8 1.5 8 4"/></svg>
  );
}
function PhoneIcon() {
  return (
    <svg width="20" height="20" fill="none" viewBox="0 0 24 24"><path stroke="#6b7280" strokeWidth="2" d="M22 16.92V19a2 2 0 01-2.18 2A19.72 19.72 0 013 5.18 2 2 0 015 3h2.09a1 1 0 011 .75l1.1 4.4a1 1 0 01-.29.95l-1.27 1.27a16 16 0 006.29 6.29l1.27-1.27a1 1 0 01.95-.29l4.4 1.1a1 1 0 01.75 1V19a2 2 0 01-2 2z"/></svg>
  );
}

function Toast({ message, onClose }: { message: string; onClose: () => void }) {
  // Auto-hide after 3 seconds
  const timer = useRef<NodeJS.Timeout | null>(null);
  useEffect(() => {
    timer.current = setTimeout(onClose, 3000);
    return () => {
      if (timer.current) clearTimeout(timer.current);
    };
  }, [onClose]);
  return (
    <div style={{
      position: 'fixed',
      top: 24,
      left: '50%',
      transform: 'translateX(-50%)',
      background: '#222',
      color: '#fff',
      padding: '12px 28px',
      borderRadius: 8,
      fontWeight: 500,
      zIndex: 1000,
      boxShadow: '0 2px 12px rgba(0,0,0,0.15)'
    }}>
      {message}
    </div>
  );
}

export default function AuthSwitcher() {
  const isMobile = useIsMobile();
  const [mode, setMode] = useState<'signin' | 'signup'>('signin');
  const [imgIdx, setImgIdx] = useState(0);
  useEffect(() => {
    const interval = setInterval(() => setImgIdx((i) => (i + 1) % images.length), 3500);
    return () => clearInterval(interval);
  }, []);

  if (isMobile) {
    return <MobileAuthSwitcher mode={mode} setMode={setMode} imgIdx={imgIdx} />;
  }
  return (
    <div className="auth-root">
      {mode === 'signin' ? (
        <AuthSignIn imgIdx={imgIdx} onSwitch={() => setMode('signup')} />
      ) : (
        <AuthSignUp imgIdx={imgIdx} onSwitch={() => setMode('signin')} />
      )}
    </div>
  );
}

function AuthSignIn({ imgIdx, onSwitch }: { imgIdx: number; onSwitch: () => void }) {
  const { signIn, isLoading } = useAuth();
  const [form, setForm] = useState({ email: '', password: '', remember: false });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const response = await signIn({
        email: form.email,
        password: form.password,
      });
      if (response.success) {
        window.location.href = '/dashboard';
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Sign in failed');
    }
  };

  return (
    <div className="auth-flex">
      <div className="auth-img-col">
        <img src={images[imgIdx]} alt="mountain" className="auth-img" width={800} height={1200} style={{ objectFit: 'cover' }} />
        <div className="auth-img-overlay" />
      </div>
      <div className="auth-form-col">
        <div className="auth-logo-wrap">
          <img src="/logo.svg" alt="Hike&Book logo" className="auth-logo-centered" width={120} height={40} />
          <h1 className="auth-title-centered">Hike&Book</h1>
        </div>
        <form className="auth-form" onSubmit={handleSubmit}>
          {error && <div className="auth-error">{error}</div>}
          <div className="auth-field">
            <label htmlFor="email">Email</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon">@</span>
              <input id="email" name="email" type="email" placeholder="Enter your email" value={form.email} onChange={e => setForm(f => ({ ...f, email: e.target.value }))} required disabled={isLoading} />
            </div>
          </div>
          <div className="auth-field">
            <label htmlFor="password">Password</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon"><LockIcon /></span>
              <input id="password" name="password" type={showPassword ? 'text' : 'password'} placeholder="Enter your password" value={form.password} onChange={e => setForm(f => ({ ...f, password: e.target.value }))} required disabled={isLoading} />
              <button type="button" className="auth-eye-btn" onClick={() => setShowPassword(v => !v)} aria-label={showPassword ? 'Hide password' : 'Show password'} disabled={isLoading}>
                <EyeIcon open={showPassword} />
              </button>
            </div>
          </div>
          <div className="auth-row">
            <label className="auth-remember">
              <input type="checkbox" name="remember" checked={form.remember} onChange={e => setForm(f => ({ ...f, remember: e.target.checked }))} disabled={isLoading} /> Remember me
            </label>
            <a href="#" className="auth-forgot">Forgot password?</a>
          </div>
          <button type="submit" className="auth-btn" disabled={isLoading}>
            {isLoading ? (
              <div className="auth-btn-content">
                <LoadingSpinner size="sm" />
                <span>Signing In...</span>
              </div>
            ) : (
              'Sign In'
            )}
          </button>
        </form>
        <p className="auth-bottom-text">
          Don&apos;t have an account?{' '}
          <button className="auth-link" onClick={onSwitch} disabled={isLoading}>Sign up</button>
        </p>
      </div>
    </div>
  );
}

function AuthSignUp({ imgIdx, onSwitch }: { imgIdx: number; onSwitch: () => void }) {
  const { signUp, isLoading } = useAuth();
  const [form, setForm] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    password: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [focus, setFocus] = useState<string | null>(null);
  const [error, setError] = useState<string>('');
  const [toast, setToast] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const response = await signUp({
        name: form.name,
        email: form.email,
        phoneNumber: form.phoneNumber || undefined,
        password: form.password,
      });
      if (response.success) {
        window.location.href = '/dashboard';
      }
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Sign up failed';
      setToast(msg);
    }
  };

  return (
    <div className="auth-flex">
      <div className="auth-form-col">
        {toast && <Toast message={toast} onClose={() => setToast('')} />}
        <div className="auth-logo-wrap">
          <img src="/logo.svg" alt="Hike&Book logo" className="auth-logo-centered" width={120} height={40} />
          <h1 className="auth-title-centered">Hike&Book</h1>
        </div>
        <form className="auth-form" onSubmit={handleSubmit}>
          {error && <div className="auth-error">{error}</div>}
          <div className="auth-field">
            <label htmlFor="name">Name</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon"><UserIcon /></span>
              <input
                id="name"
                name="name"
                type="text"
                placeholder="Enter your name"
                value={form.name}
                onChange={e => setForm(f => ({ ...f, name: e.target.value }))}
                required
                onFocus={() => setFocus('name')}
                onBlur={() => setFocus(focus === 'name' ? null : focus)}
                disabled={isLoading}
              />
            </div>
            {focus === 'name' && !form.name && (
              <div className="auth-field-msg">This field is required</div>
            )}
          </div>
          <div className="auth-field">
            <label htmlFor="email">Email</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon">@</span>
              <input
                id="email"
                name="email"
                type="email"
                placeholder="Enter your email"
                value={form.email}
                onChange={e => setForm(f => ({ ...f, email: e.target.value }))}
                required
                onFocus={() => setFocus('email')}
                onBlur={() => setFocus(focus === 'email' ? null : focus)}
                disabled={isLoading}
              />
            </div>
            {focus === 'email' && !form.email && (
              <div className="auth-field-msg">This field is required</div>
            )}
          </div>
          <div className="auth-field">
            <label htmlFor="phoneNumber">Phone Number</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon"><PhoneIcon /></span>
              <input
                id="phoneNumber"
                name="phoneNumber"
                type="tel"
                placeholder="Enter your phone number"
                value={form.phoneNumber}
                onChange={e => setForm(f => ({ ...f, phoneNumber: e.target.value }))}
                onFocus={() => setFocus('phoneNumber')}
                onBlur={() => setFocus(focus === 'phoneNumber' ? null : focus)}
                disabled={isLoading}
              />
            </div>
            {focus === 'phoneNumber' && (
              <div className="auth-field-msg">This field is optional</div>
            )}
          </div>
          <div className="auth-field">
            <label htmlFor="password">Password</label>
            <div className="auth-input-wrap">
              <span className="auth-input-icon"><LockIcon /></span>
              <input
                id="password"
                name="password"
                type={showPassword ? 'text' : 'password'}
                placeholder="Enter your password"
                value={form.password}
                onChange={e => setForm(f => ({ ...f, password: e.target.value }))}
                required
                onFocus={() => setFocus('password')}
                onBlur={() => setFocus(focus === 'password' ? null : focus)}
                disabled={isLoading}
              />
              <button type="button" className="auth-eye-btn" onClick={() => setShowPassword(v => !v)} aria-label={showPassword ? 'Hide password' : 'Show password'} disabled={isLoading}>
                <EyeIcon open={showPassword} />
              </button>
            </div>
            {focus === 'password' && !form.password && (
              <div className="auth-field-msg">This field is required</div>
            )}
          </div>
          <button type="submit" className="auth-btn" disabled={isLoading}>
            {isLoading ? (
              <div className="auth-btn-content">
                <LoadingSpinner size="sm" />
                <span>Signing Up...</span>
              </div>
            ) : (
              'Sign Up'
            )}
          </button>
        </form>
        <p className="auth-bottom-text">
          Already have an account?{' '}
          <button className="auth-link" onClick={onSwitch} disabled={isLoading}>Sign In</button>
        </p>
      </div>
      <div className="auth-img-col">
        <img src={images[imgIdx]} alt="mountain" className="auth-img" width={800} height={1200} style={{ objectFit: 'cover' }} />
        <div className="auth-img-overlay" />
      </div>
    </div>
  );
}

function MobileAuthSwitcher({ mode, setMode, imgIdx }: { mode: 'signin' | 'signup'; setMode: (m: 'signin' | 'signup') => void; imgIdx: number }) {
  return (
    <div className="mobile-auth-root">
      <div className="mobile-auth-img-wrap">
        <img src={images[imgIdx]} alt="mountain" className="mobile-auth-img" width={600} height={180} />
      </div>
      <div className="mobile-auth-logo-wrap">
        <img src="/logo.svg" alt="Hike&Book logo" className="mobile-auth-logo" width={100} height={35} />
        <h1 className="mobile-auth-title">Hike&Book</h1>
      </div>
      <div className="mobile-auth-form-wrap">
        {mode === 'signin' ? (
          <MobileAuthSignIn onSwitch={() => setMode('signup')} />
        ) : (
          <MobileAuthSignUp onSwitch={() => setMode('signin')} />
        )}
      </div>
    </div>
  );
}

function MobileAuthSignIn({ onSwitch }: { onSwitch: () => void }) {
  const { signIn, isLoading } = useAuth();
  const [form, setForm] = useState({ email: '', password: '', remember: false });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState<string>('');
  const [toast, setToast] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const response = await signIn({
        email: form.email,
        password: form.password,
      });
      if (response.success) {
        window.location.href = '/dashboard';
      }
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Sign in failed';
      if (msg.toLowerCase().includes('email') || msg.toLowerCase().includes('password')) {
        setToast('Pogrešan email ili lozinka.');
      } else {
        setError(msg);
      }
    }
  };

  return (
    <form className="mobile-auth-form" onSubmit={handleSubmit}>
      {toast && <Toast message={toast} onClose={() => setToast('')} />}
      {error && <div className="auth-error">{error}</div>}
      <div className="auth-field">
        <label htmlFor="email">Email</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon">@</span>
          <input id="email" name="email" type="email" placeholder="Enter your email" value={form.email} onChange={e => setForm(f => ({ ...f, email: e.target.value }))} required disabled={isLoading} />
        </div>
      </div>
      <div className="auth-field">
        <label htmlFor="password">Password</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon"><LockIcon /></span>
          <input id="password" name="password" type={showPassword ? 'text' : 'password'} placeholder="Enter your password" value={form.password} onChange={e => setForm(f => ({ ...f, password: e.target.value }))} required disabled={isLoading} />
          <button type="button" className="auth-eye-btn" onClick={() => setShowPassword(v => !v)} aria-label={showPassword ? 'Hide password' : 'Show password'} disabled={isLoading}>
            <EyeIcon open={showPassword} />
          </button>
        </div>
      </div>
      <div className="auth-row">
        <label className="auth-remember">
          <input type="checkbox" name="remember" checked={form.remember} onChange={e => setForm(f => ({ ...f, remember: e.target.checked }))} disabled={isLoading} /> Remember me
        </label>
        <a href="#" className="auth-forgot">Forgot password?</a>
      </div>
      <button type="submit" className="auth-btn" disabled={isLoading}>
        {isLoading ? (
          <div className="auth-btn-content">
            <LoadingSpinner size="sm" />
            <span>Signing In...</span>
          </div>
        ) : (
          'Sign In'
        )}
      </button>
      <p className="auth-bottom-text">
        Don’t have an account?{' '}
        <button className="auth-link" onClick={onSwitch} disabled={isLoading}>Sign up</button>
      </p>
    </form>
  );
}

function MobileAuthSignUp({ onSwitch }: { onSwitch: () => void }) {
  const { signUp, isLoading } = useAuth();
  const [form, setForm] = useState({
    name: '',
    email: '',
    phoneNumber: '',
    password: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [focus, setFocus] = useState<string | null>(null);
  const [error, setError] = useState<string>('');
  const [toast, setToast] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const response = await signUp({
        name: form.name,
        email: form.email,
        phoneNumber: form.phoneNumber || undefined,
        password: form.password,
      });
      if (response.success) {
        window.location.href = '/dashboard';
      }
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Sign up failed';
      setToast(msg);
    }
  };

  return (
    <form className="mobile-auth-form" onSubmit={handleSubmit}>
      {toast && <Toast message={toast} onClose={() => setToast('')} />}
      {error && <div className="auth-error">{error}</div>}
      <div className="auth-field">
        <label htmlFor="name">Name</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon"><UserIcon /></span>
          <input
            id="name"
            name="name"
            type="text"
            placeholder="Enter your name"
            value={form.name}
            onChange={e => setForm(f => ({ ...f, name: e.target.value }))}
            required
            onFocus={() => setFocus('name')}
            onBlur={() => setFocus(focus === 'name' ? null : focus)}
            disabled={isLoading}
          />
        </div>
        {focus === 'name' && !form.name && (
          <div className="auth-field-msg">This field is required</div>
        )}
      </div>
      <div className="auth-field">
        <label htmlFor="email">Email</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon">@</span>
          <input
            id="email"
            name="email"
            type="email"
            placeholder="Enter your email"
            value={form.email}
            onChange={e => setForm(f => ({ ...f, email: e.target.value }))}
            required
            onFocus={() => setFocus('email')}
            onBlur={() => setFocus(focus === 'email' ? null : focus)}
            disabled={isLoading}
          />
        </div>
        {focus === 'email' && !form.email && (
          <div className="auth-field-msg">This field is required</div>
        )}
      </div>
      <div className="auth-field">
        <label htmlFor="phoneNumber">Phone Number</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon"><PhoneIcon /></span>
          <input
            id="phoneNumber"
            name="phoneNumber"
            type="tel"
            placeholder="Enter your phone number"
            value={form.phoneNumber}
            onChange={e => setForm(f => ({ ...f, phoneNumber: e.target.value }))}
            onFocus={() => setFocus('phoneNumber')}
            onBlur={() => setFocus(focus === 'phoneNumber' ? null : focus)}
            disabled={isLoading}
          />
        </div>
        {focus === 'phoneNumber' && (
          <div className="auth-field-msg">This field is optional</div>
        )}
      </div>
      <div className="auth-field">
        <label htmlFor="password">Password</label>
        <div className="auth-input-wrap">
          <span className="auth-input-icon"><LockIcon /></span>
          <input
            id="password"
            name="password"
            type={showPassword ? 'text' : 'password'}
            placeholder="Enter your password"
            value={form.password}
            onChange={e => setForm(f => ({ ...f, password: e.target.value }))}
            required
            onFocus={() => setFocus('password')}
            onBlur={() => setFocus(focus === 'password' ? null : focus)}
            disabled={isLoading}
          />
          <button type="button" className="auth-eye-btn" onClick={() => setShowPassword(v => !v)} aria-label={showPassword ? 'Hide password' : 'Show password'} disabled={isLoading}>
            <EyeIcon open={showPassword} />
          </button>
        </div>
        {focus === 'password' && !form.password && (
          <div className="auth-field-msg">This field is required</div>
        )}
      </div>
      <button type="submit" className="auth-btn" disabled={isLoading}>
        {isLoading ? (
          <div className="auth-btn-content">
            <LoadingSpinner size="sm" />
            <span>Signing Up...</span>
          </div>
        ) : (
          'Sign Up'
        )}
      </button>
      <p className="auth-bottom-text">
        Already have an account?{' '}
        <button className="auth-link" onClick={onSwitch} disabled={isLoading}>Sign In</button>
      </p>
    </form>
  );
} 