package ru.evteev.converter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class DefaultController {

    @Autowired
    public DefaultController() {
    }

    @GetMapping("/")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "дружище")
                    String name, Map<String, Object> model) {
        model.put("name", name);
        return "index";
    }
}
