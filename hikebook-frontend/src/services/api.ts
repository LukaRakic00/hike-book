// API service for backend communication
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'https://hike-book.onrender.com';

export interface SignUpData {
  name: string;
  email: string;
  phoneNumber?: string;
  password: string;
}

export interface SignInData {
  email: string;
  password: string;
}

export interface AuthResponse {
  success: boolean;
  message: string;
  token?: string;
  user?: {
    id: number;
    name: string;
    email: string;
    phoneNumber?: string;
  };
}

export interface ApiError {
  message: string;
  field?: string;
}

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    
    const config: RequestInit = {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    try {
      const response = await fetch(url, config);
      let data;
      try {
        data = await response.json();
      } catch {
        data = {};
      }
      if (!response.ok) {
        throw new Error(data.message || 'An error occurred');
      }
      return data;
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
      throw new Error('Network error');
    }
  }

  async signUp(data: SignUpData): Promise<AuthResponse> {
    return this.request<AuthResponse>('/api/auth/signup', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async signIn(data: SignInData): Promise<AuthResponse> {
    return this.request<AuthResponse>('/api/auth/signin', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async signOut(): Promise<void> {
    const token = localStorage.getItem('authToken');
    if (token) {
      try {
        await this.request('/auth/signout', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
      } finally {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
      }
    }
  }

  // Helper method to get stored token
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Helper method to get stored user
  getUser() {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  }

  // Helper method to set auth data
  setAuthData(token: string, user: AuthResponse['user']) {
    localStorage.setItem('authToken', token);
    localStorage.setItem('user', JSON.stringify(user));
  }
}

export const apiService = new ApiService(); 