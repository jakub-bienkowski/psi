import { RegistrationService } from './../../services/registration/registration.service';
import {  FormControl, FormGroup,  Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/shared/constants';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  registrationForm: FormGroup;

  constructor(private registrationService: RegistrationService, private router: Router) {
    this.registrationForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      surname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required])
    })
   }

   onRegister(): void {
    if (this.registrationForm.invalid) {
      return;
    }
    this.registrationService.register(this.registrationForm.value)
    .subscribe(
        data => {
          this.router.navigate(['']);
        },
        error => {
          console.log(error);
            // this.alertService.error(error);
            // this.loading = false;
        });
   }

   get registrationFormControl() {
    return this.registrationForm.controls;
  }

  ngOnInit(): void {
  }

}
