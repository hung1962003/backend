package store.auroraauction.be.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CartResponse {
    private long cart_id;
    private Set<Long> jewelry_id;
    private long user_id;
}
