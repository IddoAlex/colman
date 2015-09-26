package model;

import controller.IController;

public interface MVCModel extends IModel {

	/**
	 * Sets the controller.
	 *
	 * @param aController the new controller
	 */
	public void setController(IController aController);

}
