package com.stefanini.spotifanini.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefanini.spotifanini.model.User;
import com.stefanini.spotifanini.repository.UserRepository;
import com.stefanini.spotifanini.util.Validations;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {

        try {
            return userRepository.findAll();
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

    public ResponseEntity<String> update(Long id, User user) {

        try {

            Optional<User> oldUser = userRepository.findByUsername(user.getUsername());

            Validations.notExists(user.getUsername(), "Empty Username");
            Validations.notExists(user.getPassword(), "Empty Password");
            if (oldUser.isPresent() && oldUser.get().getId() != id)
                Validations.isPresent(oldUser, "User Already Exists");

            user.setId(id);
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
}
