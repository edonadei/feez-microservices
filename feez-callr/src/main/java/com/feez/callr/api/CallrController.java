package com.feez.callr.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallrController {

    @GetMapping("/")
    public String sayHello() {
        return "SMS TO LOCAL NUMBERS: $ 0.01 to send / $ 0.0055 to receive";
    }
}
