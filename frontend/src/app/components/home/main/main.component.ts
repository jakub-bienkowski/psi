import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/authguard/auth.service';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  loggedUser!: User;
  taskList = ['odkurzyć','odkurzyć','odkurzyć','odkurzyć'];

  constructor(private authService: AuthService, private router: Router) {
    authService.currentUser.subscribe(user =>
      this.loggedUser = user);
   }

   logOut() {
     this.authService.logout();
     this.router.navigate(['/logout']);
   }

  ngOnInit(): void {
  }

}
