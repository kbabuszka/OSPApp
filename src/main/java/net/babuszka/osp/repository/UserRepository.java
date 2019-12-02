package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
	User findByUsername(String username);

	@Query(value = "SELECT * FROM users WHERE firefighter_id = ?1", nativeQuery = true)
	User findByFirefighterId(Integer id);
	
	@Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
	User findByEmail(String email);
	
}
