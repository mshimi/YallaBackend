package org.example.yalla_api.common.dtos.auth;

import lombok.Data;

@Data
public class UserResponse {

   private String username;
   private String role;
   private Long id;
   private String name;
   private String firstName;

}
