package com.user.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageProfileController {

    @GetMapping("/manageProfile")
    public String loadManageProfilePage(){
        return "profile";
    }
}
