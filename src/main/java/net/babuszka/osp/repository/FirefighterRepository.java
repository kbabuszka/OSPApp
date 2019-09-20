package net.babuszka.osp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Firefighter;

@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Integer> {

	@Query(value = "SELECT * FROM firefighters WHERE pesel = ?1", nativeQuery = true)
	public Firefighter findByPesel(String pesel);
	
	@Query(value = "SELECT * FROM firefighters WHERE in_jot = 1", nativeQuery = true)
	public List<Firefighter> findJotFirefighters();
	
}
