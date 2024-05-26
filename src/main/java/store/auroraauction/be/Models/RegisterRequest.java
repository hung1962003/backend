package store.auroraauction.be.Models;

import lombok.Data;

@Data // su dung dc getter and setter
public class RegisterRequest {
    String phone;
    String password;
}
