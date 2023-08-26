package com.guille.security.auth;

import com.guille.security.models.dtoRequest.AuthenticationRequest;
import com.guille.security.models.dtoResponse.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        //return ResponseEntity.ok(service.register(request));
        try {
            AuthenticationResponse response = service.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        try {
            Boolean emailExists = service.emailExists(request.getEmail());
            if(!emailExists){
                throw new NoSuchElementException("Email no registrado");
            }

            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Contraseña incorrecta.");
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping
    public Map<String, String> sayHoliwis(){
        return Collections.singletonMap("response", "holiwis");
    }
}
