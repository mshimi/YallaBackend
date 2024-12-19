package org.example.yalla_api.common.controllers.core;

import org.example.yalla_api.config.annotations.AdminOrControllerOnly;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
public class CoreController {

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("test from core");
    }

    @AdminOrControllerOnly
    @GetMapping("/test2")
    public ResponseEntity<?> test2(){
        return ResponseEntity.ok("test 2 from core For Admin or Controller Only");
    }


}
