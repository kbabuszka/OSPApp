package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	
	@Modifying
	@Query(value = "DELETE FROM user_roles t WHERE t.user_id = ?1", nativeQuery = true)
	void deleteByUserId(Integer id);

}
