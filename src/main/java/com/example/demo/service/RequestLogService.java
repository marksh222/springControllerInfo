package com.example.demo.service;

import com.example.demo.entity.RequestLog;

public interface RequestLogService {
  @SuppressWarnings("UnusedReturnValue")
  RequestLog insertRequestLog(RequestLog requestLog);
}
