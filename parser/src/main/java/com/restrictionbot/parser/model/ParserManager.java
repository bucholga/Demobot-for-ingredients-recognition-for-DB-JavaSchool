package com.restrictionbot.parser.model;

import db.Dietary.Ingredient.Ingredient;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ParserManager {
    List<Ingredient> getIngredientsFromImage (BufferedImage input);
    //public Map<String, List<String>> analyseImage(telegrambotUI.src.main.java.db.com.dietary.telegrambot.model.TelegramBot bot, String chatId, BufferedImage input);
}
