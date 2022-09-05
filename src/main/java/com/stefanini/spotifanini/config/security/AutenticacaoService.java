package com.stefanini.spotifanini.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.User;
import com.stefanini.spotifanini.repository.UserRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = repository.findByUsername(username);

		if (user.isPresent())
			return user.get();

		throw new UsernameNotFoundException("Dados inválidos!");
	}
}