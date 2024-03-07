package com.tiid.tienda.services;

import com.tiid.tienda.Utilities.Utility;
import com.tiid.tienda.entities.Product;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.resources.RecoveryPasswordHTML;
import com.tiid.tienda.responses.AuthResponse;
import com.tiid.tienda.requests.LoginRequest;
import com.tiid.tienda.entities.Role;
import com.tiid.tienda.requests.SingUpRequest;
import com.tiid.tienda.entities.User;
import com.tiid.tienda.repository.UserRepository;
import com.tiid.tienda.responses.EmployeeResponse;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final Utility utility;

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${frontend.url}")
    private String domain;

    @Value("${frontend.path.password.reset}")
    private String path;



    public ResponseEntity<AuthResponse> login(LoginRequest request){
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() ->
                new ResourceNotFoundExecption("User", "email",request.getEmail()));
        boolean passwordCorrectly = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (passwordCorrectly && user.isActive()){
            AuthResponse token = new AuthResponse();
            token.setToken( jwtService.generateToken(user));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(token);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
        }
    }

    public AuthResponse singup(SingUpRequest request, String role){

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                .role(Role.BUYER)
                .active(true)
                .build();

        if(role.equals("EMPLOYEE")){
            user.setRole(Role.EMPLOYEE);
        }

        if(role.equals("ADMIN")) {
            user.setRole(Role.ADMIN);
        }

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }


    public String refrestToken(String token){
        if(jwtService.validateToken(token)){
            Claims claims = jwtService.getAllClaims(token);
            User user = userRepository.findUserByEmail(claims.getSubject()).orElseThrow(() -> new ResourceNotFoundExecption("User", "email", claims.getSubject()));
            return jwtService.generateToken(user);
        }else{
            return null;
        }
    }

    public String deactivateUser(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("User", "id", Long.toString(id)));
        user.setActive(false);
        userRepository.save(user);

        return "Deactivated";

    }


    public List<User> listUsers(boolean onlyActives){

        if(onlyActives){
            return userRepository.findActiveUsers();
        }
        else{
            return userRepository.findAll();
        }


    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("User", "id", Long.toString(id)));
    }

    public void sendEmailRecovery(String emailUser) throws MessagingException {
        User user = userRepository.findUserByEmail(emailUser).orElseThrow(() -> new ResourceNotFoundExecption("User", "email", emailUser));
        String password = changePassword(user.getId());


        String html = new RecoveryPasswordHTML( password, user.getName()).getHTML();
        emailService.sendHtmlEmail(user.getEmail(), "Tu nueva contraseña", html);

    }



    public List<EmployeeResponse> getAllEmployees (boolean onlyActive){

        List<User> users = new ArrayList<>();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();

        if(onlyActive){
            users = userRepository.findAllEmployeesActive();
        }else{
            users = userRepository.findAllEmployees();
        }

        for(User user: users){
            EmployeeResponse response = new EmployeeResponse(user);
            employeeResponses.add(response);
        }

        return employeeResponses;

    }

    //Busca por empleado
    public List<EmployeeResponse> searchemployee (String nameToSearch){
        List<User> users = userRepository.findbyname(nameToSearch);
        List<EmployeeResponse> employeeResponses = new ArrayList<>();

        for(User user: users){
            EmployeeResponse response = new EmployeeResponse(user);
            employeeResponses.add(response);
        }


        return employeeResponses;
    }


    public Boolean checkJwt(String jwtBase64){
        String stringJwt = utility.deconvertB64(jwtBase64);
        return jwtService.validateToken(stringJwt);
    }


    public String changePassword(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newPassword = generateRandomString();

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return newPassword;
    }


    private static String generateRandomString() {
        int length = 8;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

}
