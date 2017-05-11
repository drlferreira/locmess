package ist.meic.cmu.repository;

import ist.meic.cmu.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Diogo on 10/05/2017.
 */
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
