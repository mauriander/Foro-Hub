package com.example.Foro_Hub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hola {
    @GetMapping("/hola")
    public String Hola(){
        return "hOLA Maurriocarlos";
    }
}
