package hoomgroom.product.promo.controller;

import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.service.FixedAmountPromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/promo/fixed")
@RequiredArgsConstructor
public class FixedAmountPromoController {
    private final FixedAmountPromoService service;
    @GetMapping("/")
    public ResponseEntity<List<FixedAmountPromo>> getAllFixedAmountPromo() {
        return service.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<PromoResponse> createFixedAmountPromo(
            @RequestBody FixedAmountPromoRequest requestBody
    ) {
        return service.create(requestBody);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PromoResponse> updateFixedAmountPromo(
            @PathVariable UUID id,
            @RequestBody FixedAmountPromoRequest requestBody
    ) {
        return service.update(id, requestBody);
    }
}