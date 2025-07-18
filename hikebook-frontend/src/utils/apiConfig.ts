// API Configuration helper
export const getApiBaseUrl = (): string => {
  return process.env.NEXT_PUBLIC_API_URL ?? 
    (typeof window !== 'undefined' && window.location.hostname !== 'localhost' 
      ? 'https://hikebook-backend.onrender.com' 
      : 'http://localhost:8080');
};

export const getApiUrl = (endpoint: string): string => {
  return `${getApiBaseUrl()}${endpoint}`;
}; 