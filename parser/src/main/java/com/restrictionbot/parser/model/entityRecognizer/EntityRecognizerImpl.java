package com.restrictionbot.parser.model.entityRecognizer;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityRecognizerImpl implements EntityRecognizer {
    @Override
    public List<String> createEntities(String source) {
        return Arrays.stream(source.split(", ")).
                collect(Collectors.toList());
    }
}
