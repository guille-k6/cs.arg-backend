package com.guille.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/get")
public class DemoController {
    @Autowired
    public DemoController(){
    }

    @GetMapping
    public ResponseEntity<String> sayHello(){
        try{
            return ResponseEntity.ok("Hello from secured endpoint");
        } catch (Exception ex) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }

    }
}
