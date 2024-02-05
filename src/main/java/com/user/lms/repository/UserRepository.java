package com.user.lms.repository;

import com.user.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from users where email = ? and is_verified = ?",nativeQuery = true)
	User findByEmail(String email,Boolean isVerified);

	@Query(value = "select * from users where email = ?",nativeQuery = true)
	User findExistingUser(String email);

}
