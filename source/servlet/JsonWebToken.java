package servlet;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import shared.Json;

public final class JsonWebToken {
  private static Set<String> expired = new HashSet<>();

  private static String key = "dummykey";

  private static String issuer = "com.assodepicche";

  private static long lifetime = 3600000L;

  private static Algorithm algorithm = Algorithm.HMAC256(key);

  public static String encode(Object object) throws Exception {
    String json = Json.from(object);

    return JWT.create()
        .withSubject(json)
        .withIssuer(issuer)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + lifetime))
        .sign(algorithm);
  }

  public static String decode(String token) throws Exception {
    return JWT.require(algorithm).withIssuer(issuer).build().verify(token).getSubject();
  }

  public static void addExpiration(String token) {
    expired.add(token);
  }

  public static boolean isExpired(String token) {
    return expired.contains(token);
  }
}
