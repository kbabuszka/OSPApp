package net.babuszka.osp.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByName(String name);
}
