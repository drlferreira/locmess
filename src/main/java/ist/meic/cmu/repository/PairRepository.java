package ist.meic.cmu.repository;

import ist.meic.cmu.domain.Pair;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Diogo on 13/05/2017.
 */
public interface PairRepository extends JpaRepository<Pair, String> {
}
