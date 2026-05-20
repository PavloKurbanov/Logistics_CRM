package org.example.logistics_crm.security.service;

import org.example.logistics_crm.entity.user.User;
import org.example.logistics_crm.repository.UserRepository;
import org.example.logistics_crm.security.SecurityUser;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@NullMarked
public class StaffUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public StaffUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new SecurityUser(user);
    }
}
