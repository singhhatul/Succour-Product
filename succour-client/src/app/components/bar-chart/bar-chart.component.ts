import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent implements OnInit {
  @Input() private barChartData: any;
  private barData: any;

  constructor() {}

  ngOnInit() {
    this.barData = {
      chart: {
        caption: 'Incomming data, domain wise.',
        yaxisname: 'Sentiment',
        decimals: '1',
        theme: 'fusion'
      },
      data: this.barChartData
    };
  }
}
