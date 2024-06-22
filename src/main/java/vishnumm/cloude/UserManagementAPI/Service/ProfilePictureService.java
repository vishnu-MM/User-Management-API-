package vishnumm.cloude.UserManagementAPI.Service;

import org.springframework.web.multipart.MultipartFile;
import vishnumm.cloude.UserManagementAPI.Entity.ProfilePicture;

import java.io.IOException;

public interface ProfilePictureService {
    ProfilePicture uploadImage( MultipartFile file, Long userId ) throws IOException;
    ProfilePicture downloadImage( Long userId ) throws IOException;
}