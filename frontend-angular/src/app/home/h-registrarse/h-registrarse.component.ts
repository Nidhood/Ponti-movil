import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../share/auth.service';
import {Router, RouterLink} from '@angular/router';
import {NgClass, NgIf} from '@angular/common';
import {PasswordModule} from 'primeng/password';
import {Button} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextModule} from 'primeng/inputtext';
import {Role} from '../../auth/models/role';

@Component({
  selector: 'app-h-registrarse',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    PasswordModule,
    Button,
    RouterLink,
    ToastModule,
    DropdownModule,
    InputTextModule,
    NgIf
  ],
  templateUrl: './h-registrarse.component.html',
  styleUrl: './h-registrarse.component.css',
  providers: [MessageService]
})

export class HRegistrarseComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  roles: { label: string; value: Role; }[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.passwordMatchValidator
    });
  }

  ngOnInit() {
    this.roles = [
      { label: 'Administrador', value: Role.Administrador },
      { label: 'Coordinador', value: Role.Coordinador },
      { label: 'Pasajero', value: Role.Pasajero }
    ];
  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('password')?.value === g.get('confirmPassword')?.value
      ? null
      : { mismatch: true };
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Por favor, complete todos los campos correctamente'
      });
      return;
    }

    if (this.registerForm.hasError('mismatch')) {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Las contraseñas no coinciden'
      });
      return;
    }

    const registrationData = {
      firstName: this.registerForm.value.firstName,
      lastName: this.registerForm.value.lastName,
      userName: this.registerForm.value.userName,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      role: this.registerForm.value.role
    };

    this.loading = true;
    this.authService.signup(registrationData).subscribe({
      next: (response) => {
        this.loading = false;
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: '¡Registro completado exitosamente!'
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
