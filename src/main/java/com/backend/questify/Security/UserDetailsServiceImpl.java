package com.backend.questify.Security;

import com.backend.questify.Entity.User;
import com.backend.questify.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Long id;
		try {
			id = Long.parseLong(userId); // แปลง String เป็น Long
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("Invalid user ID format : " + userId);
		}

		User user = userRepository.findById(id)
								  .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		return new org.springframework.security.core.userdetails.User(userId, "", grantedAuthorities);
	}
}