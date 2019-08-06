package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DepartmentRepository departmentRepository;
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


        User user = new User("joe@joe.com","password", "Joe", "Joe",true, "1421 Vertel Street",
                "MD", "Rockville", "20905", "8754");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        Department department = new Department("HR");
        user = new User("super1@super.com","password","HR","Super",true,"dewefwd",
                "MD","Rockville","20852","8788",department);
        user.setRoles(Arrays.asList(userRole));
        user.setRoles(Arrays.asList(superRole));
        Set<User> users = new HashSet<>();
        users.add(user);
        department.setUsers(users);
        departmentRepository.save(department);
        userRepository.save(user);

        department = new Department("Management");
        user = new User("super2@super.com","password","Management","Super",true,"dewefwd",
                "MD","Rockville","20852","8788",department);
        user.setRoles(Arrays.asList(userRole));
        user.setRoles(Arrays.asList(superRole));
        users = new HashSet<>();
        users.add(user);
        department.setUsers(users);
        departmentRepository.save(department);
        userRepository.save(user);

        department = new Department("Healthcare");
        user = new User("super3@super.com","password","Healthcare","Super",true,"dewefwd",
                "MD","Rockville","20852","8788",department);
        user.setRoles(Arrays.asList(userRole));
        user.setRoles(Arrays.asList(superRole));
        users = new HashSet<>();
        users.add(user);
        department.setUsers(users);
        departmentRepository.save(department);
        userRepository.save(user);

        user = new User("Admin@admin.com", "password", "Admin","User",true,"efwdewe",
                "MD","Silver Spring","20905","3333");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

    }
}