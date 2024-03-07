package com.tiid.tienda.demo;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Demo {

    @PostMapping(value = "demo")
    public String welcome(){
        return  "Un endpoint seguro";
    }
}
