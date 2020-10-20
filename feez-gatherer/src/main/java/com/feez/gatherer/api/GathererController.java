package com.feez.gatherer.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RestController
public class GathererController {

    @Autowired
    DiscoveryClient discoveryClient;

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();

    @HystrixCommand(groupKey = "testExecuteAsync", commandKey = "cmdExecuteAsync", fallbackMethod = "serviceUnavalaible")
    @GetMapping("/")
    public String hello(){
        String headerOfSite = "<h1>Feez: Know the way you pay</h1><br />";
        String twilioFees = null;
        String callrFees = null;
        try {
            twilioFees = "TWILIO: " + getPriceFromService("twilio-microservice").get();
            callrFees = "<br />" + "CALLR: " + getPriceFromService("callr-microservice").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return headerOfSite + twilioFees + callrFees;
    }

    public Future<String> getPriceFromService(String microserviceName){
        return executor.submit(() -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(microserviceName);
            ServiceInstance serviceInstance = instances.get(0);
            String hostname = serviceInstance.getHost();
            int port = serviceInstance.getPort();

            RestTemplate restTemplate = new RestTemplate();
            String microserviceAddress = "http://" + hostname + ":" + port;
            ResponseEntity<String> response = restTemplate.getForEntity(microserviceAddress, String.class);
            String responseWeTest = response.getBody();
            return responseWeTest;
        });
    }

    @HystrixCommand(groupKey = "testExecuteAsync", commandKey = "cmdExecuteAsync")
    public String serviceUnavalaible(){
        return "We have troubles to get the prices";
    }


}
