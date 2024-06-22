package vishnumm.cloude.UserManagementAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vishnumm.cloude.UserManagementAPI.DTO.UserDTO;
import vishnumm.cloude.UserManagementAPI.Repository.UserRepository;
import vishnumm.cloude.UserManagementAPI.Service.UserService;
import vishnumm.cloude.UserManagementAPI.Service.UserServiceImpl;
import vishnumm.cloude.UserManagementAPI.Utility.Role;

@SpringBootApplication
public class UserManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApiApplication.class, args);
	}
}
