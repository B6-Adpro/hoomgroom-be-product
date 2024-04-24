package hoomgroom.product.Promo.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Promo {
    UUID uuid;
    String name;
    String description;
    Date expirationDate;
    Long minimumPurchase;

    public Promo() {}
}
