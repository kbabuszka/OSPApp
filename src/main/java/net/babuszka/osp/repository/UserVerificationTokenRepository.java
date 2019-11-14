package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.babuszka.osp.model.UserVerificationToken;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Integer> {

}
