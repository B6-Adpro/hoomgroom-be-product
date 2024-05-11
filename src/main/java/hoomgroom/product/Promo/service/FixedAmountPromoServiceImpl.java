package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.dto.FixedAmountPromoRequest;
import hoomgroom.product.Promo.model.Factory.FixedAmountPromoFactory;
import hoomgroom.product.Promo.model.FixedAmountPromo;
import hoomgroom.product.Promo.repository.FixedAmountPromoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FixedAmountPromoServiceImpl implements FixedAmountPromoService{
    private final FixedAmountPromoRepository fixedAmountPromoRepository;

    @Override
    public List<FixedAmountPromo> findAll() {
        return fixedAmountPromoRepository.findAll();
    }

    @Override
    public FixedAmountPromo findById(@NonNull UUID id) throws NoSuchElementException {
        Optional<FixedAmountPromo> promo = fixedAmountPromoRepository.findById(id);
        if (promo.isEmpty()){
            throw new NoSuchElementException();
        }
        return promo.get();
    }

    @Override
    public FixedAmountPromo create(FixedAmountPromoRequest request) {
        FixedAmountPromoFactory promoFactory = new FixedAmountPromoFactory();
        FixedAmountPromo promo = promoFactory.createPromo();
        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setMinimumPurchase(request.getMinimumPurchase());
        promo.setDiscountAmount(request.getDiscountAmount());
        promo.setExpirationDate(request.getExpirationDate());
        fixedAmountPromoRepository.save(promo);
        return promo;
    }

    @Override
    public FixedAmountPromo update(UUID id, FixedAmountPromoRequest request) {
        Optional<FixedAmountPromo> promo = fixedAmountPromoRepository.findById(id);

        if (promo.isEmpty()) {
            throw new NoSuchElementException();
        }

        promo.get().setName(request.getName());
        promo.get().setDescription(request.getDescription());
        promo.get().setMinimumPurchase(request.getMinimumPurchase());
        promo.get().setDiscountAmount(request.getDiscountAmount());
        promo.get().setExpirationDate(request.getExpirationDate());
        return fixedAmountPromoRepository.save(promo.get());
    }

    @Override
    public void delete(UUID id) {
        Optional<FixedAmountPromo> promo = fixedAmountPromoRepository.findById(id);

        if (promo.isEmpty()) {
            throw new NoSuchElementException();
        }

        fixedAmountPromoRepository.delete(promo.get());
    }
}
