import { Constants } from 'src/app/shared/constants';
import { AuthService } from '../../../services/authguard/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  loginForm: FormGroup;
  badCredentialsError = false;

  constructor(private authService: AuthService, private router: Router) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  onLogin(): void {
    if (!this.loginForm.valid){
      this.loginForm.markAllAsTouched();
      return;
    }
    this.authService.login(this.loginForm.controls.email.value, this.loginForm.controls.password.value)
      .pipe(
        catchError(err => {
          if (err.status === 400) {
            this.badCredentialsError = true;
          }
          return throwError(err);
        }),
      )
      .subscribe((User) => {
        sessionStorage.setItem(Constants.CURRENT_USER, JSON.stringify(User));
        this.router.navigate(['/main']);
      });
  }

  get loginFormControl() {
    return this.loginForm.controls;
  }

  ngOnInit(): void {
  }

}
