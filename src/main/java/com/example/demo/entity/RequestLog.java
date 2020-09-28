package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@SuppressWarnings({"JpaDataSourceORMInspection", "RedundantSuppression", "unused"})
@Entity
@Table(name = "request_log")
public class RequestLog implements Serializable {
  @Transient
  private final static long PSEVDO_ID = -1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "class_parent")
  private String classParent;

  @Column(name = "method_signature")
  private String methodSignature;

  @Column(name = "time_request")
  private Timestamp timeRequest;

  private String uri;

  @Column(name = "http_method")
  private String httpMethod;

  @Column(name = "return_code")
  private String returnCode;

  private String exception;

  public static RequestLog createEmptyRequestLog() {
    RequestLog rc = new RequestLog();
    rc.setId(PSEVDO_ID);
    return rc;
  }

  public static boolean isEmptyRequestLog(RequestLog requestLog) {
    if (requestLog == null) {
      return true;
    }
    Long id = requestLog.getId();
    if (id == null) {
      return false;
    }
    return (id == PSEVDO_ID);
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClassParent() {
    return classParent;
  }

  public void setClassParent(String classParent) {
    this.classParent = classParent;
  }

  public String getMethodSignature() {
    return methodSignature;
  }

  public void setMethodSignature(String methodSignature) {
    this.methodSignature = methodSignature;
  }

  public Timestamp getTimeRequest() {
    return timeRequest;
  }

  public void setTimeRequest(Timestamp timeRequest) {
    this.timeRequest = timeRequest;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RequestLog that = (RequestLog) o;
    return id.equals(that.id) &&
        Objects.equals(classParent, that.classParent) &&
        Objects.equals(methodSignature, that.methodSignature) &&
        Objects.equals(timeRequest, that.timeRequest) &&
        Objects.equals(uri, that.uri) &&
        Objects.equals(httpMethod, that.httpMethod) &&
        Objects.equals(returnCode, that.returnCode) &&
        Objects.equals(exception, that.exception);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, classParent, methodSignature, timeRequest, uri, httpMethod, returnCode, exception);
  }
}
