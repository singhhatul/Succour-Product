import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Notification } from './notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationSocketService {
  private stompClient: any;
  private socketUri = 'http://13.235.139.47:8000/succour-web-socket';

  private recieverEndPoint = '/topic/notification/';

  private subject = new Subject<Notification>();

  constructor() { }

  /**
   * Connects websocket service
   */
  connect(domain: string) {
    const socket = new SockJs(this.socketUri);
    this.stompClient = Stomp.over(socket);
    // this.stompClient.debug = null;
    this.stompClient.connect({}, frame => {
      // console.log(frame);
      this.stompClient.subscribe(this.recieverEndPoint + domain, message => {
        let notification = JSON.parse(message.body);
        this.subject.next(notification); // Forward recieved message to Observable
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

  public getMessage(): Observable<Notification> {
    return this.subject.asObservable();
  }

}
