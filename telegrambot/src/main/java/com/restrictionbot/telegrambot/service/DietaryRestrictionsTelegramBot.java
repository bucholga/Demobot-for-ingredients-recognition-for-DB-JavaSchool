package com.restrictionbot.telegrambot.service;

import com.restrictionbot.telegrambot.models.UserIngredients;
import lombok.SneakyThrows;
//import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Configurable
public class DietaryRestrictionsTelegramBot extends TelegramLongPollingBot {
    private static final String botUserName = "JavaSchoolTestBot";
    private static final String token = "1973421485:AAHuiDhX63Z42vAxsLTd2WR3N4V3LQftjL4";
    @Override
    public String getBotUsername() {
        return botUserName;
    }
    private List<UserIngredients> users = new ArrayList<UserIngredients>();

    @Override
    public String getBotToken() {
        return token;
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Telegram bot is working");
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            String chatId = update.getMessage().getChatId().toString();
            users.add(new UserIngredients(chatId));

            java.io.File file = downloadPhotoByFilePath(getFilePath(getPhoto(update)));

            BufferedImage img = ImageIO.read(file);
            execute(new SendMessage(chatId, "Analyzing image..."));

            this.execute(this.sendInlineKeyBoardMessage(chatId, igredientsHTTPRequest(file)));
        }
        if(update.hasCallbackQuery()){
            execute(correctIngredient(update.getCallbackQuery().getData().toString(), update.getCallbackQuery().getMessage().getChatId().toString()));

        }

        if (update.hasMessage() && update.getMessage().hasText()){

            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            List<String> textList = List.of(text.split("_"));

            if (textList.size() == 2){
                for (UserIngredients user : users){
                    if (user.getChatId().equals(chatId)){

                        user.fixIngredient(textList.get(1));
                        execute(sendInlineKeyBoardMessage(chatId,user.getEntities()));
                    }
                }

            }
            else
            {
                if (text.equalsIgnoreCase("correct") ){
                    for (UserIngredients user : users){
                        if (user.getChatId().equals(chatId)){
                            List<String> listForRequest = new ArrayList<String>();
                            listForRequest.add(chatId);
                            List<String> newList = Stream.concat(listForRequest.stream(), user.getEntities().stream())
                                    .collect(Collectors.toList());
                            execute(sendIngredientsWithAllergens(analyzeIngredients(newList), chatId));
                        }
                    }
                }
            }
        }

    }
    public PhotoSize getPhoto(Update update) {
        // Check that the update contains a message and the message has a photo
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // When receiving a photo, you usually get different sizes of it
            List<PhotoSize> photos = update.getMessage().getPhoto();

            // We fetch the bigger photo
            return photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
        }

        // Return null if not found
        return null;
    }

    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);
        { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::execute method.
                File file = execute(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }
    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SneakyThrows
    public List<String> igredientsHTTPRequest(java.io.File img){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<java.io.File> request = new HttpEntity<>(img);
        ResponseEntity<List<String>> response = restTemplate
                .exchange("http://parser/parse-image", HttpMethod.POST, request, new ParameterizedTypeReference<List<String>>(){});


        List<String> responseBody = response.getBody();
        return responseBody;

    }

    public SendMessage sendIngredientsWithAllergens(Map<Integer, List<String>> analyzedIngredients, String chatId){
        SendMessage sendMessage = new SendMessage();
        String messageText = "<b>" + "Аллергенные ингредиенты " + analyzedIngredients.get(0) + "<b>";
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    public Map<Integer, List<String>> analyzeIngredients(List<String> ingredients){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<String>> request = new HttpEntity<List<String>>(ingredients);
        ResponseEntity<Map<Integer, List<String>>> response = restTemplate
                .exchange("http://analyzer/analyze-ingredients", HttpMethod.POST, request, new ParameterizedTypeReference<Map<Integer, List<String>>>(){});


        Map<Integer, List<String>> responseBody = response.getBody();
        return responseBody;

    }

    public SendMessage sendInlineKeyBoardMessage(String chatId, List<String> ingredientsInfo) {
        for (UserIngredients user:users){
            if (user.getChatId().equals(chatId)){
                user.setEntities(ingredientsInfo);
            }
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow =new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        int count = 0;
        for (String ingredient : ingredientsInfo) {
            if (count % 3 == 0 && count != 0){
                rowList.add(keyboardButtonsRow);
                keyboardButtonsRow = new ArrayList<>();
            }
            InlineKeyboardButton temp = new InlineKeyboardButton();
            temp.setText(ingredient);
            temp.setCallbackData(ingredient);
            keyboardButtonsRow.add(temp);
            count++;
        }
        if (count < 3){
            rowList.add(keyboardButtonsRow);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Проверьте состав вашего продукта, если все правильно, пришлите сообщение с текстом correct");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    public SendMessage correctIngredient(String incorrectOne, String chatId){
        for (UserIngredients user:users){
            if (user.getChatId().equals(chatId)){
                user.setIncorrectProduct(incorrectOne);
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Введите правильное название продукта в формате correct_названиеПродукта");
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

}
