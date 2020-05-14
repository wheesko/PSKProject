import { notification } from 'antd';
import * as React from 'react';

export interface NotificationProps {
	notificationType: NotificationType;
    message: React.ReactNode;
    description?: React.ReactNode;
    onClose?: () => void;
    duration?: number | null;
    icon?: React.ReactNode;
    className?: string;
    onClick?: () => void;
    closeIcon?: React.ReactNode;
}

export enum NotificationType {
    SUCCESS,
    WARNING,
    ERROR,
    INFO
}

class NotificationService {
	public notify = (config: NotificationProps) => {
		switch (config.notificationType) {
		case NotificationType.SUCCESS:
			notification.success(config);
			break;
		case NotificationType.ERROR:
			notification.error(config);
			break;
		case NotificationType.WARNING:
			notification.warning(config);
			break;
		case NotificationType.INFO:
			notification.info(config);
			break;
		default:
			break;
		}
	}
}
const notificationService = new NotificationService();

export { NotificationService };
export { notificationService as default };
