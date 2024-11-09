package com.javeriana.pontimovil.ponti_movil.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Atributos:
    private static final Duration tokenDuration = Duration.ofHours(10);

    // Crear una clave secreta para firmar el token:
    @Value("${jwt.signing-key}")
    private String jwtPrivateKey;

    // Métodos:

    // Extraer el nombre de usuario del token:
    public String extractUserName(String token) { return extractClaim(token, Claims::getSubject); }

    // Generar un token:
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Verificar si el token es válido:
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Extraer una reclamación del token:
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    // Generar un token con reclamaciones adicionales:
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenDuration.toMillis()))
                .signWith(getSigningKey()).compact();
    }

    // Verificar si el token ha expirado:
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraer la fecha de expiración del token:
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraer todas las reclamaciones del token:
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
                .getPayload();
    }

    // Obtener la clave de firma:
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtPrivateKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}