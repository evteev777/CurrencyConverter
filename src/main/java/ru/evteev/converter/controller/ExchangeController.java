package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repo.ExchangeRepo;
import ru.evteev.converter.service.ExchangeService;
import ru.evteev.converter.service.XMLParserService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

// Lombok
@RequiredArgsConstructor

@Controller
public class ExchangeController {

    private final ExchangeRepo exchangeRepo;
    private final XMLParserService xmlParserService;
    private final ExchangeService exchangeService;

    @GetMapping("/converter")
    public String exchange(Map<String, Object> model)
            throws IOException, SAXException, ParserConfigurationException {
        // TODO Parse
        xmlParserService.getCurrenciesAndExchangeRates();
        return "converter";
    }

    @PostMapping("/converter")
    public String addExchange(@AuthenticationPrincipal User user,
            Exchange exchange, Map<String, Object> model)
            throws ParseException {

        BigDecimal result = exchangeService.convert(exchange);
        exchange.setResult(result);
        exchange.setClient(user);
        exchange.setDate(LocalDate.now());

        exchangeRepo.save(exchange);
        return "/converter";
    }
}
