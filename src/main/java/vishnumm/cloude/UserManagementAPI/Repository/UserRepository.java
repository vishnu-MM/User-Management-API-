package vishnumm.cloude.UserManagementAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vishnumm.cloude.UserManagementAPI.Entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername( String username );
    List<User> searchUsersByUsernameContainingIgnoreCase( String username );
    List<User> searchUsersByFirstNameContainingIgnoreCase( String firstName );
    List<User> searchUsersByLastNameContainingIgnoreCase( String lastName );
}
