package servlet;

import domain.Book;
import domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

import java.security.Key;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import shared.Json;

public final class Jwt {
  private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static String issuer = "App";
  private static long lifetime = 3600000L;
  private static Map<String, Long> revoked = new HashMap<>();

  public static String encode(User user) {
    try {
      return Jwts.builder()
          .claim("user", Json.from(user))
          .setIssuer(issuer)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + lifetime))
          .signWith(key)
          .compact();
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new RuntimeException("Error serializing user data");
    }
  }

  public static User decode(String token) {
    String data = claimsOf(token).get("user", String.class);

    byte[] bytes = Base64.getDecoder().decode(data);

    String json = new String(bytes, StandardCharsets.UTF_8);

    return Json.from(json, User.class);
  }

  public static boolean isExpired(String token) {
    Long expiration = revoked.get(token);

    return expiration != null && System.currentTimeMillis() < expiration;
  }

  public static void revoke(String token) {
    revoked.put(token, claimsOf(token).getExpiration().getTime());
  }

  private static Claims claimsOf(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .requireIssuer(issuer)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
