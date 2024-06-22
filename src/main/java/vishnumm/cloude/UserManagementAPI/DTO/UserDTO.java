package vishnumm.cloude.UserManagementAPI.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import vishnumm.cloude.UserManagementAPI.Utility.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NonNull private String firstName;
    private String lastName;
    @NonNull private String email;
    @NonNull private String password;
    @NonNull @Enumerated( value = EnumType.STRING )
    private Role role;
}
