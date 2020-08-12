package ru.evteev.converter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.Currency;
import ru.evteev.converter.entity.ExchangeRate;
import ru.evteev.converter.parser.XMLParser;
import ru.evteev.converter.parser.XMLParserDOM;
import ru.evteev.converter.repo.CurrencyRepo;
import ru.evteev.converter.repo.ExchangeRateRepo;

// Lombok
@Getter
@Setter
@RequiredArgsConstructor

@Service
public class XMLParserService {

    private final ExchangeRateRepo exchangeRateRepo;
    private final CurrencyRepo currencyRepo;

    @Value("${url.cbr}")
    private String url;

    public void getCurrenciesAndExchangeRates()
        throws ParserConfigurationException, SAXException, IOException {

        XMLParser parser = new XMLParserDOM();
        List<ExchangeRate> exchangeRates = parser.parse(url);

        List<Currency> currencies = new ArrayList<>();
        exchangeRates.forEach(rate -> currencies.add(rate.getCurrency()));

        currencyRepo.saveAll(currencies);
        exchangeRateRepo.saveAll(exchangeRates);
    }
}
