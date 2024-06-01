package store.auroraauction.be.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.enums.RoleEnum;

@Getter
@Setter
public class AccountResponse extends Account {
    private String token;
    private long id;
    private String username;

    private String password;
    private String Firstname;
    private String Lastname;

    private String phoneNumber;

    private String address;
    private String email;

    private RoleEnum roleEnum;
}
