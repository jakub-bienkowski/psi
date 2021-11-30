import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/shared/constants';

@Component({
  selector: 'app-account-created',
  templateUrl: './account-created.component.html',
  styleUrls: ['./account-created.component.scss']
})
export class AccountCreatedComponent implements OnInit {

  logoPath = Constants.LOGIN_PATH;

  constructor() { }

  ngOnInit(): void {
  }

}
