package ru.evteev.converter.service;

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
import ru.evteev.converter.parser.XMLParserSAX;
import ru.evteev.converter.repository.CurrencyRepo;
import ru.evteev.converter.repository.ExchangeRateRepo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// Lombok
@Getter
@Setter
@RequiredArgsConstructor

@Service
public class XMLParserService {

    private final ExchangeRateRepo exchangeRateRepo;
    private final CurrencyRepo currencyRepo;

    @Value("${parse.url}")
    private String url;

    @Value("${parse.method}")
    private String parseMethod;

    private XMLParser parser;

    public void getCurrenciesAndExchangeRates()
            throws ParserConfigurationException, SAXException, IOException {

        if (parseMethod.equalsIgnoreCase("DOM")) {
            parser = new XMLParserDOM();
        } else
        if (parseMethod.equalsIgnoreCase("SAX")) {
            parser = new XMLParserSAX();
        } else {
            throw new ParserConfigurationException(
                    "Set parse method in application.yaml: SAX or DOM");
        }

        // TODO wait-notify
        List<ExchangeRate> exchangeRates = parser.parse(url);

        Set<Currency> currencies = new TreeSet<>(Comparator.comparing(Currency::getCharCode));
        exchangeRates.forEach(rate -> currencies.add(rate.getCurrency()));

        currencyRepo.saveAll(currencies);
        exchangeRateRepo.saveAll(exchangeRates);
    }
}
