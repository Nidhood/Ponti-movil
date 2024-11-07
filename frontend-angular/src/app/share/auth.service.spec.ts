import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Usa HttpClientTestingModule aquí
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    sessionStorage.clear(); // Limpia el almacenamiento después de cada prueba
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should store token and role on login', () => {
    const credentials = { username: 'test', password: 'password' };
    const mockResponse = { token: '12345', role: 'admin' };

    service.login(credentials).subscribe();

    const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);

    expect(sessionStorage.getItem('auth-token')).toBe('12345');
    expect(sessionStorage.getItem('user-role')).toBe('admin');
  });

  it('should remove token and role on logout', () => {
    sessionStorage.setItem('auth-token', '12345');
    sessionStorage.setItem('user-role', 'admin');

    service.logout();

    expect(sessionStorage.getItem('auth-token')).toBeNull();
    expect(sessionStorage.getItem('user-role')).toBeNull();
  });

  it('should return true if authenticated', () => {
    sessionStorage.setItem('auth-token', '12345');
    expect(service.isAuthenticated()).toBeTrue();
  });

  it('should return false if not authenticated', () => {
    sessionStorage.removeItem('auth-token');
    expect(service.isAuthenticated()).toBeFalse();
  });

  it('should return the correct token', () => {
    sessionStorage.setItem('auth-token', '12345');
    expect(service.token()).toBe('12345');
  });

  it('should return the correct role', () => {
    sessionStorage.setItem('user-role', 'admin');
    expect(service.role()).toBe('admin');
  });
});
