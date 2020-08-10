package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.evteev.converter.entity.Role;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repo.UserRepo;

import java.util.Collections;

// Lombok
@RequiredArgsConstructor

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("metaTitle", "Вход");
        return "login";
    }

    @PostMapping
    public String addUser(Model model) {
        // TODO Auto-login
        model.addAttribute("metaTitle", "Вход");
        return "redirect:/converter";
    }
}
