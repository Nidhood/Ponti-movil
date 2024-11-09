import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { provideLottieOptions } from 'ngx-lottie';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import {authInterceptor} from './share/auth-interceptor.component';
import {AuthService} from './share/auth.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideLottieOptions({ player: () => import('lottie-web') }),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),
    provideRouter(routes),
    provideAnimations(),
    AuthGuard,
    RoleGuard,
    AuthService
  ]
};
