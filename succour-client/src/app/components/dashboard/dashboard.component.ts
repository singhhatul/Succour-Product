import { Component, OnInit, ViewChild } from '@angular/core';
import { DomainQueryService } from 'src/app/services/domain-query.service';
import { Activity } from '../../services/activity';
import { WebsocketService } from '../../services/websocket.service';
import { AuthService } from 'src/app/authServices/auth.service';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

// Import collections
import * as Collections from 'typescript-collections';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { CountFetchService } from 'src/app/services/count-fetch.service';
import { NotificationSocketService } from '../../services/notification-socket.service';
import { Notification } from '../../services/notification';

import { MatSidenav } from '@angular/material/sidenav';

import { faBell, faTimes } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  private pieChartData: any;
  private barChartData = [];
  private domain: string; // Get the role from token. Sent to websocket.
  private domainName: string; // Domain name used to send to query-service.
  private domainsQueue: Collections.Queue<number>;
  private webSocketActivityData: Activity[]; /** Used to store data comming from websocket and pass to data-display component */
  private token: any;
  private userName: string; // Used to store username from JWT Token
  private activitiesCountByActor: number;
  private activitiesCountByDomainName = [];

  private cgiAvoidDomainsArray = ['Earthquake', 'Fire', 'Floods'];
  private ndaAvoidDomainsArray = ['@CGI_Global', 'CGI-FINANCE', 'CGI-LEGAL', 'CGI-HR'];

  private faBell = faBell; // Bell Icon
  private faTimes = faTimes; // Close (X) Icon

  private notifications: string[] = [];

  @ViewChild('sidenav', { static: false }) sidenav: MatSidenav;


  constructor(
    private domainQueryService: DomainQueryService,
    private webSockerService: WebsocketService,
    private authService: AuthService,
    private router: Router,
    private countFetchService: CountFetchService,
    private notificationService: NotificationSocketService) { }

  ngOnInit() {
    // Get logged in user details
    this.token = localStorage.getItem('JWT_TOKEN');
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.token);
    this.domain = decodedToken.Role;
    this.userName = decodedToken.sub;

    if (this.domain === 'CGI') {
      this.domainName = '@CGI_Global';
    } else {
      this.domainName = this.domain;
    }

    this.webSocketActivityData = [];
    this.webSockerService.connect(this.domain);
    this.domainsQueue = new Collections.Queue<number>();
    this.webSockerService.getMessage().subscribe((activity: Activity) => {
      this.webSocketActivityData.unshift(activity);
      this.domainsQueue.enqueue(activity.sentimentScore);
    });

    // Notification service
    this.notificationService.connect(this.domain);
    this.notificationService.getMessage().subscribe((notificationMessage: Notification) => {
      this.notifications.unshift(notificationMessage.text);
      console.log(notificationMessage.text);
    });

    // this.domainQueryService
    //   .getDomains('@CGI_Global', '2019-09-19 20:57:47', '100')
    this.getDomainActivities(this.domainName, moment().subtract(7, 'd').format('YYYY-MM-DD HH:mm:ss'), '1000')
      .subscribe((activities: Activity[]) => {
        // Pie chart data retrieval
        /**
         * The three variables will be used to store the
         * overall positive, neutral, negative sentiment
         * of over all domain.
         * This variables will be sent to pie chart.
         *
         * **BAR CHART**
         * For bar chart data store all the domains in a object.
         */
        let positiveCount = 0;
        let neutralCount = 0;
        let negativeCount = 0;
        let barChartDomains = {};
        activities.forEach(activity => {
          // Pie chart - retrieve and increment specific variable based on sentiment.
          switch (activity.sentimentScore) {
            case 1:
              negativeCount++;
              break;
            case 2:
              neutralCount++;
              break;
            case 3:
            case 4:
              positiveCount++;
              break;
            default:
              break;
          }
          // Bar chart data
          const domainName = activity.domainName;
          if (barChartDomains.hasOwnProperty(domainName)) {
            let count = barChartDomains[domainName];
            barChartDomains[domainName] = ++count;
          } else {
            barChartDomains[domainName] = 1;
          }
        });
        this.pieChartData = [
          {
            label: 'Positive',
            value: positiveCount
          },
          {
            label: 'Neutral',
            value: neutralCount
          },
          {
            label: 'Negative',
            value: negativeCount
          }
        ];
        // Populate barChartData
        Object.entries(barChartDomains).forEach(([key, value]) => {
          switch (this.domain) {
            case 'CGI':
              if (!this.cgiAvoidDomainsArray.includes(key)) {
                this.barChartData.push({
                  label: key,
                  value
                });
              }
              break;
            case 'NDA':
              if (!this.ndaAvoidDomainsArray.includes(key)) {
                this.barChartData.push({
                  label: key,
                  value
                });
              }
              break;
            default:
              break;
          }

          // Append domain specific activities count in activitiesCountByDomainName
          this.countFetchService.getCountByDomainNames(key).subscribe((count) => {
            switch (this.domain) {
              case 'CGI':
                if (!this.cgiAvoidDomainsArray.includes(key)) {
                  this.activitiesCountByDomainName.push({
                    domainName: key,
                    count
                  });
                }
                break;
              case 'NDA':
                if (!this.ndaAvoidDomainsArray.includes(key)) {
                  this.activitiesCountByDomainName.push({
                    domainName: key,
                    count
                  });
                }
                break;
              default:
                break;
            }
          });
        }
        );
      });
    // Fetch counts and add data to counter
    this.countFetchService.getCountByActor(this.domainName).subscribe((count: number) => {
      this.activitiesCountByActor = count;
    });
  }

  getDomainActivities(domainName: string, time: string, limit: string): Observable<Activity[]> {
    return this.domainQueryService.getDomains(domainName, time, limit);
  }

  logout() {
    localStorage.removeItem('JWT_TOKEN');
    this.router.navigate(['../dash-testing']);
  }

  /**
   * Close sidenav
   */
  close() {
    this.sidenav.close();
  }

}
