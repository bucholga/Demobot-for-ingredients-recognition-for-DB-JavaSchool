package com.restrictionbot.parser.model;


import java.awt.image.BufferedImage;
import java.util.List;

public interface ParserManager {
    List<String> getIngredientsFromImage (java.io.File input);
    //public Map<String, List<String>> analyseImage(telegrambotUI.src.main.java.db.com.dietary.telegrambot.model.TelegramBot bot, String chatId, BufferedImage input);
}
