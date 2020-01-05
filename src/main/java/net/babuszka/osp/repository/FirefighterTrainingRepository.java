package net.babuszka.osp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.FirefighterTraining;

@Repository
public interface FirefighterTrainingRepository extends JpaRepository<FirefighterTraining, Integer> {

	@Query(value = "SELECT * FROM firefighter_trainings WHERE firefighter_id = ?1", nativeQuery = true)
	List<FirefighterTraining> findFirefighterTrainingsAllFirefighterTrainings(Integer firefighterId);

}
