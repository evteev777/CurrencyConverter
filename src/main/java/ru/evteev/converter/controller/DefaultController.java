package ru.evteev.converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("metaTitle", "Главная");
        return "index";
    }
}
