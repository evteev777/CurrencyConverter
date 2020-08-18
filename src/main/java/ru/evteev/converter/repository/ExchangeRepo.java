package ru.evteev.converter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evteev.converter.entity.Exchange;

public interface ExchangeRepo extends CrudRepository<Exchange, Long> {

}
