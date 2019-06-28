package com.atul.springbootjpa.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 *
 */
@Component
public class JwtUtil implements Serializable  {

    private static final long serialVersionUID = -2550185165626007488L;
    // max allowed date is 24 check for this.
    private static final long JWT_TOKEN_VALIDITY = 24*24*60*60*1000;
//
    @Value("${jwt.secret}")
    private String jwtSecret ="secret";

//    @Value("${jwt.secret}")
//    private String jwtSecret;

    /**
     *
     * @param userDetails
     * @return
     */
    public String generateToken(final UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        final String token = generateToken(claims, userDetails.getUsername());

        return token;
    }

    /**
     *
     * @param claims
     * @param subject
     * @return
     */
    private String generateToken(final Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims getALlClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    /**
     *
     * @param token
     * @return
     */
    public String getUserFromToken(final String token) {
        final Claims claims = getALlClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(final String token) {
        return getALlClaimsFromToken(token).getExpiration();
    }

    /**
     *
     * @param token
     * @return
     */
    public Date getTokenIssuedDate(final String token) {
        return getALlClaimsFromToken(token).getIssuedAt();
    }

    /**
     *
     * @param token
     * @return
     */
    public boolean isValidToken(final String token) {
        return getExpirationDateFromToken(token).after(new Date());
    }

    /**
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isValidUser(final String token, final UserDetails userDetails) {
        return getUserFromToken(token).equals(userDetails.getUsername()) && isValidToken(token);
    }




}
