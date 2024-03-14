package com.user.lms.domain;

import com.user.lms.entity.User;
import com.user.lms.models.ChangePasswordModel;
import com.user.lms.models.ChangePasswordResponseModel;
import com.user.lms.models.UserDetailsModel;
import com.user.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserRestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDetailsModel getUserDetails(Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        return UserDetailsModel.fromEntity(user);
    }

    public UserDetailsModel updateProfile(UserDetailsModel userDetailsModel) {
        Optional<User> optionalUser = this.userRepository.findById(userDetailsModel.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setMobileNo(userDetailsModel.getMobileNo());
            user.setFirstName(userDetailsModel.getFirstName());
            user.setLastName(userDetailsModel.getLastName());
            user = this.userRepository.saveAndFlush(user);
            return UserDetailsModel.fromEntity(user);
        }
        return userDetailsModel;
    }

    public ChangePasswordResponseModel changePassword(Principal principal, ChangePasswordModel changePasswordModel) {
        User user = this.userRepository.findExistingUser(principal.getName());
        ChangePasswordResponseModel changePasswordResponseModel = new ChangePasswordResponseModel();
        if (user != null) {
            if (passwordEncoder.matches(changePasswordModel.getOldPassword(), user.getPassword())) {

                if (!changePasswordModel.getOldPassword().equals(changePasswordModel.getNewPassword())) {
                    user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
                    userRepository.save(user);
                } else {
                    changePasswordResponseModel.setIsError(true);
                    changePasswordResponseModel.setErrorMessage("New password should be different from the old password ");
                    changePasswordResponseModel.setIsOldError(false);
                    return changePasswordResponseModel;
                }
            } else {
                changePasswordResponseModel.setIsError(true);
                changePasswordResponseModel.setErrorMessage("Old password is incorrect");
                changePasswordResponseModel.setIsOldError(true);
                return changePasswordResponseModel;
            }
        }
        return changePasswordResponseModel;
    }
}
