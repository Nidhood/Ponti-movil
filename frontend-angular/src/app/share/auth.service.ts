import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

const TOKEN_KEY = 'auth-token';
const ROLE_KEY = 'user-role';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(credentials: { username: string, password: string }): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, credentials).pipe(
      tap(response => {
        sessionStorage.setItem(TOKEN_KEY, response.token);
        sessionStorage.setItem(ROLE_KEY, response.role);
      })
    );
  }

  logout(): void {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(ROLE_KEY);
  }

  isAuthenticated(): boolean {
    return sessionStorage.getItem(TOKEN_KEY) !== null;
  }

  token(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  role(): string | null {
    return sessionStorage.getItem(ROLE_KEY);
  }
}
