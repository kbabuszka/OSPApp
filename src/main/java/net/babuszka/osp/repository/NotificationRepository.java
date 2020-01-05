package net.babuszka.osp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.babuszka.osp.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	@Query(value = "UPDATE notifications SET is_read = 1 WHERE id = :id", nativeQuery = true)
	void markAsRead(Integer id);
}
