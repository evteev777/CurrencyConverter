package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// Lombok
@RequiredArgsConstructor

@Controller

public class LoginController {

    String title = "Вход";

    @GetMapping("/login")
    public String auth(Model model) {
        model.addAttribute("metaTitle", title);
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("metaTitle", title);
        return "redirect:exchange";
    }
}
