package com.example.demo.examples;

import com.example.demo.SpringControllerInfoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringControllerInfoApplication.class)
public class CustomAnnotationIntegrationTest {
  @Autowired
  private CustomAnnotationIntegration customAnnotationIntegration;

  @Test
  public void shouldApplyCustomAnnotation() throws InterruptedException {
    customAnnotationIntegration.requestMappingExample();
    Thread.sleep(2000);
  }
}
