import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Constants } from 'src/app/shared/constants';
import { User } from 'src/app/shared/models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<User> | BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem(Constants.CURRENT_USER)!));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  isLoggedIn() {
    const token = localStorage.getItem('token');
    if (token === null) {
      return false;
    }
    const payload = atob(token.split('.')[1]);
    const parsedPayload = JSON.parse(payload);
    return parsedPayload.exp > Date.now() / 1000;
  }

  login(username: string, password: string) {
    return this.http
      .post<any>(environment.api.serverhost + environment.api.login, {
        username,
        password,
      })
      .pipe(
        map((user: User) => {
          sessionStorage.setItem(Constants.CURRENT_USER, JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }

  logout(): void {
    sessionStorage.removeItem(Constants.CURRENT_USER);
    localStorage.removeItem(Constants.CURRENT_USER);
    this.http.get<any>(environment.api.serverhost + environment.api.logout);
  }

}
