package com.restrictionbot.parser.model;

import db.Dietary.Ingredient.Ingredient;
import db.com.dietary.parser.model.entityRecognizer.EntityRecognizer;
import db.com.dietary.parser.model.textReccognizer.TextRecognizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class ParserManagerImpl implements ParserManager{
    @Autowired
    private EntityRecognizer entityRecognizer;
    @Autowired
    private TextRecognizer textRecognizer;
//    @Autowired
//    private AnalysisManager analysisManager;

    public List<Ingredient> getIngredientsFromImage (BufferedImage input) {
        return  entityRecognizer.createEntities(textRecognizer.recognize(input));
    }


}
