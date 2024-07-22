package store.auroraauction.be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberToTalResponseDTO {
    int totalMember;
    int ClientQuantity;
    int ManagerQuantity;
    int StaffQuantity;
    int BuyerQuantity;
    int SellerQuantity;
}
