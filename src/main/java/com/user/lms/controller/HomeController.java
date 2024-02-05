package com.user.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/about")
    public String loadAboutPage() {
        return "about";
    }

    @GetMapping("/home")
    public String loadHomePage() {
        return "home";
    }
    @GetMapping("/service")
    public String loadServicePage() {
        return "service";
    }

    @GetMapping("/contact")
    public String loadContactPage() {
        return "contact";
    }

}
