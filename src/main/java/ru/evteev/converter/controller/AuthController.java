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
@RequestMapping("/registration")
public class AuthController {

    private final UserRepo userRepo;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("title", "Регистрация");
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "Такой пользователь уже есть");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        // TODO Auto-login
        model.addAttribute("metaTitle", "Регистрация");
        return "redirect:/converter";
    }
}
