package com.assodepicche.web;

public enum HttpStatus {
  ACCEPTED(202, "Accepted"),
  BAD_GATEWAY(502, "Bad Gateway"),
  BAD_REQUEST(400, "Bad Request"),
  CONFLICT(409, "Conflict"),
  CONTINUE(100, "Continue"),
  CREATED(201, "Created"),
  EXPECTATION_FAILED(417, "Expectation Failed"),
  FORBIDDEN(403, "Forbidden"),
  FOUND(302, "Found"),
  GATEWAY_TIMEOUT(504, "Gateway Timeout"),
  GONE(410, "Gone"),
  HTTP_VERSION_NOT_SUPPORTED(505, "Http Version Not Supported"),
  INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
  LENGTH_REQUIRED(411, "Length Required"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  MOVED_PERMANENTLY(302, "Moved Permanently"),
  MULTIPLE_CHOICES(300, "Multiple Choices"),
  NO_CONTENT(204, "No Content"),
  NON_AUTHORITATIVE_INFORMATION(203, "Non Authoritative Information"),
  NOT_ACCEPTABLE(406, "Not Acceptable"),
  NOT_FOUND(404, "Not Found"),
  NOT_IMPLEMENTED(501, "Not Implemented"),
  NOT_MODIFIED(304, "Not Modified"),
  OK(200, "OK"),
  PARITAL_CONTENT(206, "Partial Content"),
  PAYMENT_REQUIRED(402, "Payment Required"),
  PRECONDINTION_FAILED(412, "Precondition Failed"),
  PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
  REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
  REQUEST_TIMEOUT(408, "Request Timeout"),
  REQUEST_URI_TOO_LONG(414, "Request URI Too Long"),
  REQUEST_RANGE_NOT_SATISFIABLE(416, "Request Range Not Satisfiable"),
  RESET_CONTENT(205, "Reset Content"),
  SEE_OTHER(303, "See Other"),
  SERVICE_UNAVAILABLE(503, "Service Unavailable"),
  SWITCHING_PROTOCOLS(101, "Switching Protocols"),
  TEMPORARY_REDIRECT(307, "Temporary Redirect"),
  UNAUTHORIZED(401, "Unauthorized"),
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
  USE_PROXY(305, "Use Proxy"),
  UNKNOWN(-1, "UNKNOWN");

  public final int code;

  public final String message;

  HttpStatus(int code, String message) {
    this.code = code;

    this.message = message;
  }

  public static HttpStatus from(int code) {
    for (HttpStatus status : HttpStatus.values()) {
      if (status.code == code) {
        return status;
      }
    }

    return UNKNOWN;
  }
}
