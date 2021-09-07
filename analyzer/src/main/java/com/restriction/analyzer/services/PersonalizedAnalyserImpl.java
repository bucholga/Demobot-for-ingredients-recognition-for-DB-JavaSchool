package com.restriction.analyzer.services;

import com.restriction.analyzer.dao.IngredientDAO;
import com.restriction.analyzer.dao.UserDAO;
import com.restriction.analyzer.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalizedAnalyserImpl implements PersonalizedAnalyser {

    @Autowired
    UserDAO userDAO;

    @Autowired
    IngredientDAO ingredientDAO;

    @Override
    public Map<String, List<String>> getDietaryRestrictions(List<String> ingredients, long chatId) {
        Set<Category> set = userDAO.getUser(chatId).getCategories();
        Map<String, List<String>> map = new HashMap<>();
        for (String ingredient : ingredients) {
            Set<Category> categories = ingredientDAO.getIngredient(ingredient).getCategories();
            List<String> collect = categories.stream().filter(set::contains).map(Category::getName).collect(Collectors.toList());
            map.put(ingredient, collect);
        }
        return map;
    }
}
