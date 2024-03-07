package com.tiid.tienda.controllers;

import com.tiid.tienda.entities.Profile;
import com.tiid.tienda.entities.User;
import com.tiid.tienda.requests.ProfileRequest;
import com.tiid.tienda.requests.SiggnProfileRequest;
import com.tiid.tienda.requests.SingUpRequest;
import com.tiid.tienda.requests.StoreRequest;
import com.tiid.tienda.responses.*;
import com.tiid.tienda.services.AuthService;
import com.tiid.tienda.services.ProfileService;
import com.tiid.tienda.services.StorageService;
import com.tiid.tienda.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final StorageService storageService;
    private final ProfileService profileService;
    private final AuthService authService;
    private final StoreService storeService;

    @Autowired
    public AdminController(StorageService storageService, ProfileService profileService, AuthService authService, StoreService storeService){
        this.storageService = storageService;
        this.profileService = profileService;
        this.authService = authService;
        this.storeService = storeService;
    }


    @PostMapping(value = "/store/create")
    public ResponseEntity<StoreResponse> createStore(@ModelAttribute StoreRequest request) throws URISyntaxException {

        MultipartFile banner = request.getBanner();
        MultipartFile logo = request.getLogo();
        return  ResponseEntity.ok(storeService.createStore(request));
    }

    @PostMapping(value = "/profile/create")
    public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.createProfile(profileRequest));
    }

    @PutMapping(value = "/profile/edit/{id}")
    public ResponseEntity<ProfileResponse> editProfile(@RequestBody ProfileRequest profileRequest, @PathVariable(name = "id") long id){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.editProfile(profileRequest, id));
    }


    @DeleteMapping(value = "/profile/delete/{id}")
    public ResponseEntity<String> deleteProfile (@PathVariable(name = "id") long id){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.deleteProfile(id));
    }


    @GetMapping(value = "/profiles/all")
    public ResponseEntity<List<Profile>> getAllProfiles(){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.getAllProfiles());
    }

    @GetMapping(value = "/profile/{id}")
    public ResponseEntity<ProfileResponse> getOneProfile(@PathVariable(name = "id") long id){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.findProfileById(id));
    }


    @PostMapping(value = "/profile/siggn")
    public ResponseEntity<SiggnProfileResponse> assignProfile(@RequestBody SiggnProfileRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileService.SiggnProfile(request));

    }

    @PostMapping(value = "/employee/singup")
    public ResponseEntity<AuthResponse> singUpEmployee(@RequestBody SingUpRequest request){
        return ResponseEntity.ok(authService.singup(request, "EMPLOYEE"));
    }

    @PostMapping(value = "/profile/remove/employee")
    public ResponseEntity<String> removeEmployeeProfile(@RequestParam String email){
        return ResponseEntity.ok(profileService.removeProfileToEmployee(email));
    }


    @GetMapping("/employee/all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(defaultValue = "true") boolean onlyActive){
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.getAllEmployees(onlyActive));
    }

    @GetMapping(value = "/employee/search")
    public  ResponseEntity<List<EmployeeResponse>> getSearchEmployees(@RequestParam String nameToSearch){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(authService.searchemployee(nameToSearch));

    }



}
