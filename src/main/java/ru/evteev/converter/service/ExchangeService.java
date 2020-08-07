package ru.evteev.converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.ExchangeRate;
import ru.evteev.converter.repo.ExchangeRateRepo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;

// Lombok
@RequiredArgsConstructor

@Service
public class ExchangeService {

    private final ExchangeRateRepo exchangeRateRepo;


    public boolean exchangeRatesNotActual(Exchange exchange) {
        int sourceCurrencyId = exchange.getSourceCurrency().getId();
        ExchangeRate sourceExchRate = exchangeRateRepo.findByCurrencyId(sourceCurrencyId);
        boolean sourseExchRateActual = sourceExchRate.getDate().isEqual(LocalDate.now());

        int targetCurrencyId = exchange.getSourceCurrency().getId();
        ExchangeRate targetExchRate = exchangeRateRepo.findByCurrencyId(targetCurrencyId);
        boolean targetExchRateActual = targetExchRate.getDate().isEqual(LocalDate.now());

        return !sourseExchRateActual || !targetExchRateActual;
    }

    public BigDecimal convert(Exchange exchange) throws ParseException {
        return getAmount(exchange)
                .multiply(getConversionRate(exchange));
    }

    private BigDecimal getAmount(Exchange exchange) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("###.####", symbols);
        decimalFormat.setParseBigDecimal(true);
        return (BigDecimal) decimalFormat.parse(exchange.getAmount());
    }

    public BigDecimal getConversionRate(Exchange exchange) {
        int sourseCurrencyId = exchange.getSourceCurrency().getId();
        double sourseExchangeRate = exchangeRateRepo
                .findByCurrencyId(sourseCurrencyId)
                .getValue();

        int targetCurrencyId = exchange.getTargetCurrency().getId();
        double targetExchangeRate = exchangeRateRepo
                .findByCurrencyId(targetCurrencyId)
                .getValue();

        return BigDecimal.valueOf(sourseExchangeRate / targetExchangeRate);
    }
}
