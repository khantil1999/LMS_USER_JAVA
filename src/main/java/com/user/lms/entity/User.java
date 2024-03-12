package com.user.lms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "password")
	private String password;

	@Column(name = "is_verified")
	private Boolean isVerified;

	@Column(name= "status")
	private String status;

	@Column(name = "is_approved")
	private Boolean isApproved = true;

	@OneToMany(mappedBy = "user")
	private List<UserRoles> userRoles = new ArrayList<>();

	@OneToMany(mappedBy = "truckProvider")
	private List<TruckProviderArea> truckProviderAreas = new ArrayList<>();


	@OneToOne(mappedBy = "truckProvider",fetch=FetchType.EAGER)
	private QrCode qrCode;
	
}
