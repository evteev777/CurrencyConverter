package ru.evteev.converter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.evteev.converter.repository.UserRepo;

// Lombok
@RequiredArgsConstructor

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
