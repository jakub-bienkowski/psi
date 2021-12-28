import { SidebarComponent } from './components/home/sidebar/sidebar.component';
import { NavbarComponent } from './components/home/navbar/navbar.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home/home.component';
import { AccountCreatedComponent } from './components/public/account-created/account-created.component';
import { LoggedOutComponent } from './components/public/logged-out/logged-out.component';
import { LoginComponent } from './components/public/login/login.component';
import { RegistrationComponent } from './components/public/registration/registration.component';
import { WelcomeComponent } from './components/public/welcome/welcome.component';
import { AuthGuard } from './services/authentication/auth.guard';
import { MainComponent } from './components/home/main/main.component';

const routes: Routes = [


  {
    path: '',
    component: WelcomeComponent,
    data: {
      title: ''
    }
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
    path: 'success',
    component: AccountCreatedComponent,
    data: {
      title: 'Zarejestruj się'
    }
  },
  {
    path: 'logout',
    component: LoggedOutComponent,
    data: {
      title: ''
    }
  },

  {
    path: 'welcome',
    component: WelcomeComponent,
    data: {
      title: ''
    }
  },

  {
    path: 'main',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {
          path: '',
          outlet: 'navbar',
          component: NavbarComponent
      },
      {
        path: '',
        outlet: 'sidebar',
        component: SidebarComponent
      },
      {
        path: 'home',
        outlet: 'home',
        component: HomeComponent
      }
    ]
  },
  {
    path: '**',
    redirectTo: ''
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
