package ist.meic.cmu.repository;

import ist.meic.cmu.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jp_s on 4/10/2017.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
