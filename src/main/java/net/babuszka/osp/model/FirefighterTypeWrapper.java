package net.babuszka.osp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class FirefighterTypeWrapper {
	
	@Autowired
	public FirefighterTypeWrapper() {
		super();
	}

	private List<FirefighterType> types;

	public List<FirefighterType> getTypes() {
		return types;
	}

	public void setTypes(List<FirefighterType> types) {
		this.types = types;
	}
	
	
	
}
