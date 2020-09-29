package com.feez.calculator.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CalculatorController {

    @Autowired
    DiscoveryClient discoveryClient;

    @HystrixCommand(fallbackMethod = "serviceUnavalaible")
    @GetMapping("/")
    public String hello(){
        List<ServiceInstance> instances = discoveryClient.getInstances("twilio-microservice");
        ServiceInstance twilio = instances.get(0);
        String hostname = twilio.getHost();
        int port = twilio.getPort();

        RestTemplate restTemplate = new RestTemplate();
        String twilioMicroserviceAddress = "http://" + hostname + ":" + port;
        ResponseEntity<String> response = restTemplate.getForEntity(twilioMicroserviceAddress, String.class);
        String responseWeTest = response.getBody();

        return responseWeTest;
    }

    public String serviceUnavalaible(){
        return "Sorry, twilio service is dead :(";
    }
}
