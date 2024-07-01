package org.mansumugang.mansumugang_service.repository;


import org.mansumugang.mansumugang_service.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<org.springframework.security.core.userdetails.User> findByUsername(String username);
    Optional<org.springframework.security.core.userdetails.User> findByNickname(String nickname);


}
