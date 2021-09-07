package com.restriction.analyzer.services;
import com.restriction.analyzer.dao.IngredientDAO;
import com.restriction.analyzer.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyserImpl implements Analyser {
    @Autowired
    IngredientDAO ingredientDAO;

    @Override
    public Map<String, List<String>> getDietaryRestrictions(List<String> ingredients) {
        Map<String, List<String>> map = new HashMap<>();
        for (String ingredient : ingredients) {
            map.put(ingredient, ingredientDAO.getIngredient(ingredient).getCategories()
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.toList()));
        }
        return map;
    }
}