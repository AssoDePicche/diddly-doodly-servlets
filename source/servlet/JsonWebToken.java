package servlet;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import shared.Json;

public final class JsonWebToken {
  private static String key = "fsjafsakj";
  private static String issuer = "com.assodepicche";
  private static long lifetime = 60 * 60 * 1000L;
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

  public static String decode(String token) {
    try {
      return JWT.require(algorithm).withIssuer(issuer).build().verify(token).getSubject();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public static boolean isExpired(String token) {
    DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);

    Date expiration = decodedJWT.getExpiresAt();

    return expiration.before(new Date());
  }
}
