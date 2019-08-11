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
	
//	public Optional<FirefighterTraining> getFirefighterTraining(Integer id) {
//		return firefighterTrainingRepository.findById(id);
//	}
	

	public FirefighterTraining getFirefighterTraining(Integer id) {
		LOG.info("Getting the Firefighter training with following ID: " + id);
		try {
			LOG.info("Found the Firefighter trainig: " + firefighterTrainingRepository.getOne(id).toString());
			return firefighterTrainingRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting firefighter's training details: " + e.getMessage());
		}
		return null;
	}
	
//	public List<FirefighterTraining> getAllTrainings(){
//		return firefighterTrainingRepository.findAll();
//	}
	
	public void saveFirefighterTraining(FirefighterTraining training) {
		firefighterTrainingRepository.save(training);
	}
	
	public void saveAllFirefighterTrainings(List<FirefighterTraining> trainings) {
		LOG.info("There are " + trainings.size() + " trainings submitted");
		for(FirefighterTraining ft : trainings) {
			LOG.info(ft.toString());
			if(ft.getFirefighterId() != null) {
				firefighterTrainingRepository.save(ft);
			}
		}
		//firefighterTrainingRepository.saveAll(trainings);
	}
	
	public void deleteFirefighterTraining(Integer id) {
		LOG.info("ID given: " + id);
		try {
			firefighterTrainingRepository.deleteById(id);
		} catch(Exception e) {
			LOG.info("An error occured during deletion of firefighter training: " + e.getMessage());
		} 
	}
	
}
