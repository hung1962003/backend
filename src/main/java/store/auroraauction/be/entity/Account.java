package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.auroraauction.be.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String username;
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)//  password
    private String password;
    private String Firstname;
    private String Lastname;
    @Column(unique=true)
    private String phoneNumber;
    private String address;
    @Column(unique=true)
    private String email;
    @Enumerated(value = EnumType.STRING)
    RoleEnum roleEnum;

    @ManyToMany
    @JoinTable(name = "account_auction",
            joinColumns = @JoinColumn(name="account_id"),
            inverseJoinColumns = @JoinColumn(name="auction_id"))
            @JsonManagedReference
    Set<Auction> auctions;

    @OneToOne(mappedBy ="account")
    private Identify identify;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Jewelry> jewelries;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Bid> bid;

    @OneToOne(mappedBy ="account")
    private Cart cart;

    //    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id",referencedColumnName = "id")
    private Wallet wallet;// biến wallet này sẽ trùng  với giá trị  mappedBy trong Class Wallet

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Request> requests;



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
