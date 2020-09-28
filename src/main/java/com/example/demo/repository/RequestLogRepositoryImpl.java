package com.example.demo.repository;

import com.example.demo.entity.RequestLog;
import org.springframework.stereotype.Repository;

@Repository
public class RequestLogRepositoryImpl implements RequestLogRepository {

  private final CrudRequestLogRepository crudRequestLogRepository;

  public RequestLogRepositoryImpl(CrudRequestLogRepository crudRequestLogRepository) {
    this.crudRequestLogRepository = crudRequestLogRepository;
  }

  @Override
  public RequestLog insertRequestLog(RequestLog requestLog) {
    return crudRequestLogRepository.saveAndFlush(requestLog);
  }
}
