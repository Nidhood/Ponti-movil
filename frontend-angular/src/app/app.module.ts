import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { AdministradorComponent } from './administrador/administrador.component';
import { CoordinadorComponent } from './coordinador/coordinador.component';
import { PasajeroComponent } from './pasajero/pasajero.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { LoginComponent } from './usuario/login/login.component';
import { RegisterComponent } from './usuario/register/register.component';
import { HomeComponent } from './usuario/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    AdministradorComponent,
    CoordinadorComponent,
    PasajeroComponent,
    UsuarioComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent
  ],

  imports: [
    BrowserModule,
    AppRoutingModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
