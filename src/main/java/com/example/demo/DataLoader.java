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

<<<<<<< HEAD
        User user = new User("joe@joe.com","password", "Joe", "Joe",true, "1421 Vertel Street",
                "MD", "Rockville", "20905", "8754");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("Admin@admin.com", "password", "Admin","User",true,"efwdewe",
                "MD","Silver Spring","20905","3333");


//        user = new User("admin@admin.com", "password", "Admin",
//                "User", true, "admin");
//        user.setRoles(Arrays.asList(adminRole));
//        userRepository.save(user);

        //needs supervisor
    }
}