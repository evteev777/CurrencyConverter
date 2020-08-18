package ru.evteev.converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evteev.converter.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
