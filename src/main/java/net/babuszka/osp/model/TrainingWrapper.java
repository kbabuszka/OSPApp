package net.babuszka.osp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TrainingWrapper {

	@Autowired
	public TrainingWrapper() {
		super();
	}

	private List<Training> trainings;

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}
	
}
