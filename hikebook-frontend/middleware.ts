import { NextRequest, NextResponse } from 'next/server';

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;
  const isAuth = request.cookies.get('auth')?.value === 'true';

  // Allow access to /auth page without auth
  if (pathname === '/auth') {
    return NextResponse.next();
  }

  // If not authenticated, redirect to /auth
  if (!isAuth) {
    const authUrl = request.nextUrl.clone();
    authUrl.pathname = '/auth';
    return NextResponse.redirect(authUrl);
  }

  // Otherwise, allow
  return NextResponse.next();
}

export const config = {
  matcher: ['/((?!_next|api|favicon.ico|public).*)'],
}; 