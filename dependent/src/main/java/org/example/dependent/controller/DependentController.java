package org.example.dependent.controller;


import org.example.dependent.pojo.Dependent;
import org.example.dependent.pojo.Response;
import org.example.dependent.service.DependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DependentController {

    @Autowired
    DependentService service;

    @GetMapping("/getDependent/{id}")
    public Dependent getDependent(@PathVariable int id) {
        return service.getDependent(id);
    }

    @GetMapping("/getDependents")
    public List<Dependent> getDependents() {
        return service.getDependents();
    }

    @GetMapping("/getAllIds")
    public List<Integer> getAllIds() {
        return service.getAllIds();
    }

    @PostMapping("/postDependent")
    public void postDependent(@RequestBody Dependent dependent) {
        service.postDependent(dependent);
    }

    @GetMapping("/deleteDependent/{id}")
    public void deleteDependent(@PathVariable int id) {
        service.deleteDependentById(id);
    }

    @GetMapping("/getStringDependent/{id}")
    public String getArrayIds(@PathVariable String id) {
        return service.getStringDependent(Integer.parseInt(id));
    }

    @GetMapping("/getResponse/{id}")
    public Response getResponse(@PathVariable int id) {return  service.getResponse(id);}

}
