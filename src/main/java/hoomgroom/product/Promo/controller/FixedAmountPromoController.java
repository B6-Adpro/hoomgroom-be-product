package hoomgroom.product.promo.controller;

import hoomgroom.product.auth.service.JwtServiceImpl;
import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.service.FixedAmountPromoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/promo/fixed")
@RequiredArgsConstructor
public class FixedAmountPromoController {
    private final FixedAmountPromoService service;
    private final JwtServiceImpl jwtService;
    @GetMapping("/")
    public CompletableFuture<ResponseEntity<List<FixedAmountPromo>>> getAllFixedAmountPromo() {
        return service.findAll().thenApply(ResponseEntity::ok);
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<PromoResponse>> createFixedAmountPromo(
            @NonNull HttpServletRequest request,
            @RequestBody FixedAmountPromoRequest requestBody
    ) {
        boolean isAuthenticated = jwtService.isAuthenticated(request);

        if (!isAuthenticated) {
            PromoResponse response = PromoResponse
                    .builder()
                    .message("ADMIN Only")
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }
        return service.create(requestBody);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<ResponseEntity<PromoResponse>> updateFixedAmountPromo(
            @NonNull HttpServletRequest request,
            @PathVariable UUID id,
            @RequestBody FixedAmountPromoRequest requestBody
    ) {
        boolean isAuthenticated = jwtService.isAuthenticated(request);

        if (!isAuthenticated) {
            PromoResponse response = PromoResponse
                    .builder()
                    .message("ADMIN Only")
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }

        return service.update(id, requestBody);
    }
}
