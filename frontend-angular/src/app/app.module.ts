import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { AdministradorComponent } from './administrador/administrador.component';
import { CoordinadorComponent } from './coordinador/coordinador.component';
import { ULoginComponent } from './usuario/u-login/u-login.component';
import { URegisterComponent } from './usuario/u-register/u-register.component';
import { UHomeComponent } from './usuario/u-home/u-home.component';
import { ErrorComponent } from './error/error.component';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { PMenuComponent } from './pasajero/p-menu/p-menu.component';
import { PModuloRutaComponent } from './pasajero/p-modulo-ruta/p-modulo-ruta.component';
import { PDetallesRutaComponent } from './pasajero/p-detalles-ruta/p-detalles-ruta.component';
import { PBuscarRutaComponent } from './pasajero/p-buscar-ruta/p-buscar-ruta.component';

@NgModule({
  declarations: [
    AppComponent,
    AdministradorComponent,
    CoordinadorComponent,
    ULoginComponent,
    URegisterComponent,
    UHomeComponent,
    PMenuComponent,
    ErrorComponent,
    PModuloRutaComponent,
    PDetallesRutaComponent,
    PBuscarRutaComponent
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
