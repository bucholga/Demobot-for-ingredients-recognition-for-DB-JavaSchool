package com.restriction.analyzer.controller;

import com.restriction.analyzer.services.AnalysisManager;
import lombok.AllArgsConstructor;
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
@RequestMapping("/analyzer")
@RequiredArgsConstructor
public class AnalysisController {
    @Autowired
    AnalysisManager analysisManager;

    /*@GetMapping("/analyze-ingredients")
    public Map<Integer, List<String>> analyseIngredients(List<String> input, long chatId) {
        return analysisManager.analysis(input, chatId);
    }*/
    /*@AllArgsConstructor
    class ListWrapper {
        public List<String> mylist;
    }*/

    @GetMapping("/analyze-ingredients")
    public Map<Integer, List<String>> sampleget(ArrayList<String> input) {
        Map<Integer, List<String>> map = new HashMap<>();
        ArrayList<String> objects = new ArrayList<>();
        objects.add("мясо");
        objects.add("рыба");
        map.put(1, objects);
        ArrayList<String> objects2 = new ArrayList<>();
        objects2.add("орехи");
        objects2.add("пшеница");
        map.put(2, objects2);
        System.out.println(map);
        return map;
    }

    @GetMapping("/analyze-ingredients-real")
    public Map<Integer, List<String>> analyseIngredientsReal(List<String> input) {
        long chatId = Long.parseLong(input.get(0));
        input.remove(0);
        return analysisManager.analysis(input, chatId);
    }

    @GetMapping("/analyze-ingredients-mock")
    public Map<Integer, List<String>> analyseIngredientsMock() {
        ArrayList<String> input = new ArrayList<>(List.of("326913874", "мясо", "рыба", "орехи", "пшеница"));
        long chatId = Long.parseLong(input.get(0));
        input.remove(0);
        return analysisManager.analysis(input, chatId);
    }
}
