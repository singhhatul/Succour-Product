import { Component, OnInit, Input } from '@angular/core';
import * as Chart from 'chart.js';
import { delay } from 'rxjs/operators';
import 'chartjs-plugin-streaming';
const moment = require('moment');
import * as Collections from 'typescript-collections';
import { Activity } from 'src/app/services/activity';
@Component({
  selector: 'app-real-time-line-chart',
  templateUrl: './real-time-line-chart.component.html',
  styleUrls: ['./real-time-line-chart.component.scss']
})
export class RealTimeLineChartComponent implements OnInit {
  title = 'Live Sentiment';
  LineChart: any;
  @Input() private domainsQueue: Collections.Queue<number>;
  constructor() { }
  ngOnInit() {
    // Line chart:
    let plot = {
      type: 'line',
      data: {
        // labels: ["", "", "", "", "", "", "", "", ""],
        datasets: [
          {
            label: 'Sentiment of tweet per minute',
            data: [],
            fill: true,
            lineTension: 0.1,
            borderColor: 'red',
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          xAxes: [
            {
              type: 'realtime',
              realtime: {
                duration: 25000,
                refresh: 2000,
                delay: 5000,
                // pause: true,
                onRefresh: chart => {
                  let y1 = this.domainsQueue.dequeue();
                  if (y1) {
                    chart.data.datasets.forEach(function (dataset) {
                      dataset.data.push({
                        x: Date.now(),
                        y: y1
                      });
                    });
                  }
                }
              }
            }
          ],
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                max: 4,
                stepSize: 1
              }
            }
          ]
        }
      }
    };
    this.LineChart = new Chart('lineChart', plot);

  }

  getDate(timeStamp: number) {
    return moment(timeStamp).format('DD/MM/YYYY h:mm:ss a');
  }
}
