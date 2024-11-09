import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {MessageService} from 'primeng/api';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../share/auth.service';
import {NgClass, NgIf} from '@angular/common';
import {PasswordModule} from 'primeng/password';
import {Button} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {InputTextModule} from 'primeng/inputtext';
import {Role} from '../../auth/models/role';

@Component({
  selector: 'app-h-login',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    NgClass,
    PasswordModule,
    Button,
    ToastModule,
    NgIf,
    InputTextModule
  ],
  templateUrl: './h-login.component.html',
  styleUrl: './h-login.component.css',
  providers: [MessageService]
})

export class HLoginComponent {
  loginForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService,
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Por favor, complete todos los campos correctamente'
      });
      return;
    }

    this.loading = true;
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.loading = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: '¡Bienvenido!'
        });

        // Navegar según el rol
        switch (response.role) {
          case Role.Administrador:
            this.router.navigate(['/r-menu']).then(r => console.log('Navigated to r-menu'));
            break;
          case Role.Pasajero:
            this.router.navigate(['/r-menu-pasajero']).then(r => console.log('Navigated to r-menu'));
            break;
          case Role.Coordinador:
            this.router.navigate(['/h-select-function']).then(r => console.log('Navigated to h-select-function'));
            break;
          default:
            this.router.navigate(['/h-login']).then(r => console.log('Navigated to h-login'));
        }
      },
      error: (error) => {
        this.loading = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error
        });
      }
    });
  }
}
