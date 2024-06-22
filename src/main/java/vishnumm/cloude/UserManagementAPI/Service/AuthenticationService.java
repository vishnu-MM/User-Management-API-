package vishnumm.cloude.UserManagementAPI.Service;

import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Utility.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(UserDTO user );
    AuthenticationResponse authenticate( UserDTO userDTO );
}