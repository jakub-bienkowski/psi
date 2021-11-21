import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/shared/constants';

@Component({
  selector: 'app-logged-out',
  templateUrl: './logged-out.component.html',
  styleUrls: ['./logged-out.component.scss']
})
export class LoggedOutComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;

  constructor() { }

  ngOnInit(): void {
  }

}
