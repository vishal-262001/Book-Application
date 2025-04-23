package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.jws.soap.SOAPBinding.Use;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

}
