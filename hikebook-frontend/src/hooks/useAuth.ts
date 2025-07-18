import { useState, useEffect } from 'react';
import { apiService, SignUpData, SignInData, AuthResponse } from '../services/api';

export interface User {
  id: number;
  name: string;
  email: string;
  phoneNumber?: string;
}

export interface AuthState {
  user: User | null;
  token: string | null;
  isLoading: boolean;
  isAuthenticated: boolean;
}

export function useAuth() {
  const [authState, setAuthState] = useState<AuthState>({
    user: null,
    token: null,
    isLoading: true,
    isAuthenticated: false,
  });

  // Initialize auth state from localStorage
  useEffect(() => {
    const token = apiService.getToken();
    const user = apiService.getUser();
    
    const newAuthState = {
      user,
      token,
      isLoading: false,
      isAuthenticated: !!token && !!user,
    };
    
    setAuthState(newAuthState);
  }, []);

  const signUp = async (data: SignUpData): Promise<AuthResponse> => {
    try {
      setAuthState(prev => ({ ...prev, isLoading: true }));
      
      const response = await apiService.signUp(data);
      
      if (response.success) {
        // If backend doesn't return token, automatically sign in the user
        if (!response.token) {
          try {
            const signInResponse = await apiService.signIn({
              email: data.email,
              password: data.password,
            });
            
            if (signInResponse.success && signInResponse.token) {
              const user = signInResponse.user || {
                id: 1,
                name: data.name,
                email: data.email,
                phoneNumber: data.phoneNumber,
              };
              
              apiService.setAuthData(signInResponse.token, user);
              
              const newAuthState = {
                user: user,
                token: signInResponse.token,
                isLoading: false,
                isAuthenticated: true,
              };
              setAuthState(newAuthState);
              
              // Return the signin response instead
              return signInResponse;
            }
          } catch {
            // Continue with original response even if auto signin fails
          }
        } else {
          // Backend returned token, use it
          const user = response.user || {
            id: 1,
            name: data.name,
            email: data.email,
            phoneNumber: data.phoneNumber,
          };
          
          apiService.setAuthData(response.token, user);
          
          const newAuthState = {
            user: user,
            token: response.token,
            isLoading: false,
            isAuthenticated: true,
          };
          setAuthState(newAuthState);
        }
      }
      
      return response;
    } catch (error) {
      setAuthState(prev => ({ ...prev, isLoading: false }));
      throw error;
    }
  };

  const signIn = async (data: SignInData): Promise<AuthResponse> => {
    try {
      setAuthState(prev => ({ ...prev, isLoading: true }));
      
      const response = await apiService.signIn(data);
      
      if (response.success && response.token) {
        // If user is not provided in response, create a basic user object
        const user = response.user || {
          id: 1,
          name: 'User',
          email: data.email,
        };
        
        apiService.setAuthData(response.token, user);
        setAuthState({
          user: user,
          token: response.token,
          isLoading: false,
          isAuthenticated: true,
        });
      }
      
      return response;
    } catch (error) {
      setAuthState(prev => ({ ...prev, isLoading: false }));
      throw error;
    }
  };

  const signOut = async (): Promise<void> => {
    try {
      await apiService.signOut();
    } finally {
      setAuthState({
        user: null,
        token: null,
        isLoading: false,
        isAuthenticated: false,
      });
    }
  };

  return {
    ...authState,
    signUp,
    signIn,
    signOut,
  };
} 