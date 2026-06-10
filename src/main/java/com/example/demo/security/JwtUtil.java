package com.example.demo.security;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

/*
 * Utilidad para generar y validar tokens JWT usando Nimbus JOSE
 * Compatible con Spring Boot 4
 * @author Valenciano
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        byte[] keyBytes = secret.getBytes();
        // Aseguramos mínimo 32 bytes para HMAC-SHA256
        byte[] paddedKey = new byte[32];
        System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 32));
        return new SecretKeySpec(paddedKey, "HmacSHA256");
    }

    // Genera un token JWT con email y rol del usuario
    public String generateToken(String email, String role) {
        try {
            JWSSigner signer = new MACSigner(getKey());

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(email)
                    .claim("role", role)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expiration))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims
            );

            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (Exception e) {
            throw new RuntimeException("Error generando token JWT", e);
        }
    }

    // Extrae el email (subject) del token
    public String extractEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error extrayendo email del token", e);
        }
    }

    // Extrae el rol del token
    public String extractRole(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return (String) signedJWT.getJWTClaimsSet().getClaim("role");
        } catch (Exception e) {
            return "USER";
        }
    }

    // Valida firma y expiración
    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(getKey());

            // Verificar firma
            if (!signedJWT.verify(verifier)) return false;

            // Verificar expiración
            Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expTime != null && expTime.after(new Date());

        } catch (Exception e) {
            return false;
        }
    }
}