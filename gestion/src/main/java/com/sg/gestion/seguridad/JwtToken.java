package com.sg.gestion.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
@Slf4j
@Component
public class JwtToken {

    @Value("${jwt.key}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    public String createTK(String email, List<Integer> roles){
        Date actual = new Date();
        //log.info("Fecha: {}", actual);
        Map<String,Object> claims = new HashMap<>();

        for(Integer rol : roles){
            claims.put(String.valueOf(rol),true);
        }
        //log.info("Pase roles");

        return Jwts.builder().setClaims(claims)
                .setSubject(email)
                .setIssuedAt(actual)
                .setExpiration(sumarHoras(actual))
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuth(String tk){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes()).build()
                    .parseClaimsJws(tk).getBody();

            String email = claims.getSubject();
            List<Integer> roles = new ArrayList<>();

            for(int i = 1; i<6 ;i++){
                if(claims.get(String.valueOf(i)) != null){
                    roles.add(i);
                }
            }
            return new UsernamePasswordAuthenticationToken(email,null,Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }

    public String extractUsername(String tk){
        //log.info("TK2: {}", tk);
        return extractClaim(tk,Claims::getSubject);
    }

    public Date extractExpiration(String tk){
        return extractClaim(tk,Claims::getExpiration);
    }

    public <T> T extractClaim(String tk, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(tk);
        //log.info("Subject: {}", claims.getSubject());
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String tk){
        //log.info("TK3: {}", tk);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(tk).getBody();
    }

    private Boolean isTokenExpired(String tk){
        return extractExpiration(tk).before(new Date());
    }

    public Boolean validateToken(String tk, UserDetails userDetails){
        final String email = extractUsername(tk);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(tk));
    }

    public Date sumarHoras(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, jwtExpirationInMs);  // numero de horas a añadir, o restar en caso de horas<0
        //log.info("EXpired: {}",calendar.getTime());
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
}
