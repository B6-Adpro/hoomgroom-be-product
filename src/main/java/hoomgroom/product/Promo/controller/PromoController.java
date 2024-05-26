package hoomgroom.product.promo.controller;

import hoomgroom.product.auth.service.JwtServiceImpl;
import hoomgroom.product.promo.dto.*;
import hoomgroom.product.promo.model.Promo;
import hoomgroom.product.promo.service.PromoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromoController {
    private final PromoService promoService;
    private final JwtServiceImpl jwtService;

    @GetMapping("/")
    public CompletableFuture<ResponseEntity<List<Promo>>> getAllPromos() {
        return promoService.findAll().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<PromoResponse>> getPromoById(@PathVariable UUID id) {
        return promoService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<ResponseEntity<Response>> deletePromo(
            @NonNull HttpServletRequest request,
            @PathVariable UUID id
    ) throws ExecutionException, InterruptedException {
        boolean isAuthenticated = jwtService.isAuthenticated(request);

        if (!isAuthenticated) {
            Response response = Response
                    .builder()
                    .message("ADMIN Only")
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }
        return promoService.delete(id);
    }

    @GetMapping("/redeem/{id}")
    public CompletableFuture<ResponseEntity<RedeemResponse>> redeemPromo(
            @NonNull HttpServletRequest request,
            @PathVariable UUID id,
            @RequestBody RedeemPromoRequest requestBody
    ) {
        boolean isAuthenticated = jwtService.isAuthenticated(request);

        if (!isAuthenticated) {
            RedeemResponse response = RedeemResponse
                    .builder()
                    .message("ADMIN Only")
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }
        return promoService.redeem(requestBody.getTransactionId(), id, requestBody.getTotalPrice());
    }
}
