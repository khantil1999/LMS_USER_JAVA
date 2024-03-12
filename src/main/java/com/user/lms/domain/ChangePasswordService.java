package com.user.lms.domain;

import com.user.lms.entity.User;
import com.user.lms.models.ChangePasswordModel;
import com.user.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Service
public class ChangePasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String changePassword(@Valid @ModelAttribute("changePassword") ChangePasswordModel changePasswordModel,
                                 BindingResult bindingResult, Model model, Principal principal)
    {
        //boolean isReturnToResetPage=false;
        if (bindingResult.hasErrors())
        {
            model.addAttribute("changePassword", changePasswordModel);
            return "/changepassword";
        }
        System.out.println(principal.getName());
        if (principal != null)
        {
            String userEmail = principal.getName();
            System.out.println(userEmail);
            User user = this.userRepository.findExistingUser(userEmail);
            if (user != null) {
                System.out.println("user checking");
                if (passwordEncoder.matches(changePasswordModel.getOldPassword(), user.getPassword()))
                {
                    System.out.println("checking old password from database field");
                    if (!changePasswordModel.getOldPassword().equals(changePasswordModel.getNewPassword()))
                    {
                        System.out.println("checking old and new password different");
                        if (changePasswordModel.isPasswordAndConfirmPasswordMatch())
                        {
                            System.out.println("Checking new and confirm password are same..");
                            user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
                            userRepository.save(user);
                            model.addAttribute("successMessage", "Password changed successfully");
                        }
                        else
                        {
                            System.out.println("New and confirm password else part");
                            model.addAttribute("passwordError", "New password and confirm password do not match");
                        }
                    }
                    else
                    {
                        System.out.println("New password diffr else part");
                        model.addAttribute("passwordError", "New password should be different from the old password");
                    }
                }
                else
                {
                    System.out.println("else part for old pass checking");
                    model.addAttribute("passwordError","Old password is incorrect");

                }
            }
            else
            {
                System.out.println("else part of checking user");
                model.addAttribute("passwordError", "User not found with the provided email");
            }
            return "/changepassword";

        }
        return "login";
    }
}
