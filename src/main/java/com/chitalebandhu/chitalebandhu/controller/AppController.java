package com.chitalebandhu.chitalebandhu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/app")
public class AppController {
    @GetMapping("/version")
    public Map<String , Object> getVersion(){
        Map<String, Object> response = new HashMap<>();

        response.put("latestVersion", "1.0.0+1");
        response.put("forceUpdate", true);
        response.put("apkUrl", "https://drive.usercontent.google.com/download?id=1OC4KH29DuTppsN2KPLXZwQB2NRP9bzd1&export=download&authuser=0");

        System.out.println("Sending version");
        return response;
    }
}
