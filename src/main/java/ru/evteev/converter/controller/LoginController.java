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
        addTitle(model);
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(Model model) {
        addTitle(model);
        return "redirect:exchange";
    }

    @PostMapping(value = "/logout")
    public String logout(Model model) {
        addTitle(model);
        return "redirect:index";
    }

    private void addTitle(Model model) {
        model.addAttribute("metaTitle", title);
    }
}
