import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home/home.component';
import { MainComponent } from './components/home/main/main.component';
import { NavbarComponent } from './components/home/navbar/navbar.component';
import { SidebarComponent } from './components/home/sidebar/sidebar.component';
import { AccountCreatedComponent } from './components/public/account-created/account-created.component';
import { LoggedOutComponent } from './components/public/logged-out/logged-out.component';
import { LoginComponent } from './components/public/login/login.component';
import { RegistrationComponent } from './components/public/registration/registration.component';
import { WelcomeComponent } from './components/public/welcome/welcome.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    LoggedOutComponent,
    WelcomeComponent,
    AccountCreatedComponent,
    NavbarComponent,
    SidebarComponent,
    MainComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

