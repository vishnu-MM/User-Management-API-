package vishnumm.cloude.UserManagementAPI.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vishnumm.cloude.UserManagementAPI.Entity.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "26b507484ca8b01708b28232f66b8cef858b7b34478183aae7564a0bd51c8c4e";

    public String generateToken(User user ) {
        return Jwts
                .builder()
                .setSubject( user.getUsername() )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( new Date( System.currentTimeMillis() + 24*60*60*1000 ) )
                .signWith( getSignInKey() , SignatureAlgorithm.HS256 )
                .compact();
    }

    public boolean isTokenValid( String jwtToken, UserDetails user ) {
        String username = extractUsername(jwtToken);
        boolean isValid = username.equals(user.getUsername()) && !isTokenExpired(jwtToken);
        System.out.println("isTokenValid ? "+isValid);
        return isValid;
    }

    public String extractUsername( String jwtToken ) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpirationTime(jwtToken).before(new Date());
    }

    private Date extractExpirationTime(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

    private <T>T extractClaims( String jwtToken, Function<Claims, T> claimsResolverFunction ) {
        Claims claims =extractAllClaims(jwtToken);
        return claimsResolverFunction.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken ) {
        return Jwts
                .parserBuilder()
                .setSigningKey( getSignInKey() )
                .build()
                .parseClaimsJws( jwtToken )
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode( SECRET_KEY );
        return Keys.hmacShaKeyFor( keyBytes );
    }
}