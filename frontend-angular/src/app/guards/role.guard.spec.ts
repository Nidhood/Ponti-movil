// role.guard.spec.ts
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RoleGuard } from './role.guard';
import { AuthService } from '../share/auth.service';
import { ActivatedRouteSnapshot } from '@angular/router';

describe('RoleGuard', () => {
  let roleGuard: RoleGuard;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService', ['isAuthenticated', 'role']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        RoleGuard,
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    roleGuard = TestBed.inject(RoleGuard);
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should allow access for the correct role', () => {
    authService.isAuthenticated.and.returnValue(true);
    authService.role.and.returnValue('admin');

    const route = new ActivatedRouteSnapshot();
    route.data = { expectedRole: 'admin' };
    expect(roleGuard.canActivate(route)).toBeTrue();
  });

  it('should deny access for an incorrect role', () => {
    authService.isAuthenticated.and.returnValue(true);
    authService.role.and.returnValue('coordinador');

    const route = new ActivatedRouteSnapshot();
    route.data = { expectedRole: 'admin' };
    expect(roleGuard.canActivate(route)).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should deny access for unauthenticated user', () => {
    authService.isAuthenticated.and.returnValue(false);

    const route = new ActivatedRouteSnapshot();
    route.data = { expectedRole: 'admin' };
    expect(roleGuard.canActivate(route)).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
