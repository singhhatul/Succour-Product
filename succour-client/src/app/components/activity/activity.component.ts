import { Component, OnInit, Input } from '@angular/core';
import { Activity } from 'src/app/services/activity';

@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss']
})
export class ActivityComponent implements OnInit {

  @Input() private activity: Activity;

  constructor() { }

  ngOnInit() {
  }

}
