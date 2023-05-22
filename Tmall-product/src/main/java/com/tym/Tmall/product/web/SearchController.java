package com.tym.Tmall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/list")
    public String listPage(){
        return "search";
    }
}
