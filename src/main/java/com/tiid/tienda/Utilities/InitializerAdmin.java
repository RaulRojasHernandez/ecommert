package com.tiid.tienda.Utilities;

import com.tiid.tienda.entities.User;
import com.tiid.tienda.repository.UserRepository;
import com.tiid.tienda.requests.SingUpRequest;
import com.tiid.tienda.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializerAdmin  implements ApplicationRunner {


    @Autowired
    private UserRepository userRepository;

    @Value("${admin.initializer.password}")
    private String passwordAdmin;

    @Value("${admin.initializer.email}")
    private String emailAdmin;

    @Autowired
    private AuthService authService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.hasAdmin() == null){
            SingUpRequest request = new SingUpRequest();
            request.setName("initializerName");
            request.setLastName("initializerLastName");
            request.setEmail(emailAdmin);
            request.setPassword(passwordAdmin);

            authService.singup(request, "ADMIN");

        }
    }
}
