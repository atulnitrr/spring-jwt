package com.atul.springbootjpa.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = JwtUtil.class)
//@TestPropertySource(locations = "application-test.properties")
public class JwtUtilTest {

    private JwtUtil jwtUtil = new JwtUtil();

    @Test
    public void test() {
        System.out.println("testing ---> ");
    }

    @Test
    public void testToken() {
        final User user = new User("atul", "pass", Collections.emptyList());
        final String token = jwtUtil.generateToken(user);
        System.out.println(token);
        final String userNameFromToken = jwtUtil.getUserFromToken(token);
        System.out.println(userNameFromToken);
        System.out.print("Issue date --> ");
        System.out.println(jwtUtil.getTokenIssuedDate(token));
        System.out.print("Expore date --> ");
        System.out.println(jwtUtil.getExpirationDateFromToken(token));

    }

    @Test
    public void testUserDetails() {
        final User user = new User("atul", "pass", Collections.emptyList());
        final  String token =  generateToken(user);
        getAllClimsfromToken(token);
    }

    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        final String token = generateToken(claims, userDetails.getUsername());
        return token;
    }


    private String generateToken(final Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5*60*60))
                .signWith(SignatureAlgorithm.HS512, "secret").compact();
    }

    private Claims getAllClimsfromToken(final String token) {
        final Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
        System.out.println(claims);
        claims.getExpiration();
        claims.getSubject();

        return  claims;
    }
}




