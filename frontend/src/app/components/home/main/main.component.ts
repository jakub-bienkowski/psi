import { TokenstorageService } from './../../../services/tokenstorage/tokenstorage.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  loggedUser: User;
  taskList = ['odkurzyć','odkurzyć','odkurzyć','odkurzyć'];

  constructor(private tokenService: TokenstorageService, private router: Router) {
      this.loggedUser = this.tokenService.getCurrentUser();
   }

   logOut() {
     this.tokenService.logout();
     this.router.navigate(['/logout']);
   }

  ngOnInit(): void {
  }

}
