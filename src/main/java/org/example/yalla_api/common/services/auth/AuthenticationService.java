package org.example.yalla_api.common.services.auth;

import org.example.yalla_api.common.dtos.auth.AuthResponse;
import org.example.yalla_api.common.entities.auth.User;
import org.example.yalla_api.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuth {

    @Autowired
   private UserService userService;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthResponse loginWithEmailAndPassword(String email, String password) throws UsernameNotFoundException {
        // Fetch the user by email
        User user = userService.findByUsername(email);
        if (user == null) {
            // If user is not found, throw an exception
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Verify the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // If password doesn't match, throw an exception
            throw new BadCredentialsException("Invalid password");
        }

        // Check if the user account is enabled
        if (!user.isEnabled()) {
            throw new DisabledException("User account is disabled");
        }

        // Check if the account is locked
        if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }

        // Authentication successful, return UserDetails
        return createAuthResponse(user);
    }


    private AuthResponse createAuthResponse (UserDetails userDetails){

        return jwtTokenUtil.createAuthResponse(userDetails);
    }

    public String refreshAccessToken(String refreshToken) {
        // Validate refresh token
        if (!jwtTokenUtil.validateToken(refreshToken, null, false)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // Extract username from refresh token
        String username = jwtTokenUtil.extractUsername(refreshToken, false);
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Generate new access token
        return jwtTokenUtil.generateAccessToken(user);
    }
}
