package ru.evteev.converter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.evteev.converter.entities.ExchangeRate;

public interface ExchangeRateRepo extends CrudRepository<ExchangeRate, Integer> {
}
