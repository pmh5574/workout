package com.calendar.workout.web;

import com.calendar.workout.common.interceptor.Authenticated;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Authenticated
    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }

}
