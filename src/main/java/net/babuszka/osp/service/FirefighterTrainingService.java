package net.babuszka.osp.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.FirefighterTraining;
import net.babuszka.osp.repository.FirefighterTrainingRepository;
import net.babuszka.osp.repository.FirefighterTypeRepository;

@Service
public class FirefighterTrainingService {

	private Logger LOG = LoggerFactory.getLogger(FirefighterTypeRepository.class);
	
	private FirefighterTrainingRepository firefighterTrainingRepository;

	@Autowired
	public void setFirefighterTrainingRepository(FirefighterTrainingRepository firefighterTrainingRepository) {
		this.firefighterTrainingRepository = firefighterTrainingRepository;
	}

	public FirefighterTraining getFirefighterTraining(Integer id) {
		LOG.debug("Getting the Firefighter training with following ID: " + id);
		try {
			LOG.debug("Found the Firefighter trainig: " + firefighterTrainingRepository.getOne(id).toString());
			return firefighterTrainingRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting firefighter's training details: " + e.getMessage());
		}
		return null;
	}
		
	public void saveFirefighterTraining(FirefighterTraining training) {
		firefighterTrainingRepository.save(training);
	}
	
	public void saveAllFirefighterTrainings(List<FirefighterTraining> trainings) {
		LOG.debug("There are " + trainings.size() + " trainings submitted");
		for(FirefighterTraining ft : trainings) {
			LOG.debug(ft.toString());
			if(ft.getFirefighterId() != null) {
				firefighterTrainingRepository.save(ft);
			}
		}
	}
	
	public void deleteFirefighterTraining(Integer id) {
		LOG.debug("ID given: " + id);
		try {
			firefighterTrainingRepository.deleteById(id);
		} catch(Exception e) {
			LOG.debug("An error occured during deletion of firefighter training: " + e.getMessage());
		} 
	}
	
}
