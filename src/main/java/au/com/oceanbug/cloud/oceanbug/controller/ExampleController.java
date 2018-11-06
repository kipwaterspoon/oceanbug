package au.com.oceanbug.cloud.oceanbug.controller;

import au.com.oceanbug.cloud.oceanbug.service.SheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController {

    @Autowired
    SheetsService service;

    @RequestMapping("/")
    String home() {
        return service.readSheet();
        //return "Hello World!!!";
    }

//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(ExampleController.class, args);
//    }

}