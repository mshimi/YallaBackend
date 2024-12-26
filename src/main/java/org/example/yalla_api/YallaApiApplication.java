package org.example.yalla_api;

import org.example.yalla_api.common.entities.auth.User;
import org.example.yalla_api.common.enums.Role;
import org.example.yalla_api.common.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class YallaApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(YallaApiApplication.class, args);
    }


    @Autowired
 private PasswordEncoder passwordEncoder;
    @Autowired
   private UserRepository userRepository;



    @Override
    public void run(String... args) throws Exception {







       if(userRepository.findAll().isEmpty()){
           User user = new User();
           user.setRole(Role.ADMIN);
           user.setUsername("admin");
           user.setPassword(passwordEncoder.encode("admin"));
          System.out.print( userRepository.save(user));
       }

    }
}
