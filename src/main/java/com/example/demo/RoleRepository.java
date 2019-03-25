package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("SUPER"));
        roleRepository.save(new Role("ADMIN"));


        Role adminRole = roleRepository.findByRole("ADMIN");
        Role superRole = roleRepository.findByRole("SUPER");
        Role userRole = roleRepository.findByRole("USER");

        //needs user
//        User user = new User("jim@jim.com", "password", "Jim", "Jimmerson", true);
//        user.setRoles(Arrays.asList(userRole));
//        userRepository.save(user);
        User user = new User(String email, String password, String firstName, String lastName, boolean enabled, String street,
                String state, String city, String zip, String social)
        //needs admin

//        user = new User("admin@admin.com", "password", "Admin",
//                "User", true, "admin");
//        user.setRoles(Arrays.asList(adminRole));
//        userRepository.save(user);

        //needs supervisor
    }
}