package ist.meic.cmu.repository;

import ist.meic.cmu.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Diogo on 05/04/2017.
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
