package hoomgroom.product.promo.controller;

import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.dto.RedeemPromoRequest;
import hoomgroom.product.promo.dto.RedeemResponse;
import hoomgroom.product.promo.dto.Response;
import hoomgroom.product.promo.model.Promo;
import hoomgroom.product.promo.service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromoController {
    private final PromoService promoService;

    @GetMapping("/")
    public ResponseEntity<List<Promo>> getAllPromos() {
        return promoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoResponse> getPromoById(@PathVariable UUID id) {
        return promoService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deletePromo(
            @PathVariable UUID id
    ) throws ExecutionException, InterruptedException {
        return promoService.delete(id);
    }

    @GetMapping("/redeem/{id}")
    public ResponseEntity<RedeemResponse> redeemPromo(
            @PathVariable UUID id,
            @RequestBody RedeemPromoRequest requestBody
    ) {
        return promoService.redeem(requestBody.getTransactionId(), id, requestBody.getTotalPrice());
    }
}