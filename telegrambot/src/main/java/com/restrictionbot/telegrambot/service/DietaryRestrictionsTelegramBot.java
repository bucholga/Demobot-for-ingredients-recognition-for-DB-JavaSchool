package com.restrictionbot.telegrambot.service;

import com.restrictionbot.telegrambot.models.UserIngredients;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Configurable
public class DietaryRestrictionsTelegramBot extends TelegramLongPollingBot {
    private static final String botUserName = "JavaSchoolTestBot";
    private static final String token = "1993202006:AAFrRNzZwWHn7HeSF8Pp-4DdBr9MfucZFss";
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
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            String chatId = update.getMessage().getChatId().toString();
            users.add(new UserIngredients(chatId));

            java.io.File file = downloadPhotoByFilePath(getFilePath(getPhoto(update)));

            //BufferedImage img = ImageIO.read(file);
            execute(new SendMessage(chatId, "Analyzing image..."));

            //this.execute(this.sendInlineKeyBoardMessage(chatId, ingredientsFromImage));

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
    public void sendHTTPRequest(BufferedImage img){
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.valueOf(MediaType.MULTIPART_FORM_DATA));
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", img);
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
               .postForEntity("http://analizer/parse-image", requestEntity, String.class);*/
        /*CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://parser/parse-image");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("field1", "image", ContentType.TEXT_PLAIN);

        // This attaches the file to the POST:
        builder.addBinaryBody(
                "file",
                new FileInputStream(img),
                ContentType.APPLICATION_OCTET_STREAM,
                img.getName()
        );

        HttpEntity multipart = (HttpEntity) builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        CloseableHttpResponse responseEntity = (CloseableHttpResponse) response;
        HttpEntity result = responseEntity.getEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> response1 =
                restTemplate.getForEntity(
                        "http://parser/",
                        String[].class);
        String[] ingredients = response1.getBody();*/
        /*RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<BufferedImage>(img);
        ResponseEntity<Foo> response = restTemplate
                .exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        Foo foo = response.getBody();

        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("bar"));*/

    }

}
