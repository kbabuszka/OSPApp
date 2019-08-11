package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

}
