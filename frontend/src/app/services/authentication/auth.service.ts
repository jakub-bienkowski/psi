import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared/models/user';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string): Observable<User> {
    return this.http
      .post<any>(environment.api.serverhost + environment.api.login, {
        username,
        password,
      }, this.httpOptions)
  }

}
