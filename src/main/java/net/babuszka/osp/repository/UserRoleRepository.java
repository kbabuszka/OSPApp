package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

}
