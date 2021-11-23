import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Constants } from 'src/app/shared/constants';

@Component({
  selector: 'app-logged-out',
  templateUrl: './logged-out.component.html',
  styleUrls: ['./logged-out.component.scss']
})
export class LoggedOutComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;
  loggedOut = true;

  constructor() {

  }

  ngOnInit(): void {
  }

}
