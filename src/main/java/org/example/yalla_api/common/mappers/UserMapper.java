package org.example.yalla_api.common.mappers;

import org.example.yalla_api.common.dtos.auth.UserResponse;

import org.example.yalla_api.common.entities.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper   {


    // Assuming single role
   default UserResponse userToUserResponse(UserDetails user){
       UserResponse userResponse = new UserResponse();

       if(user instanceof User){
          User instaceOfUser = (User) user;
          userResponse.setUsername(instaceOfUser.getUsername());
          userResponse.setRole(instaceOfUser.getRole().name());
          userResponse.setId(instaceOfUser.getId());
          userResponse.setName(instaceOfUser.getName());
          userResponse.setFirstName(instaceOfUser.getFirstName());
       }

       return userResponse;
   }
}