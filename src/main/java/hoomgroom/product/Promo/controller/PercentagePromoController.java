package hoomgroom.product.promo.controller;

import hoomgroom.product.auth.service.JwtServiceImpl;
import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.service.PercentagePromoService;
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
@RequestMapping("/api/promo/percent")
@RequiredArgsConstructor
public class PercentagePromoController {
    private final JwtServiceImpl jwtService;
    private final PercentagePromoService service;
    @GetMapping("/")
    public CompletableFuture<ResponseEntity<List<PercentagePromo>>> getAllPercentagePromo() {
        return service.findAll().thenApply(ResponseEntity::ok);
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<PromoResponse>> createPercentagePromo(
            @NonNull HttpServletRequest request,
            @RequestBody PercentagePromoRequest requestBody
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
    public CompletableFuture<ResponseEntity<PromoResponse>> updatePercentagePromo(
        @NonNull HttpServletRequest request,
        @PathVariable UUID id,
        @RequestBody PercentagePromoRequest requestBody
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
