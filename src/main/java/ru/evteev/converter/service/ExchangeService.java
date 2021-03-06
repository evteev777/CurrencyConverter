package ru.evteev.converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.Currency;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.ExchangeRate;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repository.CurrencyRepo;
import ru.evteev.converter.repository.ExchangeRateRepo;
import ru.evteev.converter.repository.ExchangeRepo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Lombok
@RequiredArgsConstructor

@Service
public class ExchangeService {

    private final CurrencyRepo currencyRepo;
    private final ExchangeRateRepo exchangeRateRepo;
    private final ExchangeRepo exchangeRepo;
    private final XMLParserService xmlParserService;

    public void checkExchangeRatesUpToDate(Exchange exch)
            throws ParserConfigurationException, SAXException, IOException {

        Currency sourceC = exch.getSourceCurrency();
        Currency targetC = exch.getTargetCurrency();

        if (sourceC == null || targetC == null) {
            xmlParserService.getCurrenciesAndExchangeRates();
        } else {
            ExchangeRate sourceER = exchangeRateRepo.findByCurrencyId(sourceC.getId());
            boolean sourceUpToDate = sourceER.getDate().isEqual(LocalDate.now());

            ExchangeRate targetER = exchangeRateRepo.findByCurrencyId(targetC.getId());
            boolean targetUpToDate = targetER.getDate().isEqual(LocalDate.now());

            if (!sourceUpToDate || !targetUpToDate) {
                xmlParserService.getCurrenciesAndExchangeRates();
            }
        }
    }

    public List<Currency> getCurrencies()
            throws IOException, SAXException, ParserConfigurationException {

        if (currencyRepo.count() == 0) {
            xmlParserService.getCurrenciesAndExchangeRates();
        }
        List<Currency> currencies = new ArrayList<>();
        currencyRepo.findAll().forEach(currencies::add);
        return currencies;
    }

    public List<Exchange> getExchanges() {
        List<Exchange> exchanges = new ArrayList<>();
        exchangeRepo.findAll().forEach(exchanges::add);
        return exchanges;
    }

    public void setExchangeParams(Exchange exch, User user) throws ParseException {
        exch.setConversionRate(getThisConversionExchRate(exch));
        exch.setResult(convert(exch));
        exch.setUser(user);
        exch.setDate(LocalDate.now());
        exchangeRepo.save(exch);
    }

    public BigDecimal getThisConversionExchRate(Exchange exch) {
        int sourceId = exch.getSourceCurrency().getId();
        BigDecimal sourceExchRate = BigDecimal.valueOf(exchangeRateRepo
                .findByCurrencyId(sourceId)
                .getValue());

        int targetId = exch.getTargetCurrency().getId();
        BigDecimal targetExchRate = BigDecimal.valueOf(exchangeRateRepo
                .findByCurrencyId(targetId)
                .getValue());

        return sourceExchRate.divide(targetExchRate, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal convert(Exchange exch) throws ParseException {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###.####", dfs);
        df.setParseBigDecimal(true);

        BigDecimal sourceAmount = (BigDecimal) df.parse(exch.getAmount());
        BigDecimal conversionRate = getThisConversionExchRate(exch);
        return sourceAmount.multiply(conversionRate)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
