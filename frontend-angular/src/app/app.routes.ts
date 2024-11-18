import { Routes } from '@angular/router';
import { HIndexComponent } from './home/h-index/h-index.component';
import { HLoginComponent } from './home/h-login/h-login.component';
import { HRegistrarseComponent } from './home/h-registrarse/h-registrarse.component';
import { ErrorComponent } from './error/error.component';
import { RMenuComponent } from './ruta/r-menu/r-menu.component';
import { HSelectFunctionComponent } from './home/h-select-function/h-select-function.component';
import { REditarRutaComponent } from './ruta/r-editar-ruta/r-editar-ruta.component';
import { BMenuComponent } from './bus/b-menu/b-menu.component';
import { CMenuComponent } from './conductor/c-menu/c-menu.component';
import { RMenuPasajeroComponent } from './ruta/r-menu-pasajero/r-menu-pasajero.component';
import { NotFoundComponent } from './error/not-found/not-found.component';

// Importa los guards
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

// Importa los roles
import { Role } from './auth/models/role';

export const routes: Routes = [
  // Rutas públicas
  { path: '', component: HIndexComponent },
  { path: 'h-login', component: HLoginComponent },
  { path: 'h-register', component: HRegistrarseComponent },

  // Rutas protegidas para el Administrador
  {
    path: 'r-menu',
    component: RMenuComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Administrador }
  },
  {
    path: 'r-editar-ruta',
    component: REditarRutaComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Administrador }
  },

  // Rutas protegidas para el Coordinador
  {
    path: 'h-select-function',
    component: HSelectFunctionComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Coordinador }
  },
  {
    path: 'b-menu',
    component: BMenuComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Coordinador }
  },
  {
    path: 'c-menu',
    component: CMenuComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Coordinador }
  },

  // Rutas protegidas para el Pasajero
  {
    path: 'r-menu-pasajero',
    component: RMenuPasajeroComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { expectedRole: Role.Pasajero }
  },

  // Ruta de error por defecto
  { path: '**', component: NotFoundComponent }
];
