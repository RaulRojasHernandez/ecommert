package com.tiid.tienda.services;

import com.tiid.tienda.entities.Store;
import com.tiid.tienda.repository.StoreRepository;
import com.tiid.tienda.requests.StoreRequest;
import com.tiid.tienda.responses.StoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StorageService storageService;



    @Autowired
    public StoreService(StoreRepository storeRepository, StorageService storageService){
        this.storeRepository = storeRepository;
        this.storageService = storageService;

    }

    public StoreResponse createStore(StoreRequest request){


        storageService.uploadFile("imagenes", "imagenes/logo/", request.getLogo());
        storageService.uploadFile("imagenes", "iamgenes/banner", request.getBanner());

        String bannerEjemplo = "imagnese/aqui/img1.png";
        String logoEjemplo = "imagnese/aqui/logo.png";

        Store store = new Store();
        store.setName(request.getName());
        store.setLogo(logoEjemplo);
        store.setBanner(bannerEjemplo);

        storeRepository.save(store);

        return StoreResponse.builder()
                .name(store.getName())
                .logo(store.getLogo())
                .banner(store.getBanner())
                .build();




    }
}
