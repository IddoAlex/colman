package gui;

import java.util.Observable;

public class ObservableMember extends Observable {

	public void SetChanged() {
		this.setChanged();
	}
	
	public void changeAndNotifyObservers(Object arg) {
		this.setChanged();
		notifyObservers(arg);
	}
}
