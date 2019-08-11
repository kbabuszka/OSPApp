package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.FirefighterType;


@Repository
public interface FirefighterTypeRepository extends JpaRepository<FirefighterType, Integer> {

}
