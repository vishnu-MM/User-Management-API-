package vishnumm.cloude.UserManagementAPI.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Service.UserService;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    private final UserService service;

    @GetMapping("/users-list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        System.out.println("Getting");
        return ResponseEntity.ok( service.findAllUsers() );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getSingleUser( @PathVariable("id") Long userID ) {
        return ResponseEntity.ok( service.findUserByUserId( userID ) );
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers( @RequestParam("searchQuery") String searchQuery ) {
        return ResponseEntity.ok( service.searchUser( searchQuery ) );
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserDTO> createNewUser( @RequestBody UserDTO userDTO ) {
        return ResponseEntity.ok( service.saveUser( userDTO ) );
    }

    @PutMapping("/update-user")
    public ResponseEntity<UserDTO> updateUser( @RequestBody UserDTO userDTO ) {
        System.out.println(userDTO);
        return ResponseEntity.ok( service.updateUser( userDTO ) );
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<Void> deleteUser( @PathVariable Long userId ) {
        if (userId == null || !service.isExistsByUserId(userId)) {
            return ResponseEntity.badRequest().build();
        }
        UserDTO userDTO = service.findUserByUserId(userId);
        service.deleteUser( userDTO );
        return ResponseEntity.ok().build();
    }
}