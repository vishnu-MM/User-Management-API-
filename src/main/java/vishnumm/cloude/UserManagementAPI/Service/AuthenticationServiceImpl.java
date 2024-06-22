package vishnumm.cloude.UserManagementAPI.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vishnumm.cloude.UserManagementAPI.Configuration.JwtService;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Entity.User;
import vishnumm.cloude.UserManagementAPI.Repository.UserRepository;
import vishnumm.cloude.UserManagementAPI.Utility.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    
    @Override
    public AuthenticationResponse register( UserDTO userDTO ) {
        User newUser = new User();
        newUser.setFirstName( userDTO.getFirstName() );
        newUser.setLastName( userDTO.getLastName() );
        newUser.setUsername( userDTO.getEmail() );
        newUser.setRole( userDTO.getRole() );
        newUser.setPassword( passwordEncoder.encode( userDTO.getPassword() ) );
        newUser = repository.save( newUser );
        String jwtToken = jwtService.generateToken( newUser );
        userDTO.setId( newUser.getId() );
        return new AuthenticationResponse( jwtToken, userDTO );
    }

    @Override
    public AuthenticationResponse authenticate( UserDTO userDTO ) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken( userDTO.getEmail(), userDTO.getPassword() )
        );
        User authenticatedUser = repository.findByUsername( userDTO.getEmail() )
                .orElseThrow(()-> new UsernameNotFoundException("User with this username is not fount") );
        String jwtToken = jwtService.generateToken( authenticatedUser );

        userDTO.setId( authenticatedUser.getId() );
        userDTO.setFirstName( authenticatedUser.getFirstName() );
        userDTO.setLastName( authenticatedUser.getLastName() );
        userDTO.setEmail( authenticatedUser.getUsername() );
        userDTO.setPassword( authenticatedUser.getPassword() );
        userDTO.setRole( authenticatedUser.getRole() );

        return new AuthenticationResponse( jwtToken, userDTO );
    }
}