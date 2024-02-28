package com.user.lms.models;

import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
public class PhotoModel {

    private Long id;

    private String photoUrl;
}
