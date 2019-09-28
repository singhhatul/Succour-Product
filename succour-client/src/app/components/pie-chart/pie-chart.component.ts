import { Component, OnInit, Input, SimpleChanges } from "@angular/core";

@Component({
  selector: "app-pie-chart",
  templateUrl: "./pie-chart.component.html",
  styleUrls: ["./pie-chart.component.scss"]
})
export class PieChartComponent implements OnInit {
  @Input() private pieChartData: any;
  private piedata: any;

  constructor() { }

  ngOnInit() {
    this.piedata = {
      chart: {
        caption: "Sentiment Overview",
        subcaption: "For specific domain",
        enablesmartlabels: "1",
        showlabels: "1",
        numbersuffix: " MMbbl",
        usedataplotcolorforlabels: "1",
        plottooltext: "$label, <b>$value</b> MMbbl",
        theme: "fusion"
      },
      data: this.pieChartData
    };
  }

}
