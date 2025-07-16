import { NextRequest, NextResponse } from 'next/server';

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;
  
  // Allow access to /auth page without auth
  if (pathname === '/auth') {
    return NextResponse.next();
  }

  // Allow access to static files and API routes
  if (pathname.startsWith('/_next') || pathname.startsWith('/api') || pathname.startsWith('/favicon.ico') || pathname.startsWith('/public')) {
    return NextResponse.next();
  }

  // For client-side routes, we'll let the client handle auth
  // The client will redirect to /auth if not authenticated
  return NextResponse.next();
}

export const config = {
  matcher: ['/((?!_next|api|favicon.ico|public).*)'],
}; 