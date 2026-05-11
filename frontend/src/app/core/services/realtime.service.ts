import { Injectable, OnDestroy, signal, inject } from '@angular/core';
import { Client, IFrame } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from '../../../environments/environment';
import { Subject } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RealtimeService implements OnDestroy {
  private authService = inject(AuthService);
  private stompClient: Client | null = null;
  private connectionSubject = new Subject<boolean>();
  
  isConnected = signal(false);
  healthUpdates$ = new Subject<any>();
  careUpdates$ = new Subject<any>();
  notificationUpdates$ = new Subject<any>();

  constructor() {
    this.connect();
  }

  connect() {
    // Correct URL: point to backend root, not /api/v1
    const url = new URL(environment.apiUrl);
    const wsUrl = `${url.origin}/ws-plant-care`;
    
    const token = localStorage.getItem('access_token');

    try {
      this.stompClient = new Client({
        webSocketFactory: () => {
          const SockJSConstructor = (SockJS as any).default || SockJS;
          return new SockJSConstructor(wsUrl);
        },
        connectHeaders: {
          Authorization: token ? `Bearer ${token}` : ''
        },
        debug: (msg) => {
          // Keep debug in development
          if (!environment.production) console.log('STOMP: ' + msg);
        }, 
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: (frame: IFrame) => {
          this.isConnected.set(true);
          this.connectionSubject.next(true);
          console.log('Connected to WebSocket');
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
    if (this.stompClient && this.stompClient.active) {
      return this.stompClient.subscribe(`/topic/health/${plantId}`, (message) => {
        this.healthUpdates$.next(JSON.parse(message.body));
      });
    } else {
      // If not yet active, wait for connection and then subscribe
      const sub = this.connectionSubject.subscribe(connected => {
        if (connected && this.stompClient) {
          this.stompClient.subscribe(`/topic/health/${plantId}`, (message) => {
            this.healthUpdates$.next(JSON.parse(message.body));
          });
          sub.unsubscribe();
        }
      });
      return null;
    }
  }

  subscribeToCare(userId: number) {
    if (this.stompClient && this.stompClient.active) {
      return this.stompClient.subscribe(`/topic/care/${userId}`, (message) => {
        this.careUpdates$.next(JSON.parse(message.body));
      });
    } else {
      // If not yet active, wait for connection and then subscribe
      const sub = this.connectionSubject.subscribe(connected => {
        if (connected && this.stompClient) {
          this.stompClient.subscribe(`/topic/care/${userId}`, (message) => {
            this.careUpdates$.next(JSON.parse(message.body));
          });
          sub.unsubscribe();
        }
      });
      return null;
    }
  }

  ngOnDestroy() {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
    this.connectionSubject.complete();
  }
}
