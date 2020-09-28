package com.example.demo.service;

import com.example.demo.entity.RequestLog;
import com.example.demo.repository.RequestLogRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestLogServiceImpl implements RequestLogService {


  private final RequestLogRepository requestLogRepository;

  public RequestLogServiceImpl(RequestLogRepository requestLogRepository) {
    this.requestLogRepository = requestLogRepository;
  }

  @Override
  public RequestLog insertRequestLog(RequestLog requestLog) {
    return requestLogRepository.insertRequestLog(requestLog);
  }
}
