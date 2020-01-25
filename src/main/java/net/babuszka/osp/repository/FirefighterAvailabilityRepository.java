package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.babuszka.osp.model.FirefighterAvailability;

public interface FirefighterAvailabilityRepository extends JpaRepository<FirefighterAvailability, Integer> {

	@Query(value = "SELECT * FROM firefighter_availability WHERE (firefighter_id = ?1 AND valid_before IS null)", nativeQuery = true)
	FirefighterAvailability findCurrentStatus(Integer firefighterId);

}
