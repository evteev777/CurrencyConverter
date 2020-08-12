package ru.evteev.converter.repo;

import org.springframework.data.repository.CrudRepository;
import ru.evteev.converter.entity.Currency;

public interface CurrencyRepo extends CrudRepository<Currency, Integer> {

}
