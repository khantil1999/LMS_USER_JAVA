package com.user.lms.controller;

import com.user.lms.domain.UserRestService;
import com.user.lms.models.ChangePasswordModel;
import com.user.lms.models.ChangePasswordResponseModel;
import com.user.lms.models.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRestApiController {

    @Autowired
    private UserRestService userRestService;

    @GetMapping("/me")
    public UserDetailsModel getCurrentUserDetails(Principal principal)
    {
        return this.userRestService.getUserDetails(principal);
    }

    @PutMapping("/updateProfile")
    public UserDetailsModel updateProfile(@RequestBody UserDetailsModel userDetailsModel){
        return this.userRestService.updateProfile(userDetailsModel);
    }

    @PutMapping("/changePassword")
    public ChangePasswordResponseModel updateProfile(@RequestBody ChangePasswordModel changePasswordModel, Principal principal){
        return this.userRestService.changePassword(principal, changePasswordModel);
    }
}
