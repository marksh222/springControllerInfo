package com.example.demo.examples;

import com.example.demo.annotation.ControllerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomAnnotationIntegration {
  @SuppressWarnings("SameReturnValue")
  @RequestMapping(value = "/uriExample", method = RequestMethod.PUT)
  @ControllerInfo
  public String requestMappingExample() {
    return "requestMappingExample";
  }
}
