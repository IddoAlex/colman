package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxCreator {
	
	public static int createErrorMessageBox(Shell parent, String errorMessage) {
		return createMessageBox(parent, SWT.ERROR | SWT.OK, "Error occured!", errorMessage);
	}
	
	public static int createNotificationMessageBox(Shell parent, String notificationMessage) {
		return createNotificationMessageBox(parent, "Notification", notificationMessage);
	}
	
	public static int createNotificationMessageBox(Shell parent, String title, String notificationMessage) {
		return createMessageBox(parent, SWT.ICON_INFORMATION | SWT.OK, title, notificationMessage);
	}
	
	public static int createMessageBox(Shell parent, int style, String title, String message) {
		MessageBox mb = new MessageBox(parent, style);
		mb.setText(title);
		mb.setMessage(message);
		return mb.open();
	}
}
