package com.example.loadbalancer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadBalancerController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @GetMapping("/")
    public String getBetterInstance() {
        ServiceInstance serviceInstance = loadBalancer.choose("twilio-microservice");
        return("Best twilio instance to use " + serviceInstance.getUri().toString());
    }
}
