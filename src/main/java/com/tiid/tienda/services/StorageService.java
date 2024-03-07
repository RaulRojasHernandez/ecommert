package com.tiid.tienda.services;

import com.tiid.tienda.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {


    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storeRepository){
        this.storageRepository = storeRepository;
    }




    public boolean uploadFile(String bucketName, String filePath, MultipartFile file){

        File fileObject = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        return storageRepository.uploadFile(bucketName, fileName, fileObject);

    }

    private File convertMultiPartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)){
            fileOutputStream.write(file.getBytes());
        }catch (IOException exception){
            return null;
        }
        return convertedFile;

    }
}
