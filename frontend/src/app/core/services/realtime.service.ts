import { Injectable, OnDestroy, signal } from '@angular/core';
import { Client, IFrame } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../environments/environment';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RealtimeService implements OnDestroy {
  private stompClient: Client | null = null;
  private connectionSubject = new Subject<boolean>();
  
  isConnected = signal(false);
  healthUpdates$ = new Subject<any>();
  careUpdates$ = new Subject<any>();
  notificationUpdates$ = new Subject<any>();

  constructor() {
    try {
      this.connect();
    } catch (e) {
      console.error('Failed to initialize RealtimeService connection', e);
    }
  }
  connect() {
    const wsUrl = `${environment.apiUrl}/ws-plant-care`;
    try {
      this.stompClient = new Client({
        // Some bundlers require .default for SockJS, others provide it directly
        webSocketFactory: () => {
          const SockJSConstructor = (SockJS as any).default || SockJS;
          return new SockJSConstructor(wsUrl);
        },
        debug: () => {}, 
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: (frame: IFrame) => {
          this.isConnected.set(true);
          this.connectionSubject.next(true);
          const userName = frame.headers['user-name'] || 'User';
          console.log('Connected to WebSocket: ' + userName);
        },
        onDisconnect: () => {
          this.isConnected.set(false);
          this.connectionSubject.next(false);
          console.log('Disconnected from WebSocket');
        },
        onStompError: (frame: IFrame) => {
          console.error('Broker reported error: ' + (frame.headers['message'] || 'Unknown error'));
          this.isConnected.set(false);
        },
        onWebSocketError: (error) => {
          console.error('WebSocket Error: ', error);
          this.isConnected.set(false);
        }
      });

      this.stompClient.activate();
    } catch (e) {
      console.error('RealtimeService: Initialization failed', e);
    }
  }



  subscribeToHealth(plantId: number) {
    if (this.stompClient && this.stompClient.connected) {
      return this.stompClient.subscribe(`/topic/health/${plantId}`, (message) => {
        this.healthUpdates$.next(JSON.parse(message.body));
      });
    }
    return null;
  }

  subscribeToCare(userId: number) {
    if (this.stompClient && this.stompClient.connected) {
      return this.stompClient.subscribe(`/topic/care/${userId}`, (message) => {
        this.careUpdates$.next(JSON.parse(message.body));
      });
    }
    return null;
  }

  ngOnDestroy() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}

