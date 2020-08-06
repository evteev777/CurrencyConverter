package ru.evteev.converter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.evteev.converter.entity.Role;
import ru.evteev.converter.entity.User;
import ru.evteev.converter.repo.UserRepo;

import java.util.Collections;
import java.util.Map;

// Lombok
@RequiredArgsConstructor

@Controller
public class AuthController {

    private final UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            model.put("message", "Такой пользователь уже есть");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        // TODO Auto-login
        return "redirect:/converter";
    }
}
