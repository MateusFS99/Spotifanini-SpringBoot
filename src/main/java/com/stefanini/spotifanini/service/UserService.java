package com.stefanini.spotifanini.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.User;
import com.stefanini.spotifanini.repository.UserRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAllUsers(Pageable pagination) {

        try {
            return userRepository.findAll(pagination);
        } catch (Exception e) {
            throw e;
        }
    }

    public User findById(Long id) {

        try {

            Optional<User> user = userRepository.findById(id);

            Validations.notPresent(user, "User Not Found");

            return user.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> save(User user) {

        try {

            Validations.notExists(user.getUsername(), "Empty Username");
            Validations.notExists(user.getPassword(), "Empty Password");
            Validations.isPresent(userRepository.findByUsername(user.getUsername()), "User Already Exists");

            userRepository.save(user);

            return new ResponseEntity<String>("User Saved", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Empty"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> update(Long id, User user, String authorization) {

        try {

            Optional<User> dbUser = userRepository.findByUsername(user.getUsername());

            Validations.notExists(user.getUsername(), "Empty Username");
            Validations.notExists(user.getPassword(), "Empty Password");
            if (dbUser.isPresent() && dbUser.get().getId() != id)
                Validations.isPresent(dbUser, "User Already Exists");
            else
                Validations.notOwner(dbUser.get(), authorization, "Unauthorized");

            user.setId(id);
            user.setPassword(encryptPassword(user.getPassword()));
            userRepository.save(user);

            return new ResponseEntity<String>("User Updated", HttpStatus.valueOf(200));

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Empty"))
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
            else
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(401));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public ResponseEntity<String> delete(Long id) {

        try {

            userRepository.deleteById(id);

            return new ResponseEntity<String>("User Deleted", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    public String encryptPassword(String password) {

        String salt = BCrypt.gensalt(10);
        String passEncoded = BCrypt.hashpw(password, salt);

        return passEncoded;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent())
            throw new UsernameNotFoundException("User not found");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("USER"));
        if (user.get().getAdmin())
            authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), authorities);
    }
}
