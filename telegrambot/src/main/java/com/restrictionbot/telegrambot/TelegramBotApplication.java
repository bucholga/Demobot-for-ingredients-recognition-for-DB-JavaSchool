package com.restrictionbot.telegrambot;

import com.restrictionbot.telegrambot.service.DietaryRestrictionsTelegramBot;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@EnableDiscoveryClient
@SpringBootApplication
public class TelegramBotApplication {
    @Inject
    private DietaryRestrictionsTelegramBot bot;

    @SneakyThrows
    @PostConstruct
    protected void init() {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
    }
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
