import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Activity } from './activity';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: any;
  private socketUri = 'http://13.235.139.47:8000/succour-web-socket';

  private recieverEndPoint = '/topic/';

  private subject = new Subject<Activity>();

  constructor() { }

  /**
   * Connects websocket service
   */
  connect(domain: string) {
    const socket = new SockJs(this.socketUri);
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = null;
    this.stompClient.connect({}, frame => {
      // console.log(frame);
      this.stompClient.subscribe(this.recieverEndPoint + domain, message => {
        let activity: Activity = JSON.parse(message.body);
        this.subject.next(activity); // Forward recieved message to Observable
      });
    });
  }

  /**
   * Disconnects websocket service
   */
  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  public getMessage(): Observable<Activity> {
    return this.subject.asObservable();
  }

}
