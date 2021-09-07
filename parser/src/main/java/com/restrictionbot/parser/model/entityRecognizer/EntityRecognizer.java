package com.restrictionbot.parser.model.entityRecognizer;

import db.Dietary.Ingredient.Ingredient;

import java.util.List;

public interface EntityRecognizer {
    public List<String> createEntities(String source);
}
