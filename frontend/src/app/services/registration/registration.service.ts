import {environment} from './../../../environments/environment';
import { User } from './../../shared/models/user';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient, private router: Router) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  register(user: User): Observable<User> {
    return this.http
      .post<any>(environment.api.serverhost + environment.api.register, JSON.stringify(user), this.httpOptions)
  }

}
