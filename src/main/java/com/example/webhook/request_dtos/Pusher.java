package com.example.webhook.request_dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;

@Getter
@Setter
@NoArgsConstructor
public class Pusher {
  private String name;
  private String email;
}
