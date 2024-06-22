package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.*;
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
        private String firstname;
        private String lastname;
        @Column(unique=true)
        private String phoneNumber;
        private String address;
        @Column(unique=true)
        private String email;
        @Enumerated(value = EnumType.STRING)
        RoleEnum roleEnum;

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
        @JsonIgnore
        private List<RequestBuy> requests;

        @OneToOne(mappedBy ="manager")
        @JsonIgnore
        private RequestCompany requestCompany;

        @OneToOne(mappedBy ="staff")
        @JsonIgnore
        private RequestCompany requestCompany1;


    @ManyToMany(mappedBy ="accounts",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    Set<Auction> auctions;


    @OneToOne(mappedBy ="account")
    @JsonIgnore
    private Identify identify;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Jewelry> jewelries;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bid> bid;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart;

    //    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id",referencedColumnName = "id")
    @JsonIgnore
    private Wallet wallet;// biến wallet này sẽ trùng với giá trị  mappedBy trong Class Wallet


    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders;


    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> bills1;


    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> bills2 ;


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
