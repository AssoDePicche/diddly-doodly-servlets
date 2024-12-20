package servlet;

import shared.Json;

import java.time.ZonedDateTime;

import java.util.Date;

public final class Payload {
  public final int code;

  public final String message;

  public final Object payload;

  public final Date date;

  public Payload(int code, String message, Object payload, Date date) {
    this.code = code;

    this.message = message;

    this.payload = payload;

    this.date = date;
  }

  public Payload(int code, String message, Object payload) {
    this(code, message, payload, Date.from(ZonedDateTime.now().toInstant()));
  }

  public Payload(HttpStatus status, String message, Object payload) {
    this(status.code, message, payload);
  }

  public Payload(HttpStatus status, Object payload) {
    this(status.code, status.message, payload);
  }

  @Override
  public String toString() {
    return Json.from(this);
  }
}
