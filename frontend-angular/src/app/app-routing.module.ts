import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./usuario/u-login/login.component";
import {RegisterComponent} from "./usuario/u-register/register.component";
import {HomeComponent} from "./usuario/u-home/home.component";
import {PMenuComponent} from "./pasajero/p-menu/p-menu.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'u-login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'p-menu', component: PMenuComponent },
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
