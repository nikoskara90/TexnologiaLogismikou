package com.icsd19080_icsd19235;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByFullName(String fullName);
    
    Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
