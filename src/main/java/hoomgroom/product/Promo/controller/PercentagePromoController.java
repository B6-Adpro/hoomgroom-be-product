package hoomgroom.product.Promo.controller;

import hoomgroom.product.Promo.dto.PercentagePromoRequest;
import hoomgroom.product.Promo.model.PercentagePromo;
import hoomgroom.product.Promo.service.PercentagePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/promo/percent")
@RequiredArgsConstructor
public class PercentagePromoController {
    private final PercentagePromoService service;
    @GetMapping("/")
    public ResponseEntity<List<PercentagePromo>> getAllPercentagePromo() {
        List<PercentagePromo> response = service.findAll();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PercentagePromo> getPercentagePromoById(@PathVariable UUID id) {
        PercentagePromo response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<PercentagePromo> createPercentagePromo(@RequestBody PercentagePromoRequest request) {
        PercentagePromo response = service.create(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PercentagePromo> updatePercentagePromo(@PathVariable UUID id,
                                                                  @RequestBody PercentagePromoRequest request) {
        PercentagePromo response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePercentagePromo(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(String.format("Successfully deleted Promo with id %s", id.toString()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promo not found");
        }
    }

}
