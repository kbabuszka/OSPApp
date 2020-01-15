package net.babuszka.osp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirefighterTypeWrapper {
	
	@Autowired
	public FirefighterTypeWrapper() {
		super();
	}

	private List<FirefighterType> types;

}
