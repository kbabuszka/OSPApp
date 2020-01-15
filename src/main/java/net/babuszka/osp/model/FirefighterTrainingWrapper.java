package net.babuszka.osp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirefighterTrainingWrapper {

	@Autowired
	public FirefighterTrainingWrapper() {
		super();
	}

	private List<FirefighterTraining> firefighterTrainings;

}
