package com.javaweb.course.untils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@UtilityClass
public class JsonParser {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  private ObjectMapper objectMapper;

  public List<String> getList(String json) {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
      List<String> list = new ArrayList<>();
      try {
        list = objectMapper.readValue(json, List.class);
      } catch (IOException e) {
       log.error(e.getMessage());
      }
    return list;
  }

  public String getJson(List<String> myList) {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    String json = "";
    try {
      json = objectMapper.writeValueAsString(myList);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }
}
