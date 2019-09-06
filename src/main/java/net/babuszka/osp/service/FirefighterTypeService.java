package net.babuszka.osp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.FirefighterType;
import net.babuszka.osp.repository.FirefighterTypeRepository;

@Service
public class FirefighterTypeService {

	private Logger LOG = LoggerFactory.getLogger(FirefighterTypeRepository.class);
	
	private FirefighterTypeRepository firefighterTypeRepository;

	@Autowired
	public void setFirefighterTypeRepository(FirefighterTypeRepository firefighterTypeRepository) {
		this.firefighterTypeRepository = firefighterTypeRepository;
	}
	
	public FirefighterType getFirefighterType(int id) {
		
		LOG.debug("Getting the Firefighter type with following ID: " + id);
		try {
			LOG.debug("Found the Firefighter type: " + firefighterTypeRepository.getOne(id).toString());
			return firefighterTypeRepository.getOne(id);
		} catch (Exception e) {
			LOG.error("An error occured during getting firefighter's type details: " + e.getMessage());
		}
		return null;
	}
	
	public List<FirefighterType> getAllFirefighterTypes() {
		return firefighterTypeRepository.findAll();
	}
	
	public void saveFirefighterType(FirefighterType newFirefighterType) {
		firefighterTypeRepository.save(newFirefighterType);
	}
	
	public void updateFirefighterTypes(List<FirefighterType> editedFirefighterTypes) {
		LOG.debug("There are " + firefighterTypeRepository.count() + " training types in the repository");
		LOG.debug(editedFirefighterTypes.size() + " training types has been sent from the form");
		List<FirefighterType> typesFromRepo = firefighterTypeRepository.findAll();
		typesFromRepo = editedFirefighterTypes;
		firefighterTypeRepository.saveAll(typesFromRepo);
	}
	
	public void deleteFirefighterType(Integer id) {
		try {
			firefighterTypeRepository.deleteById(id);
		} catch (Exception e) {
			LOG.error("An error occured during deletion of firefighter type: " + e.getMessage());
		}
	}
}
