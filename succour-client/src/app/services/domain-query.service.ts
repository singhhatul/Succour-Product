import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Activity } from './activity';
@Injectable({
  providedIn: 'root'
})
export class DomainQueryService {
  private httpParams = new HttpParams();

  private BASE_URL = 'http://13.235.139.47:8097/activity/client/';

  constructor(private http: HttpClient) {}

  public getDomains(
    domainName: string,
    timeStamp: string,
    limit: string
  ): Observable<Activity[]> {
    this.httpParams = this.httpParams.set('timeStamp', timeStamp);
    this.httpParams = this.httpParams.set('limit', limit);
    // let queryURL = `${this.BASE_URL}${domainName}?${(this.httpParams.toString)}`;
    const queryURL =
      this.BASE_URL + domainName + '?' + this.httpParams.toString();
    console.log(this.httpParams.toString());
    console.log(`QueryURL: ${queryURL}`);
    return this.http.get<Activity[]>(queryURL);
  }
}
