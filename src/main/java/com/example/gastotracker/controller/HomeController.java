package com.example.gastotracker.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");

        if (usuario != null) {
            return "redirect:/gastos";
        } else {
            return "index";
        }
    }
}

