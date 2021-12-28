import { TokenstorageService } from './../../../services/tokenstorage/tokenstorage.service';
import { Constants } from 'src/app/shared/constants';
import { AuthService } from '../../../services/authentication/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { isVariableDeclarationList } from 'typescript';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  loginForm: FormGroup;
  badCredentialsError = false;

  onLogin(): void {
    if (!this.loginForm.valid){
      this.loginForm.markAllAsTouched();
      return;
    }
    this.authService.login(this.loginForm.controls.username.value, this.loginForm.controls.password.value)
      .pipe(
        catchError(err => {
          if (err.status === 400) {
            this.badCredentialsError = true;
          }
          return throwError(err);
        }),
      )
      .subscribe(user => {
          this.tokenStorage.saveToken(user.token);
          this.tokenStorage.saveUser(user);
        this.router.navigate(['/main']);
      });
  }

  get loginFormControl() {
    return this.loginForm.controls;
  }

  constructor(private authService: AuthService, private tokenStorage: TokenstorageService, private router: Router) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
  }

}
