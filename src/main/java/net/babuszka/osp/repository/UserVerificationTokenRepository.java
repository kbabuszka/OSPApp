package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.babuszka.osp.model.UserVerificationToken;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Integer> {

	@Query(value = "SELECT * FROM user_verification_tokens WHERE token = ?1", nativeQuery = true)
	UserVerificationToken findByToken(String token);
	
	@Query(value = "SELECT * FROM user_verification_tokens t WHERE t.user_id = ?1", nativeQuery = true)
	UserVerificationToken findTokenByUserId(Integer id);
	
	@Modifying
	@Query(value = "DELETE FROM user_verification_tokens WHERE user_id = ?1", nativeQuery = true)
	void deleteByUserId(Integer id);

}
