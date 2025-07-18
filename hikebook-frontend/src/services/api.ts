// env.d.ts
declare const process: {
  env: {
    NEXT_PUBLIC_API_URL?: string;
    [key: string]: string | undefined;
  };
};

// API service for backend communication
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? 'http://localhost:8080';

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

// New interfaces for trails and activities
export interface Trail {
  id: number;
  name: string;
  length: number;
  elevation_gain: number;
  estimated_time: number;
  difficulty: 'easy' | 'moderate' | 'hard';
  rating: number;
  review_count: number;
  main_image?: string;
  destination: {
    id: number;
    name: string;
    region: string;
  };
  activities: Activity[];
  photos?: Photo[];
}

export interface Activity {
  id: number;
  name: string;
  description: string;
  icon: string;
  emoji: string;
}

export interface Photo {
  id: number;
  trail_id: number;
  user_id: number;
  url: string;
  description: string;
  date: string;
  latitude: number;
  longitude: number;
  is_main: boolean;
  created_at: string;
}

export interface PhotoDto {
  id: number;
  trailId: number;
  userId: number;
  url: string;
  description: string;
  date: string;
  latitude: number;
  longitude: number;
  isMain: boolean;
  createdAt: string;
}

export interface TrailsResponse {
  success: boolean;
  data: Trail[];
  message?: string;
}

export interface ActivitiesResponse {
  success: boolean;
  data: Activity[];
  message?: string;
}

export interface PhotosResponse {
  success: boolean;
  data: PhotoDto[];
  message?: string;
}

export interface FavoriteResponse {
  success: boolean;
  message: string;
}

export interface UploadResponse {
  success: boolean;
  data?: {
    url: string;
    public_id: string;
    secure_url: string;
  };
  message?: string;
}

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    
    // Get token for authenticated requests
    const token = this.getToken();
    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
    };

    // Add authorization header if token exists
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    // Merge with options headers
    Object.assign(headers, options.headers);

    const config: RequestInit = {
      headers,
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

  // Existing auth methods
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
        // Try to call backend signout endpoint if it exists
        await this.request('/api/auth/signout', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
      } catch {
        // If backend doesn't support signout, just clear local storage
      } finally {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
      }
    }
  }

  // New methods for trails and activities
  async getTrails(params?: {
    limit?: number;
    offset?: number;
    difficulty?: string;
    activity_id?: number;
    destination_id?: number;
  }): Promise<TrailsResponse> {
    const searchParams = new URLSearchParams();
    if (params?.limit) searchParams.append('limit', params.limit.toString());
    if (params?.offset) searchParams.append('offset', params.offset.toString());
    if (params?.difficulty) searchParams.append('difficulty', params.difficulty);
    if (params?.activity_id) searchParams.append('activity_id', params.activity_id.toString());
    if (params?.destination_id) searchParams.append('destination_id', params.destination_id.toString());

    const queryString = searchParams.toString();
    const endpoint = queryString ? `/api/trails?${queryString}` : '/api/trails';
    
    const response = await this.request<{ success: boolean; data: unknown; message?: string }>(endpoint);
    
    // Handle both Page and List responses
    if (response.success && response.data) {
      // If data has content property (Page object), use it
      if (typeof response.data === 'object' && response.data !== null && 'content' in response.data && Array.isArray((response.data as { content: Trail[] }).content)) {
        return {
          success: true,
          data: (response.data as { content: Trail[] }).content,
          message: response.message
        };
      }
      // If data is directly an array
      if (Array.isArray(response.data)) {
        return {
          success: true,
          data: response.data as Trail[],
          message: response.message
        };
      }
    }
    
    return response as TrailsResponse;
  }

  async getActivities(): Promise<ActivitiesResponse> {
    return this.request<ActivitiesResponse>('/api/activities');
  }

  async getFavorites(): Promise<TrailsResponse> {
    return this.request<TrailsResponse>('/api/trails/favorites');
  }

  async addToFavorites(trailId: number): Promise<FavoriteResponse> {
    return this.request<FavoriteResponse>(`/api/trails/${trailId}/favorite`, {
      method: 'POST',
    });
  }

  async removeFromFavorites(trailId: number): Promise<FavoriteResponse> {
    return this.request<FavoriteResponse>(`/api/trails/${trailId}/favorite`, {
      method: 'DELETE',
    });
  }

  async getTrailById(trailId: number): Promise<{ success: boolean; data: Trail; message?: string }> {
    return this.request(`/api/trails/${trailId}`);
  }

  // Photo methods
  async getTrailPhotos(trailId: number): Promise<PhotosResponse> {
    const response = await this.request<{ success: boolean; data: unknown; message?: string }>(`/api/photos/trail/${trailId}`);
    
    // Handle both Page and List responses
    if (response.success && response.data) {
      // If data has content property (Page object), use it
      if (typeof response.data === 'object' && response.data !== null && 'content' in response.data && Array.isArray((response.data as { content: PhotoDto[] }).content)) {
        return {
          success: true,
          data: (response.data as { content: PhotoDto[] }).content,
          message: response.message
        };
      }
      // If data is directly an array
      if (Array.isArray(response.data)) {
        return {
          success: true,
          data: response.data as PhotoDto[],
          message: response.message
        };
      }
    }
    
    return response as PhotosResponse;
  }

  async getMainPhoto(trailId: number): Promise<PhotosResponse> {
    const response = await this.getTrailPhotos(trailId);
    if (response.success && response.data) {
      const mainPhoto = response.data.find(photo => photo.isMain);
      if (mainPhoto) {
        return {
          success: true,
          data: [mainPhoto],
          message: response.message
        };
      }
    }
    return response;
  }

  async addPhoto(trailId: number, url: string, description: string): Promise<{ success: boolean; data?: Photo; message?: string }> {
    const formData = new FormData();
    formData.append('trailId', trailId.toString());
    formData.append('url', url);
    formData.append('description', description);
    
    const token = this.getToken();
    const headers: Record<string, string> = {};
    
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE_URL}/api/photos`, {
      method: 'POST',
      headers,
      body: formData,
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Failed to add photo');
    }
    return data;
  }

  async setMainPhoto(photoId: number): Promise<{ success: boolean; message?: string }> {
    return this.request(`/api/photos/${photoId}/main`, {
      method: 'PUT',
    });
  }

  // Upload methods
  async uploadImage(file: File, folder?: string): Promise<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file);
    
    const token = this.getToken();
    const headers: Record<string, string> = {};
    
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const endpoint = folder ? `/api/upload/image/${folder}` : '/api/upload/image';
    
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      method: 'POST',
      headers,
      body: formData,
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Upload failed');
    }
    return data;
  }

  async uploadActivityIcon(file: File): Promise<UploadResponse> {
    return this.uploadImage(file, 'activities');
  }

  async uploadTrailImage(file: File): Promise<UploadResponse> {
    return this.uploadImage(file, 'trails');
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

  // Helper method to check if user is authenticated
  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}

export const apiService = new ApiService(); 