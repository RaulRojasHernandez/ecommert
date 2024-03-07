package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiggnProfileRequest {
    String employeeEmail;
    Long profileId;



}
