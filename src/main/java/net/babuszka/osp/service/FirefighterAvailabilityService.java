package net.babuszka.osp.service;

import java.util.List;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.babuszka.osp.model.Firefighter;
import net.babuszka.osp.model.FirefighterAvailability;
import net.babuszka.osp.model.FirefighterAvailabilityType;
import net.babuszka.osp.repository.FirefighterAvailabilityRepository;
import net.babuszka.osp.repository.FirefighterRepository;

@Service
public class FirefighterAvailabilityService {

	private Logger LOG = LoggerFactory.getLogger(FirefighterAvailabilityService.class);
	private FirefighterAvailabilityRepository firefighterAvailabilityRepository;
	private FirefighterRepository firefighterRepository;

	@Autowired
	public void setFirefighterAvailabilityRepository(FirefighterAvailabilityRepository firefighterAvailabilityRepository) {
		this.firefighterAvailabilityRepository = firefighterAvailabilityRepository;
	}
	
	@Autowired
	public void setFirefighterRepository(FirefighterRepository firefighterRepository) {
		this.firefighterRepository = firefighterRepository;
	}

	public FirefighterAvailability getCurrentFirefighterAvailability(Integer firefighterId) {
		FirefighterAvailability availability = firefighterAvailabilityRepository.findCurrentStatus(firefighterId);
		if(availability != null)
			LOG.info("Firefighter (" + firefighterId + ") availability: " + availability.getType());
		return availability;
	}
	
	public void toggleFirefighterAvailabilty(Integer firefighterId) {
		FirefighterAvailability availability = firefighterAvailabilityRepository.findCurrentStatus(firefighterId);
		Firefighter firefighter = firefighterRepository.findById(firefighterId).orElse(null);
		if (firefighter == null || !firefighter.getIsInJOT()) {
			LOG.info("Firefighter with ID " + firefighterId + " doesn't exists or is not in JOT");
			return;
		}
		if(availability == null) {
			LOG.info("Firefighter with ID " + firefighterId + " don't have any status set. Setting the status to UNAVAILABLE");
			availability = new FirefighterAvailability();
			availability.setFirefighter(firefighter);
			availability.setType(FirefighterAvailabilityType.UNAVAILABLE);
			availability.setValidFrom(LocalDateTime.now());
			firefighterAvailabilityRepository.save(availability);
		} else {
			availability.setValidBefore(LocalDateTime.now());
			firefighterAvailabilityRepository.save(availability);
			FirefighterAvailability newAvailability = new FirefighterAvailability();
			newAvailability.setFirefighter(firefighter);
			if (availability.getType().equals(FirefighterAvailabilityType.UNAVAILABLE)) {
				LOG.info("Firefighter with ID " + firefighterId + " has status UNAVAILABLE. Changing it to AVAILABLE");
				newAvailability.setType(FirefighterAvailabilityType.AVAILABLE);
			} else {
				LOG.info("Firefighter with ID " + firefighterId + " has status AVAILABLE. Changing it to UNAVAILABLE");
				newAvailability.setType(FirefighterAvailabilityType.UNAVAILABLE);
			}
			newAvailability.setValidFrom(LocalDateTime.now());
			firefighterAvailabilityRepository.save(newAvailability);
		}
	}
	
	public List<FirefighterAvailability> getLatestStatusChanges(Integer amount) {
		List<FirefighterAvailability> latest = firefighterAvailabilityRepository.findLatestStatusChanges(amount);
		LOG.info("Got " + latest.size() + " latest status changes from database.");
		return latest;
	}
	
	public Integer getAvailableFirefightersCount() {
		return firefighterAvailabilityRepository.countAvailableFirefighters();
	}

	public List<FirefighterAvailability> getFirefighterAvailabilityHistory(Integer id, Integer amount) {
		List<FirefighterAvailability> latest = firefighterAvailabilityRepository.findFirefighterStatusChanges(id, amount);
		LOG.info("Got " + latest.size() + " status changes for firefighter with ID: " + id);
		return latest;
	}	
}
