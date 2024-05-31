package store.auroraauction.be.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    String username;
    String password;
}
