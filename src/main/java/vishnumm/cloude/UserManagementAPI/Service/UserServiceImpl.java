package vishnumm.cloude.UserManagementAPI.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Entity.User;
import vishnumm.cloude.UserManagementAPI.Repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with this username is not fount"));
        return EntityToDTO(user);
    }

    @Override
    public UserDTO findUserByUserId(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User with this username is not fount"));
        return EntityToDTO(user);
    }

    @Override
    public Boolean isExistsByUserId( Long userId ) {
        return repository.existsById( userId );
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = repository.findAll();
        System.out.println(users);
        return users.stream().map(this::EntityToDTO).toList();
    }

    @Override
    public List<UserDTO> searchUser(String searchQuery) {
        List<User> userList = repository.searchUsersByUsernameContainingIgnoreCase( searchQuery );
        userList.addAll( repository.searchUsersByFirstNameContainingIgnoreCase( searchQuery ) );
        userList.addAll( repository.searchUsersByLastNameContainingIgnoreCase( searchQuery ) );
        return userList.stream().map( this::EntityToDTO ).toList();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = DTOToEntity(userDTO);
        user.setPassword( passwordEncoder.encode( user.getUsername() ) );
        return EntityToDTO( repository.save( user ) );
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return EntityToDTO( repository.save( DTOToEntity(userDTO) ) );
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        repository.delete( DTOToEntity( userDTO ) );
    }

    private UserDTO EntityToDTO(User user) {
        return new UserDTO(
            user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getRole()
        );
    }
    private User DTOToEntity(UserDTO user) {
        return new User(
            user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole()
        );
    }
}