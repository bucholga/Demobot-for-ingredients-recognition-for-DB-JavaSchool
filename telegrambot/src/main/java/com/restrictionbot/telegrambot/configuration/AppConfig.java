package com.restrictionbot.telegrambot.configuration;

import com.restrictionbot.telegrambot.service.DietaryRestrictionsTelegramBot;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @SneakyThrows
    @Bean
    public DietaryRestrictionsTelegramBot bot(){
        return new DietaryRestrictionsTelegramBot();
    }
}
