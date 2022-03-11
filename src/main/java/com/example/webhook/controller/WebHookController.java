package com.example.webhook.controller;

import com.example.webhook.entity.User;
import com.example.webhook.repository.UserRepository;
import com.example.webhook.request_dtos.WebHookRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class WebHookController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  @PostMapping("/webhook")
  public ResponseEntity<String> saveWebHookData(@RequestBody String requestBody) throws Exception {
    System.out.println("WebHook Request: " + requestBody);
    WebHookRequestDto webHookRequestDto = null;
    try {
      webHookRequestDto = objectMapper.readValue(requestBody, WebHookRequestDto.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    if (Objects.isNull(webHookRequestDto)) {
      throw new Exception("Server Error");
    }
    User user = User.builder()
            .name(webHookRequestDto.getPusher().getName())
            .email(webHookRequestDto.getPusher().getEmail())
            .login(webHookRequestDto.getSender().getLogin())
            .build();
    userRepository.save(user);
    return new ResponseEntity<String >(requestBody, HttpStatus.OK);
  }
}
