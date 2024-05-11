package hoomgroom.product.Promo.controller;

import hoomgroom.product.Promo.model.Promo;
import hoomgroom.product.Promo.service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromoController {
    private final PromoService promoService;

    @GetMapping("/")
    public ResponseEntity<List<Promo>> getAllPromos() {
        List<Promo> response = promoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromoById(@PathVariable UUID id) {
        try {
            Promo response = promoService.findById(id);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promo not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePromo(@PathVariable UUID id) {
        try {
            promoService.delete(id);
            return ResponseEntity.ok(String.format("Successfully deleted Promo with id %s", id.toString()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promo not found");
        }
    }
}
