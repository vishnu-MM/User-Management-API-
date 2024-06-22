package vishnumm.cloude.UserManagementAPI.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Entity.ProfilePicture;
import vishnumm.cloude.UserManagementAPI.Service.ProfilePictureService;
import vishnumm.cloude.UserManagementAPI.Service.UserService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final ProfilePictureService profilePictureService;

    @GetMapping("/user-profile")
    public ResponseEntity<UserDTO> getSingleUser( Principal principal ) {
        return ResponseEntity.ok( service.findUserByUsername( principal.getName() ) );
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserDTO> updateUser( @RequestBody UserDTO userDTO ) {
        System.out.println(userDTO);
        return ResponseEntity.ok( service.updateUser( userDTO ) );
    }

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<ProfilePicture> uploadProfilePicture( @RequestBody MultipartFile image,
                                                                @RequestParam Long userId ) {
        System.out.println(( image == null)+" || "+!service.isExistsByUserId( userId ));
        if ( image == null || !service.isExistsByUserId( userId ))
            return ResponseEntity.badRequest().build();
        try { return ResponseEntity.ok( profilePictureService.uploadImage( image, userId) ); }
        catch (IOException e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}