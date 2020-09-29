package com.feez.twilio.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwilioController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello from Twilio controller";
    }
}
