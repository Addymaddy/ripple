package com.ripple.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.ripple.model.ApplicationUser;

import java.util.Date;

import static com.ripple.model.SecurityConstants.EXPIRATION_TIME;
import static com.ripple.model.SecurityConstants.SECRET;

/**
 * Created by ahmed on 5/10/2019.
 */
public class TokenService {

    public static String getUserToken(ApplicationUser user){
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return token;
    }

    public static boolean validateToken(String userToken) {
        if (userToken != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(userToken)
                    .getBody()
                    .getSubject();
            if (user != null) {
                return true;
            }
            return false;
        }
        return false;
    }
}
