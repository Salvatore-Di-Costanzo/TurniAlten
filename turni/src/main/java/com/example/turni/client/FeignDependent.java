package com.example.turni.client;


import com.example.turni.pojo.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@Service
@FeignClient(name = "dependent-service",url ="http://localhost:8080")
public interface FeignDependent {

    @GetMapping("/getAllIds")
    public List<Integer> getIds();

    @GetMapping("/getStringDependent/{id}")
    public String getDateDependent(@PathVariable String id);

    @GetMapping("/getResponse/{id}")
    public Response getResponse(@PathVariable int id);
}
