package com.maverick.service;

import java.util.List;

import com.maverick.entity.*;
import com.maverick.repo.ManagerRepository;
import com.maverick.enums.UserStatus;
import com.maverick.repo.CustomerRepository;
import com.maverick.repo.ExecutiveRepository;
import com.maverick.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerRepository customerRepository;
    private ExecutiveRepository executiveRepository;
    private ManagerRepository managerRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       CustomerRepository customerRepository, ExecutiveRepository executiveRepository,
                       ManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.executiveRepository = executiveRepository;
        this.managerRepository = managerRepository;
    }

    public User signUp(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new RuntimeException("Username already exists!");
        String plainPassword = user.getPassword();							// plain password
        String encodedPassword = passwordEncoder.encode(plainPassword);	// encodes the plain password
        user.setPassword(encodedPassword);									// encrypted password set to user password
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    public User putUserStatus(int id, String status) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
        user.setUserStatus(UserStatus.valueOf(status));
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Object getUserInfo(String username) {
        User user = userRepository.getByUsername(username);
        if(user.getUserStatus()!=UserStatus.ACTIVE)
            throw new RuntimeException("INACTIVE");
        switch(user.getRole()) {
            case "CUSTOMER":
                Customer customer = customerRepository.getCustomerByUsername(username);
                return customer;
            case "EXECUTIVE":
                Executive executive = executiveRepository.getExecutiveByUsername(username);
                return executive;
            case "MANAGER":
                Manager manager = managerRepository.getManagerByUsername(username);
                return manager;
            case "ADMIN":
                Admin admin = new Admin();
                admin.setName("Admin");
                admin.setUser(user);
                return admin;
            default:
                return null;
        }
    }

    // inside UserService
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}