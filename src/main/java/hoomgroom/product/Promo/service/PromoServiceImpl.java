package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.dto.RedeemResponse;
import hoomgroom.product.promo.dto.Response;
import hoomgroom.product.promo.model.Promo;
import hoomgroom.product.promo.repository.PromoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {
    private static final String PROMO_NOT_FOUND_MESSAGE = "Promo id %s not found!";
    private final PromoRepository promoRepository;

    @Async("promoTaskExecutor")
    public CompletableFuture<List<Promo>> findAll() {
        return CompletableFuture.supplyAsync(promoRepository::findAll);
    }

    @Async("promoTaskExecutor")
    public CompletableFuture<ResponseEntity<PromoResponse>> findById(@NonNull UUID id) throws NoSuchElementException {
        Optional<Promo> promo = promoRepository.findById(id);
        if (promo.isEmpty()) {
            PromoResponse response = PromoResponse.builder()
                    .message(String.format(PROMO_NOT_FOUND_MESSAGE, id))
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
        }

        PromoResponse response = PromoResponse
                .builder()
                .message("Success")
                .promo(promo.get())
                .build();

        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    @Async("promoTaskExecutor")
    public CompletableFuture<ResponseEntity<Response>> delete(UUID id){
        Optional<Promo> promo = promoRepository.findById(id);

        if (promo.isEmpty()) {
            Response response = Response.builder()
                    .message(String.format(PROMO_NOT_FOUND_MESSAGE, id))
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
        }

        promoRepository.delete(promo.get());
        Response response = Response.builder()
                .message(String.format("Promo id %s deleted successfully", promo.get().getId().toString()))
                .build();
        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }

    public boolean isValid (Promo promo) {
        return promo.getExpirationDate().isBefore(LocalDateTime.now());
    }

    @Async("promoTaskExecutor")
    public CompletableFuture<ResponseEntity<RedeemResponse>> redeem(UUID transactionId, UUID promoId, Long totalPrice) {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if (promo.isEmpty()) {
            RedeemResponse response = RedeemResponse.builder()
                    .message(String.format(PROMO_NOT_FOUND_MESSAGE, promoId))
                    .transactionId(transactionId)
                    .promoId(promoId)
                    .normalPrice(totalPrice)
                    .discountPrice(totalPrice)
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
        } else if (!isValid(promo.get())) {
            RedeemResponse response = RedeemResponse.builder()
                    .message(String.format("Promo id %s has expired!", promoId))
                    .transactionId(transactionId)
                    .promoId(promoId)
                    .normalPrice(totalPrice)
                    .discountPrice(totalPrice)
                    .build();
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
        }

        Long discountPrice =  promo.get().applyPromo(totalPrice);

        RedeemResponse response = RedeemResponse.builder()
                .message(String.format("Success redeem promo %s", promoId))
                .transactionId(transactionId)
                .promoId(promoId)
                .normalPrice(totalPrice)
                .discountPrice(discountPrice)
                .build();

        return CompletableFuture.completedFuture(ResponseEntity.ok(response));
    }
}
