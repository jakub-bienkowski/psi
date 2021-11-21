import { AuthService } from './../../../services/authguard/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  loggedUser!: User;

  constructor(private authService: AuthService) {
    authService.currentUser.subscribe(user =>
      this.loggedUser = user);
   }

   logOut() {
     this.authService.logout();
   }

  ngOnInit(): void {
  }

}
