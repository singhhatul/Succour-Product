import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: any;
  // const decodedToken = helper.decodeToken(this.token);


  private socketUri = 'http://13.235.139.47:8000/succour-web-socket';

  private recieverEndPoint = '/topic/Finance';

  private subject = new Subject<any>();

  constructor() {}

  /**
   * Connects websocket service
   */
  connect() {
    const socket = new SockJs(this.socketUri);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, frame => {
      // console.log(frame);
      this.stompClient.subscribe(this.recieverEndPoint, message => {
        this.subject.next(message.body); // Forward recieved message to Observable
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

  public getMessage(): Observable<any> {
    return this.subject.asObservable();
  }
}
