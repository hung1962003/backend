package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.auroraauction.be.enums.RoleEnum;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)//  password
    private String password;
    private String Firstname;
    private String Lastname;
    @Column(unique=true)
    private String phoneNumber;
    @Column(unique = true)
    private String address;
    private String email;
    @Enumerated(value = EnumType.STRING)
    RoleEnum roleEnum;
//    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id",referencedColumnName = "id")
    private Wallet wallet;// biến wallet này sẽ trùng  với giá trị  mappedBy trong Class Wallet

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
