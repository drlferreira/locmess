package ist.meic.cnv.repository;

import ist.meic.cnv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Diogo on 05/04/2017.
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u where u.username=:username and u.password=:password")
    public User findUser(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT u FROM User u where u.username=:username")
    public User findUserByUsername(@Param("username") String username);

}
