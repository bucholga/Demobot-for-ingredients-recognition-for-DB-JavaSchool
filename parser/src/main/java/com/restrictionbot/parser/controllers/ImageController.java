package com.restrictionbot.parser.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping("/parser")
@RequiredArgsConstructor
public class ImageController {

    @PostMapping("/parse-image")
    public List<String> parseImage(@RequestBody BufferedImage image){
        System.out.println("ParsingImage");
        return List.of("aaaaaa", "asd");
    }
}