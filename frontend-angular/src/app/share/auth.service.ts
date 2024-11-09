import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import {UserRegistrationDTO} from '../dto/authentication/UserRegistrationDTO';
import {JwtAuthenticationResponse} from '../dto/authentication/JwtAuthenticationResponse';
import {LoginDTO} from '../dto/authentication/LoginDTO';
import {Role} from '../auth/models/role';
import {Router} from '@angular/router';

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
    const route = this.roleRoutes[role as Role];
    if (route) {
      this.router.navigate([route]).then(r => console.log(`Navigated to ${route}`));
    } else {
      console.error(`No route defined for role: ${role}`);
      this.router.navigate(['/h-login']).then(r => console.log('Navigated to /h-login'));
    }
  }

  signup(registrationData: UserRegistrationDTO): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/signup`, registrationData)
      .pipe(
        tap(response => {
          this.setSession(response);
          this.navigateByRole(response.role);
        }),
        catchError(this.handleError)
      );
  }

  login(credentials: LoginDTO): Observable<JwtAuthenticationResponse> {
    return this.http.post<JwtAuthenticationResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(
        tap(response => {
          this.setSession(response);
          this.navigateByRole(response.role);
        }),
        catchError(this.handleError)
      );
  }

  private setSession(response: JwtAuthenticationResponse): void {
    sessionStorage.setItem(TOKEN_KEY, response.token);
    sessionStorage.setItem(ROLE_KEY, response.role);
    sessionStorage.setItem(EMAIL_KEY, response.email);
  }

  logout(): void {
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
    let errorMessage = 'An error occurred';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else if (error.status === 401) {
      errorMessage = 'Invalid email or password';
    } else if (error.status === 400) {
      errorMessage = 'Invalid input data';
    }
    return throwError(() => errorMessage);
  }
}
