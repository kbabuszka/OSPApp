package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.FirefighterTraining;

@Repository
public interface FirefighterTrainingRepository extends JpaRepository<FirefighterTraining, Integer> {

}
