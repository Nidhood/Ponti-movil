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
  ) {}

  private navigateByRole(role: string): void {
    console.log('Navigating based on role:', role); // Log the role received

    const route = this.roleRoutes[role as Role];
    if (route) {
      console.log(`Found route for role "${role}": ${route}`);
      this.router.navigate([route]).then(() => {
        console.log(`Successfully navigated to ${route}`);
      });
    } else {
      console.error(`Role "${role}" is not recognized. Redirecting to login.`);
      this.router.navigate(['/h-login']).then(() => {
        console.log('Navigated to default login page');
      });
    }
  }

  signup(registrationData: UserRegistrationDTO): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/signup`, registrationData)
      .pipe(
        tap(response => {
          if (response?.token) {
            this.setSession(response);
            console.log('Signup successful. Token and role set.');
            this.navigateByRole(response.role);
          } else {
            console.error('Signup response did not include a token.');
          }
        }),
        catchError(this.handleError)
      );
  }

  login(credentials: LoginDTO): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          console.log('Login response received:', response);
          if (response?.token) {
            this.setSession(response);
            console.log('Login successful. Token stored.');
            this.navigateByRole(response.role);
          } else {
            console.error('Login response did not include a token.');
          }
        }),
        catchError(this.handleError)
      );
  }

  private setSession(response: JwtAuthenticationResponse): void {
    try {
      if (response.token) {
        sessionStorage.setItem(TOKEN_KEY, response.token);
        sessionStorage.setItem(ROLE_KEY, response.role);
        sessionStorage.setItem(EMAIL_KEY, response.email);

        console.log('Session set successfully:', {
          token: response.token,
          role: response.role,
          email: response.email
        });
      } else {
        console.error('Attempting to set session with null or undefined token.');
      }
    } catch (error) {
      console.error('Error setting session:', error);
    }
  }

  logout(): void {
    console.log('Logging out and clearing session storage.');
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(ROLE_KEY);
    sessionStorage.removeItem(EMAIL_KEY);
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem(TOKEN_KEY) !== null;
  }

  getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  getRole(): string | null {
    return sessionStorage.getItem(ROLE_KEY);
  }

  getEmail(): string | null {
    return sessionStorage.getItem(EMAIL_KEY);
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred.';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else if (error.status === 401) {
      errorMessage = 'Unauthorized: Invalid email or password.';
    } else if (error.status === 400) {
      errorMessage = 'Bad Request: Invalid input data.';
    }
    console.error('Error occurred:', errorMessage);
    return throwError(() => errorMessage);
  }
}
