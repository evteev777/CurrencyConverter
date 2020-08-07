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
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repo.CurrencyRepo;
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

// Lombok
@RequiredArgsConstructor

@Controller

public class ExchangeController {

    private final ExchangeRepo exchangeRepo;
    private final ExchangeService exchangeService;
    private final XMLParserService xmlParserService;
    private final CurrencyRepo currencyRepo;

    @GetMapping("/converter")
    public String exchange(Model model)
            throws IOException, SAXException, ParserConfigurationException {
        // TODO Parse
        xmlParserService.getCurrenciesAndExchangeRates();

        updateModel(model);
        model.addAttribute("title", "Обмен валюты");
        return "converter";
    }

    @PostMapping("/converter")
    public String addExchange(@AuthenticationPrincipal User user,
                              @ModelAttribute Exchange exchange,
                              Model model)
            throws ParseException {

        BigDecimal result = exchangeService.convert(exchange);
        exchange.setResult(result);
        exchange.setClient(user);
        exchange.setDate(LocalDate.now());
        BigDecimal conversionRate = exchangeService.getConversionRate(exchange);
        exchange.setConversionRate(conversionRate);

        exchangeRepo.save(exchange);
        updateModel(model);
        model.addAttribute("metaTitle", "Обмен валюты");
        return "/converter";
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
