import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Lista de rutas públicas que no necesitan token
  const publicRoutes = ['/auth/login', '/auth/signup'];
  const isPublicRoute = publicRoutes.some(route => req.url.includes(route));

  console.log('AuthInterceptor - Request URL:', req.url);
  console.log('AuthInterceptor - Is public route:', isPublicRoute);

  if (isPublicRoute) {
    console.log('Skipping token for public route');
    return next(req);
  }

  const token = authService.getToken();
  console.log('AuthInterceptor - Token present:', !!token);

  if (token) {
    console.log('Adding token to request headers');
    const clonedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });

    // Log the final headers for debugging
    console.log('Request headers:', clonedReq.headers.keys());
    return next(clonedReq);
  }

  // Si no es una ruta pública y no hay token, aún así dejamos pasar la request
  // para que el backend pueda responder con 401 si es necesario
  console.warn('No token available for protected route');
  return next(req);
};
