package net.babuszka.osp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class FirefighterTrainingWrapper {

	@Autowired
	public FirefighterTrainingWrapper() {
		super();
	}

	private List<FirefighterTraining> firefighterTrainings;

	public List<FirefighterTraining> getFirefighterTrainings() {
		return firefighterTrainings;
	}

	public void setFirefighterTrainings(List<FirefighterTraining> firefighterTrainings) {
		this.firefighterTrainings = firefighterTrainings;
	}
	
	
}
