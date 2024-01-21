// UserServiceImpl.java
package com.icsd19080_icsd19235;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    // Method to create a new user
    public void createUser(User user) {
        Scanner scanner = new Scanner(System.in);

        // Create a new User
        User User = new User();
        System.out.println("Enter username:");
        User.setUsername(scanner.next());
        System.out.println("Enter password:");
        User.setPassword(scanner.next());
        System.out.println("Enter full name:");
        User.setFullName(scanner.next());

        // Save the new user
        userRepository.save(User);
        assignRole(User);

        System.out.println("User created: " + User);
        scanner.close();
    }

    public Role assignRole(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Roles:");
        List<Role> availableRoles = roleRepository.findAll();
        availableRoles.stream()
                .map(role -> role.getState().toString())
                .distinct()
                .forEach(System.out::println);

        while (true) {
            System.out.println("Enter Role Name to assign:");
            String roleNameInput = scanner.next();
            Role.RoleName selectedRoleName = Role.RoleName.valueOf(roleNameInput);

            if (selectedRoleName != null) {
                Role selectedRole = new Role();
                selectedRole.setState(selectedRoleName);
                selectedRole.setUser(user);
                user.getRoles().add(selectedRole);
                roleRepository.save(selectedRole);
                System.out.println("Role assigned successfully.");
                break;
            } else {
                System.out.println("Invalid role name. Please try again.");
            }
        }
        scanner.close();
        return null;
    }
    //Overload for not having User object
    public Role assignRole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        Long userId = scanner.nextLong();
    
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
    
            System.out.println("Available Roles:");
            List<Role> availableRoles = roleRepository.findAll();
            availableRoles.stream()
                    .map(role -> role.getState().toString())
                    .distinct()
                    .forEach(System.out::println);
    
            while (true) {
                System.out.println("Enter Role Name to assign:");
                String roleNameInput = scanner.next();
                Role.RoleName selectedRoleName = Role.RoleName.valueOf(roleNameInput);
    
                if (selectedRoleName != null) {
                    Role selectedRole = new Role();
                    selectedRole.setState(selectedRoleName);
                    selectedRole.setUser(user);
                    user.getRoles().add(selectedRole);
                    roleRepository.save(selectedRole);
                    System.out.println("Role assigned successfully.");
                    break;
                } else {
                    System.out.println("Invalid role name. Please try again.");
                }
            }
        } else {
            System.out.println("User not found with the given user ID: " + userId);
        }
    
        scanner.close();
        return null;
    }
    
    public User login() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Enter username:");
        String username = scanner.next();
    
        System.out.println("Enter password:");
        String password = scanner.next();
    
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getFullName() + "!");
            System.out.println();
            return user;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }
    

}