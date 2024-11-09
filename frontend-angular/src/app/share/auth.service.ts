import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { UserRegistrationDTO } from '../dto/authentication/UserRegistrationDTO';
import { JwtAuthenticationResponse } from '../dto/authentication/JwtAuthenticationResponse';
import { LoginDTO } from '../dto/authentication/LoginDTO';
import { Role } from '../auth/models/role';
import { Router } from '@angular/router';

const TOKEN_KEY = 'auth-token';
const ROLE_KEY = 'user-role';
const EMAIL_KEY = 'user-email';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly roleRoutes = {
    [Role.Administrador]: '/r-menu',
    [Role.Coordinador]: '/h-select-function',
    [Role.Pasajero]: '/r-menu-pasajero'
  };

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    // Log token on service initialization
    console.log('AuthService initialized. Current token:', this.getToken());
  }

  private navigateByRole(role: string): void {
    const route = this.roleRoutes[role as Role];
    if (route) {
      this.router.navigate([route]).then(r => console.log(`Navigated to ${route}`));
    } else {
      console.error(`No route defined for role: ${role}`);
      this.router.navigate(['/h-login']).then(r => console.log('Navigated to /h-login'));
    }
  }

  signup(registrationData: UserRegistrationDTO): Observable<JwtAuthenticationResponse> {
    console.log('Attempting signup with data:', registrationData);
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/signup`, registrationData)
      .pipe(
        tap(response => {
          console.log('Signup response received:', response);
          if (response && response.token) {
            this.setSession(response);
            this.navigateByRole(response.role);
            console.log('AuthService initialized. Current token:', this.getToken());
            // ROle:
            console.log('AuthService initialized. Current role:', this.getRole());
            // Get email:
            console.log('AuthService initialized. Current email:', this.getEmail());
          } else {
            console.error('No token received in signup response');
          }
        }),
        catchError(this.handleError)
      );
  }

  login(credentials: LoginDTO): Observable<JwtAuthenticationResponse> {
    console.log('Attempting login with credentials:', credentials);
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          console.log('Login response received:', response);
          if (response && response.token) {
            this.setSession(response);
            console.log('Token stored successfully:', this.getToken());
            this.navigateByRole(response.role);
          } else {
            console.error('No token received in login response');
          }
        }),
        catchError(this.handleError)
      );
  }

  private setSession(response: JwtAuthenticationResponse): void {
    try {
      if (!response.token) {
        console.error('Attempting to set session with null token');
        return;
      }
      sessionStorage.setItem(TOKEN_KEY, response.token);
      sessionStorage.setItem(ROLE_KEY, response.role);
      sessionStorage.setItem(EMAIL_KEY, response.email);

      // Verify storage
      const storedToken = sessionStorage.getItem(TOKEN_KEY);
      console.log('Session set. Stored token:', storedToken ? 'Token present' : 'Token missing');
    } catch (error) {
      console.error('Error setting session:', error);
    }
  }

  logout(): void {
    console.log('Logging out, removing session data');
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(ROLE_KEY);
    sessionStorage.removeItem(EMAIL_KEY);
  }

  isAuthenticated(): boolean {
    const isAuth = sessionStorage.getItem(TOKEN_KEY) !== null;
    console.log('Checking authentication status:', isAuth);
    return isAuth;
  }

  getToken(): string | null {
    const token = sessionStorage.getItem(TOKEN_KEY);
    console.log('Getting token:', token ? 'Token exists' : 'No token found');
    return token;
  }

  getRole(): string | null {
    return sessionStorage.getItem(ROLE_KEY);
  }

  getEmail(): string | null {
    return sessionStorage.getItem(EMAIL_KEY);
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An error occurred';
    console.error('HTTP Error occurred:', error);

    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else if (error.status === 401) {
      errorMessage = 'Invalid email or password';
    } else if (error.status === 400) {
      errorMessage = 'Invalid input data';
    }

    console.error('Error message:', errorMessage);
    return throwError(() => errorMessage);
  }
}
