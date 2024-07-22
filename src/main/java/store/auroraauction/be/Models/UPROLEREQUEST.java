package store.auroraauction.be.Models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.RoleEnum;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UPROLEREQUEST {
    @Enumerated(value = EnumType.STRING)
    RoleEnum roleEnum;
}
