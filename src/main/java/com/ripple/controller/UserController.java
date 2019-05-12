package com.ripple.controller;

import com.ripple.exception.EmailNotUniqueException;
import com.ripple.exception.MobileNoNotUniqueException;
import com.ripple.model.ApplicationCode;
import com.ripple.model.ApplicationResponse;
import com.ripple.model.ApplicationUser;
import com.ripple.model.UserCredentials;
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

import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {
    private ApplicationUserRepository applicationUserRepository;

    Logger logger = Logger.getLogger(UserController.class.getName());


    public UserController(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApplicationResponse> signUp(@RequestBody ApplicationUser user) {
        validate(user);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        applicationUserRepository.save(user);
        ApplicationResponse response = new ApplicationResponse(ApplicationCode.SUCCESSFUL_SIGNUP, "User Created succesfully");
        return new ResponseEntity<ApplicationResponse>(response, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse> login(@RequestBody UserCredentials userCredentials) {
        String userName = userCredentials.getUserName();
        System.out.println("Got the User credentials " + userCredentials.getUserName() + "     " +userCredentials.getPassword());
        ApplicationUser foundUser = applicationUserRepository.findByUsername(userName);

        if(foundUser==null){
            ApplicationResponse response = new ApplicationResponse(ApplicationCode.FAILED_LOGIN, "User Doesnt Exist");
            return new ResponseEntity<ApplicationResponse>(response, HttpStatus.UNAUTHORIZED);
        }

        if(!foundUser.getPassword().equals(DigestUtils.sha256Hex(userCredentials.getPassword()))){
            ApplicationResponse response = new ApplicationResponse(ApplicationCode.FAILED_LOGIN, "Invalid User Credentials.");
            return new ResponseEntity<ApplicationResponse>(response, HttpStatus.UNAUTHORIZED);
        }

        ApplicationResponse response = new ApplicationResponse(ApplicationCode.SUCCESS, "Login Successfull");
        response.addParams("userId", foundUser.getId()+"");
        HttpHeaders headers = new HttpHeaders();
        headers.add("User_Token", TokenService.getUserToken(userCredentials, foundUser.getId()));
        ResponseEntity<ApplicationResponse> result =  new ResponseEntity<ApplicationResponse>(response, headers, HttpStatus.OK);

        return result;
    }

    private void validate(ApplicationUser user) {

        if(!applicationUserRepository.findByMobileno(user.getMobileno()).isEmpty())
            throw new MobileNoNotUniqueException("Unique mobile no is required");
        if(!applicationUserRepository.findByEmailId(user.getEmailId()).isEmpty())
            throw new EmailNotUniqueException("Unique Email id is required");
    }

}