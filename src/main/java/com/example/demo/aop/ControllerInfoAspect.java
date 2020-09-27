package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.time.Instant;

@Aspect
@Component
public class ControllerInfoAspect {

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
   * Метод записывает ассинхронно в базу переданные параметры.
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
                                              String methodSignature)
  {
    System.out.println("@rc= "+rc); 
    System.out.println("@time= "+time);
    System.out.println("@exception= "+exception); 
    System.out.println("@uri= "+uri);
    System.out.println("@httpMethod= "+httpMethod);
    System.out.println("@classParent= "+classParent);
    System.out.println("@methodSignature= "+methodSignature);
  }

}
