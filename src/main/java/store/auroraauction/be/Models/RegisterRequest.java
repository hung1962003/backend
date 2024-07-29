package store.auroraauction.be.Models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import store.auroraauction.be.enums.RoleEnum;

@Getter
@Setter
public class RegisterRequest {
    String username;
    String password;
    String email;
    String phoneNumber ;
    String address;
    String LastName;
    String FirstName;

}
//hung44570@gmail.com