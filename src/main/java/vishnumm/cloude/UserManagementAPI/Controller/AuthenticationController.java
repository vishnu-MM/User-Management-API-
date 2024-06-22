package vishnumm.cloude.UserManagementAPI.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Service.AuthenticationService;
import vishnumm.cloude.UserManagementAPI.Utility.AuthenticationResponse;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login( @RequestBody UserDTO userDTO ) {
        System.out.println("Login request from "+ userDTO );
        AuthenticationResponse response = service.authenticate( userDTO );
        System.out.println(response);
        return ResponseEntity.ok( response );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody UserDTO userDTO ) {
        System.out.println(userDTO);
        return ResponseEntity.ok( service.register( userDTO ) );
    }
}
