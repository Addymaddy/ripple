package com.ripple.controller;

import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ripple.service.TokenService;

/**
 * Created by intel on 5/10/2019.
 */
@RestController
@RequestMapping("/vms")
public class VMController {

    @GetMapping("/{userId}")
    public ResponseEntity<ApplicationResponse> getVm(@PathVariable("userId") String userId, @RequestHeader("User_Token") String userToken){

        System.out.println("Got the user Id------> " + userId);

        if(userToken == null || userToken.isEmpty())
        {
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.Invalid_User_Token,"Invalid User token");
            return new ResponseEntity<ApplicationResponse>( HttpStatus.UNAUTHORIZED);
        }
        if(TokenService.validateToken(userToken)){
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.SUCCESS,"Vm Get Succcessfull");
            return new ResponseEntity<ApplicationResponse>( HttpStatus.OK);
        }
        else
        {
            ApplicationResponse rep = new ApplicationResponse(ApplicationCode.User_Token_Tempered,"User Tokken Tempered");
            return new ResponseEntity<ApplicationResponse>( HttpStatus.UNAUTHORIZED);
        }
    }
}
