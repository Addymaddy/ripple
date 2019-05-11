package com.ripple.controller;

import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import com.ripple.model.ApplicationUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ripple.repository.ApplicationUserRepository;
import com.ripple.service.TokenService;

@RestController
@RequestMapping("/users")
public class UserController {
    private ApplicationUserRepository applicationUserRepository;

    public UserController(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }
    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        applicationUserRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse> login(@RequestBody ApplicationUser user) {
        String userName = user.getUsername();
        ApplicationUser foundUser = applicationUserRepository.findByUsername(userName);

        if(foundUser==null){
            ApplicationResponse response = new ApplicationResponse(ApplicationCode.FAILED_LOGIN, "User Doesnt Exist");
            return new ResponseEntity<ApplicationResponse>(response, HttpStatus.UNAUTHORIZED);
        }

        if(!foundUser.getPassword().equals(DigestUtils.sha256Hex(user.getPassword()))){
            ApplicationResponse response = new ApplicationResponse(ApplicationCode.FAILED_LOGIN, "Invalid User Credentials.");
            return new ResponseEntity<ApplicationResponse>(response, HttpStatus.UNAUTHORIZED);
        }

        ApplicationResponse response = new ApplicationResponse(ApplicationCode.SUCCESS, "Login Successfull");
        response.addParams("userId", foundUser.getId()+"");
        HttpHeaders headers = new HttpHeaders();
        headers.add("User_Token", TokenService.getUserToken(user));
        ResponseEntity<ApplicationResponse> result =  new ResponseEntity<ApplicationResponse>(response, headers, HttpStatus.OK);

        return result;
    }

}