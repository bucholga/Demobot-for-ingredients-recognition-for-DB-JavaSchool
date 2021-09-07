package com.restriction.analyzer.controller;

import com.restriction.analyzer.services.AnalysisManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analysing")
@RequiredArgsConstructor
public class AnalysisController {
    @Autowired
    AnalysisManager analysisManager;

    @GetMapping("/analyze-ingredients")
    public Map<Integer, List<String>> analyseIngredients(List<String> input, long chatId) {
        return analysisManager.analysis(input, chatId);
    }
}
