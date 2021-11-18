import { Constants } from './../../shared/constants';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  loginForm: FormGroup;

  onLogin(): void {
    if (this.loginForm.valid) {

    }
  }

  get loginFormControl() {
    return this.loginForm.controls;
  }

  constructor() {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
  }

}
