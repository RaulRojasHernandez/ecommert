package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.User;
import com.tiid.tienda.responses.AuthResponse;
import com.tiid.tienda.requests.LoginRequest;
import com.tiid.tienda.requests.SingUpRequest;
import com.tiid.tienda.services.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

        return authService.login(request);

    }

    @PostMapping(value = "/singup")
    public ResponseEntity<AuthResponse> singUp(@RequestBody SingUpRequest request){

        return ResponseEntity.ok(authService.singup(request, "BUYER"));
    }

    @PostMapping(value = "/password/send/email/recovery")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        authService.sendEmailRecovery(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Send");

    }

    @GetMapping(value = "/check/jwt")
    public ResponseEntity<Boolean> checkJwt(@RequestParam String value){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.checkJwt(value));
    }



    @GetMapping(value = "/jwt/refresh")
    public ResponseEntity<String> refreshToken(@RequestParam String oldToken){
        System.out.println(oldToken);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.refrestToken(oldToken));

    }

    @PostMapping(value = "/user/deactivate")
    public ResponseEntity<String> refreshToken(@RequestParam Long id){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.deactivateUser(id));

    }

    @PostMapping(value = "/users/list")
    public ResponseEntity<List<User>> refreshToken(@RequestParam(defaultValue = "true") boolean onlyActive){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.listUsers(onlyActive));

    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.findUserById(id));

    }









}
