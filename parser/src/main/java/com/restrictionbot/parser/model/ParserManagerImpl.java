package com.restrictionbot.parser.model;

import com.restrictionbot.parser.model.entityRecognizer.EntityRecognizer;
import com.restrictionbot.parser.model.textReccognizer.TextRecognizer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
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

    @SneakyThrows
    public List<String> getIngredientsFromImage (java.io.File  input) {
        BufferedImage img = ImageIO.read(input);
        return  entityRecognizer.createEntities(textRecognizer.recognize(img));
    }


}
