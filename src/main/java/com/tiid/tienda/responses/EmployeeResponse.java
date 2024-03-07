package com.tiid.tienda.responses;

import com.tiid.tienda.entities.Profile;
import com.tiid.tienda.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeResponse {
    private String name;
    private String lastName;
    private String email;
    private ProfileResponse profile;

    private boolean active;


    public EmployeeResponse(User user){
       this.name = user.getName();
       this.lastName = user.getLastName();
       this.email = user.getEmail();
       this.profile = new ProfileResponse(user.getProfile());
       this.active = user.isActive();
    }


}
