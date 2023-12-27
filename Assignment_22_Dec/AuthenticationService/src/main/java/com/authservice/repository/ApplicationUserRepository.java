package com.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authservice.entity.ApplicationUser;

@Repository
public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String> {

}
