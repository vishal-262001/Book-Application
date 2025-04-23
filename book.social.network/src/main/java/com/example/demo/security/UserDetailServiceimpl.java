package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserDetailServiceimpl implements UserDetailsService {

	private final UserRepository repository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return repository.findByEmail(userEmail)
				.orElseThrow(()-> new UsernameNotFoundException("User Not  Found"));
	}

}
