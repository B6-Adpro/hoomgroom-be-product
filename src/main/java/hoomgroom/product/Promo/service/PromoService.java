package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.dto.RedeemResponse;
import hoomgroom.product.promo.dto.Response;
import hoomgroom.product.promo.model.Promo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public interface PromoService {
    CompletableFuture<List<Promo>> findAll();
    CompletableFuture<ResponseEntity<PromoResponse>> findById(UUID uuid);
    CompletableFuture<ResponseEntity<Response>> delete(UUID uuid) throws ExecutionException, InterruptedException;
    CompletableFuture<ResponseEntity<RedeemResponse>> redeem(UUID transactionId, UUID promoId, Long totalPrice);

    boolean isValid (Promo promo);
}
