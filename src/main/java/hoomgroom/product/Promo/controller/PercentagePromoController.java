package hoomgroom.product.promo.controller;

import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.service.PercentagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promo/percent")
@RequiredArgsConstructor
public class PercentagePromoController {
    private final PercentagePromoService service;
    @GetMapping("/")
    public ResponseEntity<List<PercentagePromo>> getAllPercentagePromo() {
        return service.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<PromoResponse> createPercentagePromo(
            @RequestBody PercentagePromoRequest requestBody
    ) {
        return service.create(requestBody);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PromoResponse> updatePercentagePromo(
            @PathVariable UUID id,
            @RequestBody PercentagePromoRequest requestBody
    ) {
        return service.update(id, requestBody);
    }
}