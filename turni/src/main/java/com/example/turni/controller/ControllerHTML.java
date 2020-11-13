package com.example.turni.controller;


import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.service.HTMLService;
import com.example.turni.service.RealizzaTurni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class ControllerHTML {


    @Autowired
    Controller controller;
    @Autowired
    RealizzaTurni realizzaTurni;
    @Autowired
    HTMLService htmlService;


    @GetMapping("/turni")
    public String getTurniList(@RequestParam(name = "data", required = false, defaultValue = "")
                                       String data, Model model) {

        List<Response> result = new ArrayList<>();

        if (!data.isBlank())
            result.addAll(htmlService.getList(data));
        else {
            StringBuilder today = new StringBuilder();

            today.append(LocalDate.now().getYear());
            today.append("-");
            today.append(LocalDate.now().getMonthValue());
            today.append("-");
            today.append(LocalDate.now().getDayOfMonth());
            result.addAll(htmlService.getList(today.toString()));
        }


        model.addAttribute("lista", result);
        return "turni";
    }


}
