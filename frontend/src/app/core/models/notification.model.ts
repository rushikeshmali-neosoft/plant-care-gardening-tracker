export enum NotificationType {
  REMINDER = 'REMINDER',
  ALERT = 'ALERT',
  INFO = 'INFO'
}

export interface Notification {
  id: number;
  title: string;
  message: string;
  type: NotificationType;
  isRead: boolean;
  createdAt: string;
}

export interface MarkReadRequest {
  notificationIds: number[];
}
