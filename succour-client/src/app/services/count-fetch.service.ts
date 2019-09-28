import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountFetchService {
  private httpParams = new HttpParams();

  private BASE_URL = 'http://13.235.139.47:8097/activity/count/';

  constructor(private http: HttpClient) { }

  getCountByDomainNames(domainName: string): Observable<number> {
    const queryUrl = this.BASE_URL + 'domainname/' + domainName;
    return this.http.get<number>(queryUrl);
  }

  getCountByActor(actorName: string): Observable<number> {
    const queryUrl = this.BASE_URL + 'actor/' + actorName;
    return this.http.get<number>(queryUrl);
  }

}
