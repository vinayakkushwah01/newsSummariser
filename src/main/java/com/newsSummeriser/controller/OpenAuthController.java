package com.newsSummeriser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newsSummeriser.Config.OpenAuthService;
import com.newsSummeriser.dto.LoginRequest;
import com.newsSummeriser.dto.RegisterRequest;
import com.newsSummeriser.model.User;


@CrossOrigin(origins = "*") 
@RestController
public class OpenAuthController {
   
    @Autowired
    private OpenAuthService registerService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest rReq){

        return registerService.registerUser(rReq);
    }
    @PostMapping("/login")
    public String   loginUser(@RequestBody LoginRequest lReq ){
        return registerService.verifyLogin(lReq);
    }
    
 

}
