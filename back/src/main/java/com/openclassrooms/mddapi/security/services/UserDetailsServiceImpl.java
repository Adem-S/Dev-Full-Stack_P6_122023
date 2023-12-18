package com.openclassrooms.mddapi.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {

		User user = userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with email or username: " + emailOrUsername));

		return UserDetailsImpl.build(user);
	}

}
