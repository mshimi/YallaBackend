package org.example.yalla_api.common.controllers.auth;

import org.example.yalla_api.common.dtos.auth.AuthRequest;
import org.example.yalla_api.common.mappers.UserMapper;
import org.example.yalla_api.common.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5174"})

public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

   public AuthController(UserMapper userMapper){
       this.userMapper = userMapper;
   }

   private final UserMapper userMapper;
    @CrossOrigin(origins = "http://localhost:5174")
    @PostMapping("/login")
    public ResponseEntity<?> loginWithEmailAndPassword(@RequestBody AuthRequest authRequest) {

        try {
            return ResponseEntity.ok(authenticationService.loginWithEmailAndPassword(authRequest.getUsername(), authRequest.getPassword()));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }
    }
    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(userMapper.userToUserResponse(userDetails));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me (){
        return ResponseEntity.ok("hi from Auth");
    }

}
