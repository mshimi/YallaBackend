package org.example.yalla_api.common.services.auth;

import org.example.yalla_api.common.dtos.auth.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuth  {

    public AuthResponse loginWithEmailAndPassword(String email, String password);



}
