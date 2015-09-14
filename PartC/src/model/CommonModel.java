package model;

import controller.IController;

public abstract class CommonModel implements IModel {
	
	IController controller;
	
	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}

}
