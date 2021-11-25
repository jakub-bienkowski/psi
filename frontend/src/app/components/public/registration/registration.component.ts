import { RegistrationService } from '../../../services/registration/registration.service';
import { FormControl, FormGroup,  Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/shared/constants';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  registrationForm: FormGroup;
  accountExistsError = false;

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
      .pipe(
        catchError(err => {
          if (err.status === 422) {
            this.accountExistsError = true;
          }
          return throwError(err);
        }),
      )
      .subscribe(() => {
        this.router.navigate(['/success']);
      });

    }

  get registrationFormControl() {
    return this.registrationForm.controls;
  }

  ngOnInit(): void {
  }

}
