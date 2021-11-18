import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Zaloguj się'
    }
  },
  {
    path: 'register',
    component: RegistrationComponent,
    data: {
      title: 'Zarejestruj się'
    }
  },
  {
    path: '**',
    redirectTo: ''
  },
  // {
  //   path: '',
  //   component: HomeComponent,
  //   canActivate: [AuthGuard]
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
