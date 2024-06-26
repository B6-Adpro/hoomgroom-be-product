package hoomgroom.product.promo.service;

import hoomgroom.product.promo.dto.PercentagePromoRequest;
import hoomgroom.product.promo.dto.PromoResponse;
import hoomgroom.product.promo.model.PercentagePromo;
import hoomgroom.product.promo.model.factory.PercentagePromoFactory;
import hoomgroom.product.promo.repository.PercentagePromoRepository;
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
public class PercentagePromoServiceImpl implements PercentagePromoService{
    private final PercentagePromoRepository percentagePromoRepository;

    @Override
    public ResponseEntity<List<PercentagePromo>> findAll() {
        return ResponseEntity.ok(percentagePromoRepository.findAll());
    }

    @Override
    public ResponseEntity<PromoResponse> create(PercentagePromoRequest request) {
        PercentagePromoFactory promoFactory = new PercentagePromoFactory();
        PercentagePromo promo = promoFactory.createPromo();
        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setMinimumPurchase(request.getMinimumPurchase());
        promo.setPercentage(request.getPercentage());
        promo.setExpirationDate(request.getExpirationDate());
        percentagePromoRepository.save(promo);

        if (isValid(promo)) {
            try {
                percentagePromoRepository.save(promo);
            } catch (ConstraintViolationException e) {
                PromoResponse response = PromoResponse
                        .builder()
                        .message("Percent Promo name can't be empty!")
                        .promo(promo)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            PromoResponse response = PromoResponse
                    .builder()
                    .message("Percent Fixed Promo Success")
                    .promo(promo)
                    .build();
            return ResponseEntity.ok(response);
        }

        PromoResponse response = PromoResponse
                .builder()
                .message("Create Percent Promo Failed! Invalid field(s)")
                .promo(promo)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<PromoResponse> update(UUID id, PercentagePromoRequest request) {
        Optional<PercentagePromo> promo = percentagePromoRepository.findById(id);

        if (promo.isEmpty()) {
            PromoResponse response = PromoResponse.builder()
                    .message("Percent Promo not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        PercentagePromo percentagePromo = promo.get();

        percentagePromo.setName(request.getName());
        percentagePromo.setDescription(request.getDescription());
        percentagePromo.setMinimumPurchase(request.getMinimumPurchase());
        percentagePromo.setPercentage(request.getPercentage());
        percentagePromo.setExpirationDate(request.getExpirationDate());

        if (isValid(percentagePromo)){
            try {
                percentagePromoRepository.save(percentagePromo);
            } catch (ConstraintViolationException e) {
                PromoResponse response = PromoResponse
                        .builder()
                        .message("Percent Promo name can't be empty!")
                        .promo(percentagePromo)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            PromoResponse response = PromoResponse
                    .builder()
                    .message("Percent Promo updated successfully!")
                    .promo(percentagePromo)
                    .build();

            return ResponseEntity.ok(response);
        }

        PromoResponse response = PromoResponse
                .builder()
                .message("Percent Promo update failed! Invalid field(s)!")
                .promo(percentagePromo)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public boolean isValid(PercentagePromo promo) {
        return isNotExpired(promo) && isNotNegativeMinPurchase(promo) && isNotNegativeDiscount(promo);
    }

    @Override
    public boolean isNotExpired(PercentagePromo promo) {
        return promo.getExpirationDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isNotNegativeMinPurchase(PercentagePromo promo) {
        return promo.getMinimumPurchase() >= 0;
    }

    @Override
    public boolean isNotNegativeDiscount(PercentagePromo promo) {
        return promo.getPercentage() >= 0;
    }
}
