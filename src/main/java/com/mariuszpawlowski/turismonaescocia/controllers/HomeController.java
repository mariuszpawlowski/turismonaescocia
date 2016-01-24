package com.mariuszpawlowski.turismonaescocia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mario on 24/01/16.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String index() {
        return "index";
    }
}
