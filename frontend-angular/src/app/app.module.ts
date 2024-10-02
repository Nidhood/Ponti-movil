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
import {LottieComponent, LottieModule} from "ngx-lottie";
import player from 'lottie-web';
import {Button} from "primeng/button";


export function playerFactory() {
  return player;
}

@NgModule({
  declarations: [
    AppComponent,
    AdministradorComponent,
    CoordinadorComponent,
    ULoginComponent,
    URegisterComponent,
    UHomeComponent,
    ErrorComponent
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    LottieComponent,
    LottieModule.forRoot({player: playerFactory}),
    Button
  ],

  providers: [],
  exports: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
