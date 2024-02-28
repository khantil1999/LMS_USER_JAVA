package com.user.lms.models;

import lombok.Data;

@Data
public class ChangePasswordModel {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
