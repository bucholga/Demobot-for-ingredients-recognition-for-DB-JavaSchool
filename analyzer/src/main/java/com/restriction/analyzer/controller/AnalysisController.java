package com.restriction.analyzer.controller;

import com.restriction.analyzer.services.AnalysisManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysing")
@RequiredArgsConstructor
public class AnalysisController {
    @Autowired
    AnalysisManager analysisManager;

    /*@GetMapping("/analyze-ingredients")
    public Map<Integer, List<String>> analyseIngredients(List<String> input, long chatId) {
        return analysisManager.analysis(input, chatId);
    }*/

    @GetMapping("/sampleget")
    public Map<Integer, List<String>> sampleget() {
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(1, new ArrayList<>(List.of("мясо", "рыба")));
        map.put(2, new ArrayList<>(List.of("орехи", "пшеница")));
        return map;
    }

    @GetMapping("/analyze-ingredients")
    public Map<Integer, List<String>> analyseIngredients(@RequestBody List<String> input) {
        long chatId = Long.parseLong(input.get(0));
        input.remove(0);
        return analysisManager.analysis(input, chatId);
    }
}
