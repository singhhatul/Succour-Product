import { Component, OnInit, Input } from '@angular/core';
import { Activity } from 'src/app/services/activity';

@Component({
  selector: 'app-data-display',
  templateUrl: './data-display.component.html',
  styleUrls: ['./data-display.component.scss']
})
export class DataDisplayComponent implements OnInit {

  @Input() webSocketActivityData: Activity[];

  constructor() { }

  ngOnInit() {

  }

}
