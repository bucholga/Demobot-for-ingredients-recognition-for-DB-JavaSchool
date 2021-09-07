package com.restriction.analyzer.dao;

import com.restriction.analyzer.model.Ingredient;

public interface IngredientDAO {
    Ingredient getIngredient(String name);
}
