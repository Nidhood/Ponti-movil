// auth.guard.ts
import { Injectable } from '@angular/core';
import {CanActivate, Router, UrlTree} from '@angular/router';
import { AuthService } from '../share/auth.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (this.authService.isAuthenticated()) {
      return true;
    }

    this.router.navigate(['/h-login']).then(r => console.log('Navigated to /h-login'));
    return false;
  }
}
