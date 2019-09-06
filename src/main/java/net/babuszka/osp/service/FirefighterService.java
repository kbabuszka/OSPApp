package net.babuszka.osp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.repository.FirefighterRepository;

@Service
public class FirefighterService {
	
	private Logger LOG = LoggerFactory.getLogger(FirefighterService.class);

	private FirefighterRepository firefighterRepository;

	@Autowired
	public void setFirefighterRepository(FirefighterRepository firefighterRepository) {
		this.firefighterRepository = firefighterRepository;
	}
	
	public Firefighter getFirefighter(Integer id) {
		LOG.debug("Getting the Firefighter with following ID: " + id);
		try {
			LOG.debug("Found the Firefighter: " + firefighterRepository.getOne(id).getFirstName() + " " + firefighterRepository.getOne(id).getLastName() + ". This firefighter has " + firefighterRepository.getOne(id).getTrainings().size() + " certificates assigned.");
			return firefighterRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting firefighter's details: " + e.getMessage());
		}
		return null;
	}
	
	public boolean getFirefighter(String pesel, Integer excludedId) {
		LOG.debug("Getting the Firefighter with following pesel: " + pesel);
		Firefighter foundFirefighter = firefighterRepository.findByPesel(pesel);
		if (foundFirefighter != null) {
			LOG.debug("Found the Firefighter: " + foundFirefighter.getFirstName() + " " + foundFirefighter.getLastName());
			if (foundFirefighter.getId() != excludedId) {
				return true;
			} else {
				LOG.debug("This PESEL belongs to the same firefighter");
				return false;
			}
		} else {
			return false;
		}
	}
	
	public List<Firefighter> getAllFirefighters(){
		LOG.debug("There are " + firefighterRepository.count() + " firefighters in the repository");
		for(Firefighter firefighter : firefighterRepository.findAll()) {
			LOG.debug(firefighter.toString());
		}
		return firefighterRepository.findAll();
	}
	
	public void saveFirefighter(Firefighter firefighter) {
		firefighterRepository.save(firefighter);
	}
	
	public void updateFirefighter(Firefighter firefighterToUpdate, Integer id) {
		
		Firefighter foundFirefighter = firefighterRepository.findById(id).get();
		try {
			foundFirefighter = firefighterToUpdate;
			firefighterRepository.save(foundFirefighter);
		} catch (Exception e) {
			LOG.debug("An error occured during update of firefighter: " + e.getMessage());
		}
	}
	
	public void deleteFirefighter(Integer id) {
		LOG.debug("ID given: " + id);
		try {
			firefighterRepository.deleteById(id);
		} catch(Exception e) {
			LOG.info("An error occured during deletion of firefighter: " + e.getMessage());
		} 
	}
	
	public List<Firefighter> getJotFirefighters(){
		LOG.debug("There are " + firefighterRepository.count() + " firefighters in the repository");
		for(Firefighter firefighter : firefighterRepository.findJotFirefighters()) {
			LOG.debug(firefighter.toString());
		}
		return firefighterRepository.findJotFirefighters();
	}
}

