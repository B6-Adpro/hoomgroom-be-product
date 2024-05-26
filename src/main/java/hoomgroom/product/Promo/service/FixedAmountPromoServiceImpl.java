package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.FixedAmountPromo;
import hoomgroom.product.promo.model.factory.FixedAmountPromoFactory;
import hoomgroom.product.promo.repository.FixedAmountPromoRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FixedAmountPromoServiceImpl implements FixedAmountPromoService{
    private final FixedAmountPromoRepository fixedAmountPromoRepository;

    @Override
    public ResponseEntity<List<FixedAmountPromo>> findAll() {
        return ResponseEntity.ok(fixedAmountPromoRepository.findAll());
    }

    @Override
    public ResponseEntity<PromoResponse> create(FixedAmountPromoRequest request) {
        FixedAmountPromoFactory promoFactory = new FixedAmountPromoFactory();
        FixedAmountPromo promo = promoFactory.createPromo();
        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setMinimumPurchase(request.getMinimumPurchase());
        promo.setDiscountAmount(request.getDiscountAmount());
        promo.setExpirationDate(request.getExpirationDate());
        fixedAmountPromoRepository.save(promo);

        if (isValid(promo)) {
            try {
                fixedAmountPromoRepository.save(promo);
            } catch (ConstraintViolationException e) {
                PromoResponse response = PromoResponse
                        .builder()
                        .message("Fixed Promo name can't be empty!")
                        .promo(promo)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            PromoResponse response = PromoResponse
                    .builder()
                    .message("Create Fixed Promo Success")
                    .promo(promo)
                    .build();
            return ResponseEntity.ok(response);
        }

        PromoResponse response = PromoResponse
                .builder()
                .message("Create Fixed Promo Failed! Invalid field(s)")
                .promo(promo)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<PromoResponse> update(UUID id, FixedAmountPromoRequest request) {
        Optional<FixedAmountPromo> promo = fixedAmountPromoRepository.findById(id);

        if (promo.isEmpty()) {
            PromoResponse response = PromoResponse.builder()
                    .message("Fixed Promo not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        FixedAmountPromo fixedPromo = promo.get();

        fixedPromo.setName(request.getName());
        fixedPromo.setDescription(request.getDescription());
        fixedPromo.setMinimumPurchase(request.getMinimumPurchase());
        fixedPromo.setDiscountAmount(request.getDiscountAmount());
        fixedPromo.setExpirationDate(request.getExpirationDate());

        if (isValid(fixedPromo)){
            try {
                fixedAmountPromoRepository.save(fixedPromo);
            } catch (ConstraintViolationException e) {
                PromoResponse response = PromoResponse
                        .builder()
                        .message("Fixed Promo name can't be empty!")
                        .promo(fixedPromo)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            PromoResponse response = PromoResponse
                    .builder()
                    .message("Fixed Promo updated successfully!")
                    .promo(fixedPromo)
                    .build();

            return ResponseEntity.ok(response);
        }

        PromoResponse response = PromoResponse
                .builder()
                .message("Fixed Promo update failed! Invalid field(s)!")
                .promo(fixedPromo)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public boolean isValid(FixedAmountPromo promo) {
        return isNotExpired(promo) && isNotNegativeMinPurchase(promo) && isNotNegativeDiscount(promo);
    }

    @Override
    public boolean isNotExpired(FixedAmountPromo promo) {
        return promo.getExpirationDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isNotNegativeMinPurchase(FixedAmountPromo promo) {
        return promo.getMinimumPurchase() >= 0;
    }

    @Override
    public boolean isNotNegativeDiscount(FixedAmountPromo promo) {
        return promo.getDiscountAmount() >= 0;
    }
}
