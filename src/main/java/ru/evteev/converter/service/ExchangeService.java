package ru.evteev.converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.evteev.converter.entity.Currency;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.ExchangeRate;
import ru.evteev.converter.repo.ExchangeRateRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

// Lombok
@RequiredArgsConstructor

@Service
public class ExchangeService {

    private final ExchangeRateRepo exchangeRateRepo;

    public BigDecimal convert(Exchange exchange) throws ParseException {
        return getAmount(exchange)
                .multiply(getValue(exchange.getSourceCurrency()))
                .divide((getValue(exchange.getTargetCurrency())), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAmount(Exchange exchange) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###.####", symbols);
        decimalFormat.setParseBigDecimal(true);
        return (BigDecimal) decimalFormat.parse(exchange.getAmount());
    }

    private BigDecimal getValue(Currency currency) {
        ExchangeRate rate = exchangeRateRepo.findByCurrencyId(currency.getId());
        return BigDecimal.valueOf(rate.getValue() / currency.getNominal());
    }

}
