package com.example.demo.repository;

import com.example.demo.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public interface CrudRequestLogRepository extends JpaRepository<RequestLog, Long> {
  @SuppressWarnings({"unchecked", "NullableProblems"})
  @Override
  @Transactional
  RequestLog saveAndFlush(RequestLog requestLog);
}
