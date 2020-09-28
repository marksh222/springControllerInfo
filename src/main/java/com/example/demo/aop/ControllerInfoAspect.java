package com.example.demo.aop;

import com.example.demo.entity.RequestLog;
import com.example.demo.service.RequestLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;

@Aspect
@Component
public class ControllerInfoAspect {

  private static final boolean NON_DURABLE = false;
  public static final String MY_QUEUE_NAME = "myQueue";

  private final RequestLogService requestLogService;
  private final RabbitTemplate rabbitTemplate;

  public ControllerInfoAspect(RequestLogService requestLogService, RabbitTemplate rabbitTemplate)
  {
    this.requestLogService = requestLogService;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Around("@annotation(com.example.demo.annotation.ControllerInfo)")
  public Object controllerInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    Object rc = null;
    long time = Instant.now().toEpochMilli();
    String exception = null;
    String uri = null;
    String httpMethod = null;
    String classParent = null;
    String methodSignature;

    Signature signature = joinPoint.getSignature();
    methodSignature = signature.toString();
    if (signature instanceof MethodSignature) {
      Method method = ((MethodSignature)signature).getMethod();
      classParent = method.getDeclaringClass().getName();
      RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
      if (requestMapping == null) {
        exception = "Аннотация ControllerInfo используется без @RequestMapping";
      }
      else {
        String[] value = requestMapping.value();
        if (value.length > 0) {
          uri = value[0];
        }
        RequestMethod[] methods = requestMapping.method();
        if (methods.length > 0) {
          httpMethod = methods[0].name();
        }
      }
    }
    else {
      exception = "Аннотация ControllerInfo используется не для метода класса";
    }

    try {
      rc = joinPoint.proceed();
    }
    catch(Throwable ex) {
      exception = ex.getMessage();
      throw ex;
    }
    finally {
      controllerInfoCustomProcessing(rc, time, exception, uri, httpMethod, classParent, methodSignature);
    }
    return rc;
  }

  /**
   * Создает объект - сущность RequestLog и ставит ее в очередь на запись в базу.
   * В качестве очереди используется rabbitmq.
   *
   * @param rc
   * @param time
   * @param exception
   * @param uri
   * @param httpMethod
   * @param classParent
   * @param methodSignature
   */
  private void controllerInfoCustomProcessing(Object rc,
                                              long time,
                                              String exception,
                                              String uri,
                                              String httpMethod,
                                              String classParent,
                                              String methodSignature) {

    RequestLog requestLog = new RequestLog();
    requestLog.setReturnCode(rc == null ? null : rc.toString());
    requestLog.setTimeRequest(new Timestamp(time));
    requestLog.setException(exception);
    requestLog.setUri(uri);
    requestLog.setHttpMethod(httpMethod);
    requestLog.setClassParent(classParent);
    requestLog.setMethodSignature(methodSignature);

    rabbitTemplate.convertAndSend(MY_QUEUE_NAME, requestLog);
  }
  // ------------ RabbitMq -------
  @Bean
  public ApplicationRunner runner(RabbitTemplate template) {
    return args -> template.convertAndSend("myQueue", RequestLog.createEmptyRequestLog());
  }

  @Bean
  public Queue myQueue() {
    return new Queue(MY_QUEUE_NAME, NON_DURABLE);
  }

  @RabbitListener(queues = MY_QUEUE_NAME)
  public void listen(RequestLog in) {
    if (!RequestLog.isEmptyRequestLog(in)) {
      requestLogService.insertRequestLog(in);
    }
  }

}
