package ru.bober.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bober.currencyconverter.entity.ValutesCurrency;

import javax.persistence.criteria.CriteriaBuilder;
@Repository
public interface CurrencyRepository extends JpaRepository<ValutesCurrency, Integer> {
    ValutesCurrency findByValutename(String valutename);
}
