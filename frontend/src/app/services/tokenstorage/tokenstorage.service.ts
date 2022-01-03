import { Injectable } from '@angular/core';
import { Constants } from 'src/app/shared/constants';
import { User } from 'src/app/shared/models/user';

@Injectable({
  providedIn: 'root'
})
export class TokenstorageService {

  constructor() { }

  logout(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(Constants.TOKEN_KEY);
    window.sessionStorage.setItem(Constants.TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(Constants.TOKEN_KEY);
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(Constants.USER_KEY);
    window.sessionStorage.setItem(Constants.USER_KEY, JSON.stringify(user));
  }

  public getCurrentUser(): User | null {
    let user = window.sessionStorage.getItem(Constants.USER_KEY);
    return user ? JSON.parse(user) : null;
  }

}
