package ru.evteev.converter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xml.sax.SAXException;
import ru.evteev.converter.entities.Currency;
import ru.evteev.converter.entities.ExchangeRate;
import ru.evteev.converter.models.XMLParser;
import ru.evteev.converter.models.XMLParserDOM;
import ru.evteev.converter.repos.CurrencyRepo;
import ru.evteev.converter.repos.ExchangeRateRepo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ConverterController {

    private final ExchangeRateRepo exchangeRateRepo;
    private final CurrencyRepo currencyRepo;

    @Autowired
    public ConverterController(ExchangeRateRepo exchangeRateRepo,
                               CurrencyRepo currencyRepo) {
        this.exchangeRateRepo = exchangeRateRepo;
        this.currencyRepo = currencyRepo;
    }

    @GetMapping("/converter")
    public String main(Map<String, Object> model)
            throws IOException, SAXException, ParserConfigurationException {
        final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";

        XMLParser parser = new XMLParserDOM();
        List<ExchangeRate> exchangeRates = parser.parse(URL);

        List<Currency> currencies = new ArrayList<>();
        exchangeRates.forEach(rate -> currencies.add(rate.getCurrency()));

        currencyRepo.saveAll(currencies);
        exchangeRateRepo.saveAll(exchangeRates);

        return "converter";
    }
}
