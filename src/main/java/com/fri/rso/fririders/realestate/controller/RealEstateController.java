package com.fri.rso.fririders.realestate.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class RealEstateController {

    @RequestMapping(value = "/estates", method = RequestMethod.GET)
    @ResponseBody
    public String getAllEstates(){
        return "{name: 'Janez', surrname: 'Eržen'}";
    }
}
