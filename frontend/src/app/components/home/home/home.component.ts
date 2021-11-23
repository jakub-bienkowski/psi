import { AuthService } from './../../../services/authguard/auth.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  loggedUser!: User;

  constructor(private authService: AuthService, private router: Router) {
    authService.currentUser.subscribe(user =>
      this.loggedUser = user);
   }

   logOut() {
     this.authService.logout();
     this.router.navigate(['/success?type=loggout']);
   }

  ngOnInit(): void {
  }

}
