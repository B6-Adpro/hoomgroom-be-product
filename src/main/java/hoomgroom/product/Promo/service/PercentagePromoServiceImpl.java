package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.dto.PercentagePromoRequest;
import hoomgroom.product.Promo.model.Factory.PercentagePromoFactory;
import hoomgroom.product.Promo.model.PercentagePromo;
import hoomgroom.product.Promo.repository.PercentagePromoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PercentagePromoServiceImpl implements PercentagePromoService{
    private final PercentagePromoRepository percentagePromoRepository;

    @Override
    public List<PercentagePromo> findAll() {
        return percentagePromoRepository.findAll();
    }

    @Override
    public PercentagePromo findById(@NonNull UUID id) throws NoSuchElementException {
        Optional<PercentagePromo> promo = percentagePromoRepository.findById(id);
        if (promo.isEmpty()){
            throw new NoSuchElementException();
        }
        return promo.get();
    }

    @Override
    public PercentagePromo create(PercentagePromoRequest request) {
        PercentagePromoFactory promoFactory = new PercentagePromoFactory();
        PercentagePromo promo = promoFactory.createPromo();
        promo.setName(request.getName());
        promo.setDescription(request.getDescription());
        promo.setMinimumPurchase(request.getMinimumPurchase());
        promo.setPercentage(request.getPercentage());
        promo.setExpirationDate(request.getExpirationDate());
        percentagePromoRepository.save(promo);
        return promo;
    }

    @Override
    public PercentagePromo update(UUID id, PercentagePromoRequest request) {
        Optional<PercentagePromo> promo = percentagePromoRepository.findById(id);

        if (promo.isEmpty()) {
            throw new NoSuchElementException();
        }

        promo.get().setName(request.getName());
        promo.get().setDescription(request.getDescription());
        promo.get().setMinimumPurchase(request.getMinimumPurchase());
        promo.get().setPercentage(request.getPercentage());
        promo.get().setExpirationDate(request.getExpirationDate());
        return percentagePromoRepository.save(promo.get());
    }

    @Override
    public void delete(UUID id) {
        Optional<PercentagePromo> promo = percentagePromoRepository.findById(id);

        if (promo.isEmpty()) {
            throw new NoSuchElementException();
        }

        percentagePromoRepository.delete(promo.get());
    }
}
