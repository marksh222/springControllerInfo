package com.example.demo.examples;

import com.example.demo.SpringControllerInfoApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SqlDialectInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringControllerInfoApplication.class)
public class CustomAnnotationIntegrationTest {
  @Autowired
  private CustomAnnotationIntegration customAnnotationIntegration;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("ConstantConditions")
  @Test
  @Transactional
  public void shouldApplyCustomAnnotation() throws InterruptedException {
    jdbcTemplate.execute("delete from controller_info.request_log");
    customAnnotationIntegration.requestMappingExample();
    Thread.sleep(1000);
    int count = jdbcTemplate.queryForObject("select count(1) from controller_info.request_log", Integer.class);
    Assert.assertEquals(count, 1);
  }
}
