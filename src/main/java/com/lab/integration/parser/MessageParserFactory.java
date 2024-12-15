package com.lab.integration.parser;

import com.lab.integration.model.ParserEntity;
import com.lab.integration.repository.ParserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class MessageParserFactory {

    private final ParserEntityRepository parserEntityRepository ;

    public MessageParserFactory(ParserEntityRepository parserEntityRepository) {
        this.parserEntityRepository = parserEntityRepository;
    }

    public ParserEntity getParser(String parserType) {
        // Load parser configuration from the database
        ParserEntity parserEntity = parserEntityRepository.findByName(parserType);
        if (parserEntity == null) {
            throw new IllegalArgumentException("Unknown parser type: " + parserType);
        }
        return parserEntity;
    }

}