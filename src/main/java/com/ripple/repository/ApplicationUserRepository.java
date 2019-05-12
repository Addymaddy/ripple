package com.ripple.repository;

import com.ripple.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    List<ApplicationUser> findByEmailId(String emailId);
    List<ApplicationUser> findByMobileno(String mobileno);
}