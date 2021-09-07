package com.restrictionbot.parser.controllers;


import com.restrictionbot.parser.model.ParserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ParserManager parserManager;
    @PostMapping("/parse-image")
    public List<String> parseImage(@RequestBody java.io.File file){
        parserManager.getIngredientsFromImage(file);
        System.out.println("ParsingImage");
        return parserManager.getIngredientsFromImage(file);
    }
}