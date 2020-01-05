package net.babuszka.osp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Firefighter;

@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Integer> {

	@Query(value = "SELECT * FROM firefighters WHERE pesel = ?1", nativeQuery = true)
	Firefighter findByPesel(String pesel);
	
	@Query(value = "SELECT * FROM firefighters WHERE in_jot = 1", nativeQuery = true)
	List<Firefighter> findJotFirefighters();

	@Query(value = "SELECT * FROM firefighters WHERE user_id IS NULL", nativeQuery = true)
	List<Firefighter> findFirefightersWithNoAccount();
	
}
