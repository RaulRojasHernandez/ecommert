package com.tiid.tienda.services;

import com.tiid.tienda.entities.Profile;
import com.tiid.tienda.entities.Role;
import com.tiid.tienda.entities.User;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.repository.ProfileRepository;
import com.tiid.tienda.repository.UserRepository;
import com.tiid.tienda.requests.ProfileRequest;
import com.tiid.tienda.requests.SiggnProfileRequest;
import com.tiid.tienda.responses.ProfileResponse;
import com.tiid.tienda.responses.SiggnProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository){
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;

    }

    public ProfileResponse createProfile(ProfileRequest request){

        Profile profile = new Profile();

        profile.setName(request.getName());
        profile.setCreateProduct(request.isCreateProduct());
        profile.setEditProduct(request.isEditProduct());
        profile.setDeleteProduct(request.isDeleteProduct());
        profile.setEditInventory(request.isEditInventory());

        profileRepository.save(profile);


        return getProfileResponse(profile);


    }


    public ProfileResponse editProfile(ProfileRequest request, Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Profile", "id", Long.toString(id)));


        profile.setName(request.getName());
        profile.setCreateProduct(request.isCreateProduct());
        profile.setEditProduct(request.isEditProduct());
        profile.setDeleteProduct(request.isDeleteProduct());
        profile.setEditInventory(request.isEditInventory());

        profileRepository.save(profile);

        return getProfileResponse(profile);

    }

    public String deleteProfile(Long id) {
        profileRepository.deleteById(id);

        return "Deleted";

    }

    public String removeProfileToEmployee(String email){
        User user = userRepository.findUserByEmail(email).orElseThrow();
        user.setProfile(null);
        userRepository.save(user);

        return "Removed";
    }

    public List<Profile> getAllProfiles(){
        List<Profile> profiles = profileRepository.findAll();
        return profiles;
    }

    public ProfileResponse findProfileById(Long id){
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Profile", "id", Long.toString(id)));

        return getProfileResponse(profile);

    }

    public SiggnProfileResponse SiggnProfile(SiggnProfileRequest request){
        User user = userRepository.findUserByEmail(request.getEmployeeEmail()).orElseThrow(() -> new ResourceNotFoundExecption("user", "email", request.getEmployeeEmail()));
        Profile profile = profileRepository.findById(request.getProfileId()).orElseThrow(() -> new ResourceNotFoundExecption("Profile", "id", Long.toString( request.getProfileId())));;

        user.setProfile(profile);

        userRepository.save(user);


        SiggnProfileResponse siggnProfileResponse = new SiggnProfileResponse();
        siggnProfileResponse.setNameProfile(profile.getName());
        siggnProfileResponse.setNameEmployee(user.getName() + user.getLastName());
        siggnProfileResponse.setEmailEmployee(user.getEmail());


        return  siggnProfileResponse;
    }


    public Collection<? extends GrantedAuthority> getPermissions(User user){
        Profile profile = user.getProfile();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(profile == null){
            return null;
        }
        if(user.getRole().equals(Role.ADMIN)){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if(profile.isCreateProduct()){
            authorities.add(new SimpleGrantedAuthority("CREATE_PRODUCT"));
        }
        if(profile.isEditProduct()){
            authorities.add(new SimpleGrantedAuthority("EDIT_PRODUCT"));
        }
        if(profile.isDeleteProduct()){
            authorities.add(new SimpleGrantedAuthority("DELETE_PRODUCT"));
        }
        if(profile.isEditInventory()){
            authorities.add(new SimpleGrantedAuthority("EDIT_INVENTORY"));
        }
        return authorities;
    }


    private ProfileResponse getProfileResponse(Profile profile) {
        ProfileResponse profileResponse = new ProfileResponse();

        profileResponse.setId(profile.getId());
        profileResponse.setName(profile.getName());
        profileResponse.setCreateProduct(profile.isCreateProduct());
        profileResponse.setEditProduct(profile.isEditProduct());
        profileResponse.setDeleteProduct(profile.isDeleteProduct());
        profileResponse.setEditInventory(profile.isEditInventory());

        return profileResponse;
    }






}
