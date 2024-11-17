import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, Router, UrlTree} from '@angular/router';
import { AuthService } from '../share/auth.service';
import {MessageService} from 'primeng/api';
import {Observable} from 'rxjs';
import {Role} from '../auth/models/role';

@Injectable({
  providedIn: 'root'
})

export class RoleGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    const expectedRole = route.data['expectedRole'];
    const currentRole = this.authService.getRole();
    const currentPath = route.routeConfig?.path;

    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/h-login']).then(() => console.log('Navigated to /h-login'));
      return false;
    }

    // Si el rol actual no coincide con el rol esperado para la ruta
    if (expectedRole && currentRole !== expectedRole) {
      this.messageService.add({
        severity: 'error',
        summary: 'Acceso Denegado',
        detail: 'No tienes permisos para acceder a esta sección',
      });

      // Redirigir a la ruta apropiada según el rol actual
      switch (currentRole) {
        case Role.Administrador:
          this.router.navigate(['/r-menu']).then(() => console.log('Navigated to /r-menu'));
          break;
        case Role.Pasajero:
          this.router.navigate(['/r-menu-pasajero']).then(() => console.log('Navigated to /r-menu'));
          break;
        case Role.Coordinador:
          this.router.navigate(['/h-select-function']).then(() => console.log('Navigated to /h-select-function'));
          break;
        default:
          this.router.navigate(['/h-login']).then(() => console.log('Navigated to /h-login'));
      }
      return false;
    }

    return true;
  }
}
