package store.auroraauction.be.Models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JewelryCountResponse {
    private int JewelryIsSold;
    private int JewelryNotSold;
}
