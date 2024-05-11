package hoomgroom.product.Promo.controller;

import hoomgroom.product.Promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.Promo.model.FixedAmountPromo;
import hoomgroom.product.Promo.service.FixedAmountPromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/promo/fixed")
@RequiredArgsConstructor
public class FixedAmountPromoController {
    private final FixedAmountPromoService service;
    @GetMapping("/")
    public ResponseEntity<List<FixedAmountPromo>> getAllFixedAmountPromo() {
        List<FixedAmountPromo> response = service.findAll();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FixedAmountPromo> getFixedAmountPromoById(@PathVariable UUID id) {
        FixedAmountPromo response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<FixedAmountPromo> createFixedAmountPromo(@RequestBody FixedAmountPromoRequest request) {
        FixedAmountPromo response = service.create(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FixedAmountPromo> updateFixedAmountPromo(@PathVariable UUID id, @RequestBody FixedAmountPromoRequest request) {
        FixedAmountPromo response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFixedAmountPromo(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(String.format("Successfully deleted Promo with id %s", id.toString()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promo not found");
        }
    }
}
