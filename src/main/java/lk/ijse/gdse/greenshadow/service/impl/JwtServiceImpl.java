package lk.ijse.gdse.greenshadow.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.gdse.greenshadow.entity.impl.UserEntity;
import lk.ijse.gdse.greenshadow.service.JwtService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${spring.jwtKey}")
    private String secretKey;
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 12;
    @Autowired
    private Mapping mapping;
    @Override
    public String extractUsername(String token) {
        String email = extractClaim(token, Claims::getSubject);
        return email ;
    }

    @Override
    public String generateToken(UserDetails user) {
        return genToken(new HashMap<>(),user);
    }

    private String genToken(Map<String, Object> genClaims, UserDetails user) {
        genClaims.put("role", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("Other"));
        UserEntity userEntity = (UserEntity) user;
        return Jwts.builder()
                .setClaims(genClaims)
                .setSubject(mapping.toUserDTO(userEntity).getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256,getSecretKey()).compact();
    }
    @Override
    public boolean validateToken(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String refreshToken(UserDetails user) {
        return refreshToken(new HashMap<>(),user);
    }
    public <T>T extractClaim(String token, Function<Claims,T>  claimResolver) {
        final Claims claims = getClaims(token);
        return claimResolver.apply(claims);
    }
    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSecretKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }
    private boolean isTokenExpired(String token) {
        Date expiration = getExpiration(token);
        return expiration.before(new Date());
    }
    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private String refreshToken(HashMap<String,Object>claims,UserDetails user) {
        claims.put("role",user.getAuthorities());
        Date now = new Date();
//        Date expiration = new Date(now.getTime() + 1000 * 600);
        Date reFreshExpiration = new Date(now.getTime() + 1000 * 600 * 6);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(reFreshExpiration)
                .signWith(SignatureAlgorithm.HS256,getSecretKey()).compact();

    }
}
