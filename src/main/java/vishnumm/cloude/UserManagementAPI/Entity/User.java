package vishnumm.cloude.UserManagementAPI.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vishnumm.cloude.UserManagementAPI.Utility.Role;
import java.util.Collection;
import java.util.List;

@Entity
@Table( name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column( name = "first_name" )
    private String firstName;
    @Column( name = "last_name" )
    private String lastName;
    @Column( name = "email" )
    private String username;
    @Column( name = "password" )
    private String password;
    @Column( name = "role" )
    @Enumerated( value = EnumType.STRING )
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority( role.name() ) );
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
