package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.Currency;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.ExchangeRate;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repo.CurrencyRepo;
import ru.evteev.converter.repo.ExchangeRateRepo;
import ru.evteev.converter.repo.ExchangeRepo;
import ru.evteev.converter.service.ExchangeService;
import ru.evteev.converter.service.XMLParserService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Lombok
@RequiredArgsConstructor

@Controller

public class ExchangeController {

    private final ExchangeRepo exchangeRepo;
    private final ExchangeService exchangeService;
    private final XMLParserService xmlParserService;
    private final CurrencyRepo currencyRepo;
    private final ExchangeRateRepo exchangeRateRepo;

    @GetMapping("/converter")
    public String exchange(Model model)
            throws IOException, SAXException, ParserConfigurationException {

        if (currencyRepo.count() == 0) {
            xmlParserService.getCurrenciesAndExchangeRates();
        }

        updateModel(model);
        model.addAttribute("title", "Обмен валюты");
        return "converter";
    }

    @PostMapping("/converter")
    public String addExchange(@AuthenticationPrincipal User user,
                              @ModelAttribute Exchange exchange,
                              Model model)
            throws ParseException, IOException, SAXException, ParserConfigurationException {

        if (exchangeRatesNotActuat(exchange)) {
            xmlParserService.getCurrenciesAndExchangeRates();
        }

        exchange.setResult(exchangeService.convert(exchange));
        exchange.setClient(user);
        exchange.setDate(LocalDate.now());
        exchange.setConversionRate(exchangeService.getConversionRate(exchange));
        exchangeRepo.save(exchange);

        updateModel(model);
        model.addAttribute("metaTitle", "Обмен валюты");

        return "/converter";
    }

    private boolean exchangeRatesNotActuat(Exchange exchange) {
        int sourceCurrencyId = exchange.getSourceCurrency().getId();
        ExchangeRate sourceExchRate = exchangeRateRepo.findByCurrencyId(sourceCurrencyId);
        boolean sourseExchRateActual = sourceExchRate.getDate().isEqual(LocalDate.now());

        int targetCurrencyId = exchange.getSourceCurrency().getId();
        ExchangeRate targetExchRate = exchangeRateRepo.findByCurrencyId(targetCurrencyId);
        boolean targetExchRateActual = targetExchRate.getDate().isEqual(LocalDate.now());

        return sourseExchRateActual && targetExchRateActual;
    }

    private void updateModel(Model model) {
        List<Currency> currencies = new ArrayList<>();
        currencyRepo.findAll().forEach(currencies::add);
        model.addAttribute("currencies", currencies);

        List<Exchange> exchanges = new ArrayList<>();
        exchangeRepo.findAll().forEach(exchanges::add);
        model.addAttribute("exchanges", exchanges);
    }
}
