package store.auroraauction.be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProfitResponseDTO {
    int month;
    float revenuePortal;
    List<ListSystemProfitMapByDTO> systemProfits;
}
