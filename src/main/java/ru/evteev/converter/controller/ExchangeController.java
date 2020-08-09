package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.Exchange;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.service.ExchangeService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

// Lombok
@RequiredArgsConstructor

@Controller

public class ExchangeController {

    private final ExchangeService exchangeService;


    @GetMapping("/converter")
    public String exchange(Model model) throws IOException, SAXException, ParserConfigurationException {

        model.addAttribute("currencies", exchangeService.getCurrencies());
        model.addAttribute("title", "Обмен валюты");
        return "converter";
    }

    @PostMapping("/converter")
    public String addExchange(@AuthenticationPrincipal User user,
                              @ModelAttribute Exchange exchange,
                              Model model)
            throws ParseException, IOException, SAXException, ParserConfigurationException {

        exchangeService.checkExchangeRatesUpToDate(exchange);
        exchangeService.setExchangeParams(exchange, user);

        model.addAttribute("currencies", exchangeService.getCurrencies());
        model.addAttribute("exchanges", exchangeService.getExchanges());
        model.addAttribute("metaTitle", "Обмен валюты");
        return "/converter";
    }
}
