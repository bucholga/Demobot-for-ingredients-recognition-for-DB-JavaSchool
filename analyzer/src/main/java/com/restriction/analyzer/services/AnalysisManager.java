package com.restriction.analyzer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisManager {

    @Autowired
    private Analyser analyser;

    @Autowired
    private PersonalizedAnalyser personalizedAnalyser;

    public Map<Integer, List<String>> analysis(List<String> input, long chatId) {
        Map<Integer, List<String>> result = new HashMap<>();
        Map<String, List<String>> personalizedDietaryRestrictions = personalizedAnalyser.getDietaryRestrictions(input, chatId);
        Map<String, List<String>> dietaryRestrictions = analyser.getDietaryRestrictions(input);
        result.put(2, new ArrayList<>(personalizedDietaryRestrictions.keySet()));
        result.put(1, new ArrayList<>(dietaryRestrictions.keySet()));
        return result;
    }
}
