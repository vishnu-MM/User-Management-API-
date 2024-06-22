package vishnumm.cloude.UserManagementAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vishnumm.cloude.UserManagementAPI.Entity.ProfilePicture;
import vishnumm.cloude.UserManagementAPI.Entity.User;
import java.util.Optional;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
    Optional<ProfilePicture> findByUser(User user);
}
