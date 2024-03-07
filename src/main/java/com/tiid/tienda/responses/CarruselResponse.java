package com.tiid.tienda.responses;
import com.tiid.tienda.entities.Carrusel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CarruselResponse {
    private Long id;
    private String name;
    private boolean active;
    private String img;


    public CarruselResponse(Carrusel carrusel){
        this.id = carrusel.getId();
        this.name = carrusel.getName();
        this.active = carrusel.getActive();
        this.img = carrusel.getImg();
    }
}
