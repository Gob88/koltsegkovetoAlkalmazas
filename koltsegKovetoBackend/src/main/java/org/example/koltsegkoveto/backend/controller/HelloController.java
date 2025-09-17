package org.example.koltsegkoveto.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Szia, ez az első Spring Boot válaszom! 🎉";
    }
}
