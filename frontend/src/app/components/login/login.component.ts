import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  logoPath = "assets/images/logo1.png";
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
      login: new FormControl('', [Validators.required, Validators.maxLength(10)]),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
  }

}
