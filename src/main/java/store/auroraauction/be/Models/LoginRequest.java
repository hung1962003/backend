package store.auroraauction.be.Models;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
