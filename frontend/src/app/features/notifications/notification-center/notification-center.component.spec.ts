import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NotificationCenterComponent } from './notification-center.component';
import { NotificationService } from '../../../core/services/notification.service';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { signal } from '@angular/core';

describe('NotificationCenterComponent', () => {
  let component: NotificationCenterComponent;
  let fixture: ComponentFixture<NotificationCenterComponent>;
  let notificationServiceMock: any;

  beforeEach(async () => {
    notificationServiceMock = {
      getNotifications: jasmine.createSpy('getNotifications').and.returnValue(of([])),
      markAsRead: jasmine.createSpy('markAsRead').and.returnValue(of(undefined)),
      notifications: signal([]),
      unreadCount: signal(0),
      isLoading: signal(false)
    };

    await TestBed.configureTestingModule({
      imports: [NotificationCenterComponent, NoopAnimationsModule],
      providers: [
        { provide: NotificationService, useValue: notificationServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getNotifications on init', () => {
    expect(notificationServiceMock.getNotifications).toHaveBeenCalled();
  });
});
