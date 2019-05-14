package com.ripple.service;

import com.ripple.model.UserCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.ripple.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.ripple.model.SecurityConstants.EXPIRATION_TIME;
import static com.ripple.model.SecurityConstants.SECRET;

/**
 * Created by ahmed on 5/10/2019.
 */
@Component
public class TokenService {


    @Value("${com.ripple.token.secret}")
    String SECRET;

    @Value("${com.ripple.token.ttl}")
    String EXPIRATION_TIME;

    public  String getUserToken(UserCredentials user, long userId){
        String token = Jwts.builder()
                .setSubject(user.getUserName() + ","+userId)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return token;
    }

    public  String validateToken(String userToken) {
        if (userToken != null) {
            String token = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(userToken)
                    .getBody()
                    .getSubject();
            if (token != null) {
                String arr[] = token.split(",");
                if(arr.length==2)
                    return arr[1];
                else
                    return null;
            }
        }
        return null;
    }
}
