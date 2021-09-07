package com.restrictionbot.telegrambot.models;

import java.util.ArrayList;
import java.util.List;

public class UserIngredients {
    private List<String> entities = new ArrayList<String>();
    private String chatId;
    private String incorrectProduct;

    public UserIngredients(String chatId){
        this.chatId = chatId;
    }

    public void fixIngredient(String correctProduct){
        if (entities.contains(incorrectProduct)){
            int index = entities.indexOf(incorrectProduct);

            entities.set(index, correctProduct);
        }
    }
}
