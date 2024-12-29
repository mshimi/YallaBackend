package org.example.yalla_api.config;

import org.example.yalla_api.common.entities.auth.User;
import org.example.yalla_api.common.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
//public class AuditorAwareImpl implements AuditorAware<Long> {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.of(1L);
//        }
//
//       Optional<User> user = userRepository.findByUsername(authentication.getName());
//        return user.map(User::getId);
//
//    }
//}
