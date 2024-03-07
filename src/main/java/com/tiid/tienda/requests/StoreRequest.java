package com.tiid.tienda.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {
    String name;
    MultipartFile banner;
    MultipartFile logo;

}
