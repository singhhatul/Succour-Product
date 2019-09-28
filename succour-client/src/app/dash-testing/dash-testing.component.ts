import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dash-testing',
  templateUrl: './dash-testing.component.html',
  styleUrls: ['./dash-testing.component.scss']
})
export class DashTestingComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }
  redirect() {
    this.router.navigateByUrl('login');
  }
}
