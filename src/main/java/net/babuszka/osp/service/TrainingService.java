package net.babuszka.osp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Training;
import net.babuszka.osp.repository.TrainingRepository;

@Service
public class TrainingService {
	
	private Logger LOG = LoggerFactory.getLogger(Logger.class);

	private TrainingRepository trainingRepository;

	@Autowired
	public void setTrainingRepository(TrainingRepository trainingRepository) {
		this.trainingRepository = trainingRepository;
	}
	
	public Training getTraining(int id) {
		
		LOG.debug("Getting Training type with following ID: " + id);
		try {
			LOG.debug("Found the Training type: " + trainingRepository.getOne(id).toString());
			return trainingRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting training type details: " + e.getMessage());
		}
		return null;
	}
	
	public List<Training> getAllTrainings() {
		LOG.debug("There are " + trainingRepository.count() + " training types in the repository");
		for(Training training : trainingRepository.findAll()) {
			LOG.debug(training.toString());
		}
		return trainingRepository.findAll();
	}
	
	public void saveTraining(Training training) {
		trainingRepository.save(training);
	}
	
	public void updateTrainings(List<Training> editedTrainings) {
		LOG.debug("There are " + trainingRepository.count() + " training types in the repository");
		LOG.debug(editedTrainings.size() + " training types has been sent from the form");
		List<Training> trainingsFromRepo = trainingRepository.findAll();
		trainingsFromRepo = editedTrainings;
		trainingRepository.saveAll(trainingsFromRepo);
	}
	
	public void deleteTraining(Integer id) {
		try {
			trainingRepository.deleteById(id);
		} catch(Exception e) {
			LOG.error("An error occured during deletion of training: " + e.getMessage());
		}
	}
}
