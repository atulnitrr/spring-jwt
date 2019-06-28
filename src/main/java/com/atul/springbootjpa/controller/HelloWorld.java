package com.atul.springbootjpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class HelloWorld {

    @GetMapping("/hello")
    public ResponseEntity<?> getHelloWorld() {
        final String hello = "Hello world in spring jpa ";
        return ResponseEntity.ok().body(hello);

    }
 }
