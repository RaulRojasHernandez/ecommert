package com.tiid.tienda.services;

import com.tiid.tienda.entities.Shipping;
import com.tiid.tienda.entities.User;
import com.tiid.tienda.exceptions.ResourceNotFoundExecption;
import com.tiid.tienda.exceptions.ShippingError;
import com.tiid.tienda.repository.ShippingRepository;
import com.tiid.tienda.repository.UserRepository;
import com.tiid.tienda.requests.ShippingRequest;
import com.tiid.tienda.responses.ShippingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final UserRepository userRepository;


    @Autowired
    public ShippingService(ShippingRepository shippingRepository, UserRepository userRepository){
        this.shippingRepository = shippingRepository;
        this.userRepository = userRepository;
    }


    public void saveShippingByUser(ShippingRequest request, String email){

       List<Shipping> shippingsUser = shippingRepository.findAllShippignsUser(email);
       if(shippingsUser.size() == 3){
            throw new ShippingError(email);
       }else{
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundExecption("user", "email", email));

            Shipping shipping = new Shipping();
            shipping.setName(request.getName());
            shipping.setUser(user);
            shipping.setState(request.getState());
            shipping.setCity(request.getCity());
            shipping.setStreet(request.getStreet());
            shipping.setNumber(request.getNumber());
            shipping.setInternalNumber(request.getInternalNumber());
            shipping.setCologne(request.getCologne());
            shipping.setReferencesHouse(request.getReferencesHouse());
            shipping.setCp(request.getCp());
            shipping.setActive(true);

            shippingRepository.save(shipping);

       }

    }

    public List<ShippingResponse> getAllAdressUser(String email){
        List<Shipping> shippings =shippingRepository.findAllShippignsUser(email);
        List<ShippingResponse> shippingResponses = new ArrayList<>();

        for(Shipping shipping: shippings){
            ShippingResponse response = new ShippingResponse();
            response.setId(shipping.getId());
            response.setEmailUser(shipping.getUser().getEmail());
            response.setName(shipping.getName());
            response.setState(shipping.getState());
            response.setCity(shipping.getCity());
            response.setStreet(shipping.getStreet());
            response.setNumber(shipping.getNumber());
            response.setInternalNumber(shipping.getInternalNumber());
            response.setCologne(shipping.getCologne());
            response.setReferencesHouse(shipping.getReferencesHouse());
            response.setCp(shipping.getCp());

            shippingResponses.add(response);
        }

        return shippingResponses;
    }


    public void deactivateAdressUser(Long id){
        Shipping shipping = shippingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExecption("Shipping", "id", Long.toString(id)));
        shipping.setActive(false);

        shippingRepository.save(shipping);
    }




}
