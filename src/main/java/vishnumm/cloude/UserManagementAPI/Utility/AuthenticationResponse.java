package vishnumm.cloude.UserManagementAPI.Utility;

import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;

public record AuthenticationResponse(String jwtToken, UserDTO user ) {}