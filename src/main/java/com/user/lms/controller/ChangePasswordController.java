package com.user.lms.controller;

import com.user.lms.domain.ChangePasswordService;
import com.user.lms.models.ChangePasswordModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;
    @GetMapping("/changePassword")
    public String loadChangePassword(Model model){
        model.addAttribute("changePassword",new ChangePasswordModel());
        return "changepassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@Valid @ModelAttribute("changePassword") ChangePasswordModel changePasswordModel,
                                 BindingResult bindingResult, Model model, Principal principal){
        return  this.changePasswordService.changePassword(changePasswordModel,bindingResult,model,principal);
    }
}
