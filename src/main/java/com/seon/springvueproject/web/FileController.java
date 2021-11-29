package com.seon.springvueproject.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileController {
    private static final String BASE_DIR = System.getProperty("user.dir") + "/src/main/resources/static/files";

    @GetMapping("/api/file/{id}")
    public ResponseEntity<Object> download(@PathVariable("id") Long id){

    }
}
