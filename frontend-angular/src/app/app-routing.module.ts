import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ULoginComponent} from "./usuario/u-login/u-login.component";
import {URegisterComponent} from "./usuario/u-register/u-register.component";
import {UHomeComponent} from "./usuario/u-home/u-home.component";
import {PMenuComponent} from "./pasajero/p-menu/p-menu.component";
import {ErrorComponent} from "./error/error.component";

const routes: Routes = [
  { path: '', component: UHomeComponent },
  { path: 'u-login', component: ULoginComponent },
  { path: 'u-register', component: URegisterComponent },
  { path: 'p-menu', component: PMenuComponent },
  { path: '**', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
