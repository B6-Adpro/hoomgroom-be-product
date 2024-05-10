package hoomgroom.product.Promo.service;

import hoomgroom.product.Promo.model.Promo;
import hoomgroom.product.Promo.repository.PromoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {
    private final PromoRepository promoRepository;

    @Override
    public List<Promo> findAll() {
        return promoRepository.findAll();
    }

    @Override
    public Promo findById(@NonNull UUID id) throws NoSuchElementException {
        Optional<Promo> promo = promoRepository.findById(id);
        if (promo.isEmpty()){
            throw new NoSuchElementException();
        }
        return promo.get();
    }
}
