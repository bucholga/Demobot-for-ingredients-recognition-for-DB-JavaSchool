package com.restrictionbot.parser.model.entityRecognizer;

import java.util.List;

public interface EntityRecognizer {
    public List<String> createEntities(String source);
}
