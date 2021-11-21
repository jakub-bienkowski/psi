import { AuthService } from './../../services/authguard/auth.service';
import { Constants } from './../../shared/constants';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/user';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  errorMsg = '';
  logoPath = Constants.LOGIN_PATH;
  loginForm: FormGroup;

  onLogin(): void {
    if (this.loginForm.valid) {
      console.log(this.loginForm.controls.email.value);
      this.authService.login(this.loginForm.controls.email.value, this.loginForm.controls.password.value)
        .pipe(
          catchError(err => {
              return throwError(err);
          }),
        )
        .subscribe((user: User) => {
          this.router.navigate(['']);
        });
      }
  }

  get loginFormControl() {
    return this.loginForm.controls;
  }

  constructor(private authService: AuthService, private router: Router) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
  }

}
