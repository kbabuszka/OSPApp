package net.babuszka.osp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.babuszka.osp.model.FirefighterAvailability;

public interface FirefighterAvailabilityRepository extends JpaRepository<FirefighterAvailability, Integer> {

	@Query(value = "SELECT * FROM firefighter_availability WHERE (firefighter_id = ?1 AND valid_before IS null)", nativeQuery = true)
	FirefighterAvailability findCurrentStatus(Integer firefighterId);

	@Query(value = "SELECT * FROM firefighter_availability ORDER BY id DESC LIMIT ?1", nativeQuery = true)
	List<FirefighterAvailability> findLatestStatusChanges(Integer amount);
	
	@Query(value = "SELECT COUNT(*) FROM firefighter_availability " + 
					"JOIN firefighters ON firefighter_availability.firefighter_id=firefighters.id " + 
					"WHERE  firefighters.in_jot=1 AND " + 
					"availability=1 AND valid_before IS NULL;", nativeQuery = true)
	Integer countAvailableFirefighters();

	@Query(value = "SELECT * FROM firefighter_availability WHERE firefighter_id = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
	List<FirefighterAvailability> findFirefighterStatusChanges(Integer id, Integer amount);
}
