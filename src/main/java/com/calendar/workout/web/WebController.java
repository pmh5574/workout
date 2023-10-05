package com.calendar.workout.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }



}
