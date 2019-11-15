package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.babuszka.osp.model.UserVerificationToken;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Integer> {

	@Query(value = "SELECT * FROM user_verification_tokens WHERE token = ?1", nativeQuery = true)
	UserVerificationToken findByToken(String token);

}
