package com.tiid.tienda.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiggnProfileResponse {
    String nameProfile;
    String nameEmployee;
    String emailEmployee;
}
