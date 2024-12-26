package org.example.yalla_api.common.services.auth;

import org.example.yalla_api.common.entities.auth.Admin;
import org.example.yalla_api.common.entities.auth.User;
import org.example.yalla_api.common.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements  UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user instanceof Admin) {
            return (Admin) user;
        }
        return null;
    }


    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user =   findByUsername(username);
     if(user == null){
         throw new UsernameNotFoundException("User not found");
     }
     return user;
    }
}
