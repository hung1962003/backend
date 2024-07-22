package store.auroraauction.be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    String Jewelry_name;
    String Auction_name;
    String Buyer_name;
    String Seller_name;
    String Staff_name;
}
