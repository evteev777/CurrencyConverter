package ru.evteev.converter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.evteev.converter.entities.Currency;

public interface CurrencyRepo extends CrudRepository<Currency, Integer> {
}
