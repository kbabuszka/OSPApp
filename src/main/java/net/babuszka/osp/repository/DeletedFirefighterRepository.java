package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.DeletedFirefighter;

@Repository
public interface DeletedFirefighterRepository extends JpaRepository<DeletedFirefighter, Integer> {
}
