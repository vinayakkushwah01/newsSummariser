package com.newsSummeriser.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth.html")
    public String showAuthPage() {
        return "auth"; // This maps to templates/auth.html
    }
}