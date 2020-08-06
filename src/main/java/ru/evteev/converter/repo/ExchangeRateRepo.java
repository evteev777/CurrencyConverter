package ru.evteev.converter.repo;

import org.springframework.data.repository.CrudRepository;
import ru.evteev.converter.entity.ExchangeRate;

public interface ExchangeRateRepo extends CrudRepository<ExchangeRate, Integer> {
    ExchangeRate findByCurrencyId(int id);
}
