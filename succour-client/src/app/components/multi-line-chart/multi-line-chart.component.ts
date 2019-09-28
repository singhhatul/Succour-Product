import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';

@Component({
  selector: 'app-multi-line-chart',
  templateUrl: './multi-line-chart.component.html',
  styleUrls: ['./multi-line-chart.component.scss']
})
export class MultiLineChartComponent implements OnInit {
  title = 'Ng7ChartJs';
  LineChart: any;
  data1 = {
    label: 'Number of Items Sold in Months',
    data: [9, 7, 3, 5, 2, 10, 15, 16, 19, 3, 1, 9],
    fill: true,
    borderColor: "red",
    borderWidth: 3,
    pointstyle:"star"
  };
  data2 = {
    label: 'Number of Items Sold in Months',
    data: [9, 7, 3, 5, 2, 10, 15, 16, 19, 3, 1, 9],
    fill: true,
    borderColor: "blue",
    borderWidth: 3,
    pointstyle:"circle"
  };
  data3 = {
    label: 'Number of Items Sold in Months',
    data: [9, 7, 3, 5, 2, 10, 15, 16, 19, 3, 1, 9],
    fill: true,
    borderColor: "green",
    borderWidth: 3,
    pointstyle:"square"
  };


  constructor() { }

  ngOnInit() {
    let plot = {
      type: 'line',
      data: {
        labels: ["Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov"],
        datasets: [this.data1,this.data2,this.data3]
      },
      options: {
        title: {
          text: "Line Chart",
          display: true,
          animation: {
            duration: 0 // general animation time
        },
        responsiveAnimationDuration: 0 // animation duration after a resize
        },
        scales: {
          yAxes: [{
            stacked: true,
            ticks: {
              beginAtZero: false
            }
          }]
        }
      }
    };
    this.LineChart = new Chart('multiLineChart', plot);
    // setInterval(() => {
    //   plot.data.datasets[0].data.unshift(Math.floor(Math.random() * 10));
    //   plot.data.labels.unshift('a');
    //   console.log(plot.data.datasets[0].data);
    //   this.LineChart.update();
    //   plot.data.datasets[0].data.pop();
    //   plot.data.labels.pop();
    // }, 1000);

  }

}
