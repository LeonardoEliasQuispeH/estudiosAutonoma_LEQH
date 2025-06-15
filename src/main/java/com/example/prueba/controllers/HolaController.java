package com.example.prueba.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HolaController {
    
    @GetMapping("/")
    public String hola() {
        return "Hola mundo";
    }
    
}
