package vishnumm.cloude.UserManagementAPI.Service;

import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findUserByUsername( String username );
    UserDTO findUserByUserId( Long userId );
    Boolean isExistsByUserId( Long userId );
    List<UserDTO> findAllUsers();
    List<UserDTO> searchUser( String searchQuery );
    UserDTO saveUser( UserDTO userDTO );
    UserDTO updateUser( UserDTO userDTO );
    void deleteUser( UserDTO userDTO );
}