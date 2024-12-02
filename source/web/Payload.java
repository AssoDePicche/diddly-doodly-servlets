package com.assodepicche.web;

import com.assodepicche.shared.Json;

import java.time.ZonedDateTime;

import java.util.Date;

public final class Payload<T> {
  public int code;

  public String message;

  public final T payload;

  public final Date date;

  public Payload(int code, String message, T payload, Date date) {
    this.code = code;

    this.message = message;

    this.payload = payload;

    this.date = date;
  }

  public Payload(int code, String message, T payload) {
    this(code, message, payload, Date.from(ZonedDateTime.now().toInstant()));
  }

  public Payload(HttpStatus status, String message, T payload) {
    this(status.code, message, payload);
  }

  public Payload(HttpStatus status, T payload) {
    this(status.code, status.message, payload);
  }

  @Override
  public String toString() {
    return Json.from(this);
  }
}
