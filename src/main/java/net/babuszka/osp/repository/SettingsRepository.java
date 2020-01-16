package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Setting;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Integer> {

	Setting findByName(String name);
}