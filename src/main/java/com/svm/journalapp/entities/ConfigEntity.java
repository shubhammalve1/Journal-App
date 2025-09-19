package com.svm.journalapp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

// We are using Lombok Library of java to automate getters and setters
//so we don't need to add it manually.

@Document(collection = "journal_app_config")
@Data
@NoArgsConstructor

public class ConfigEntity {

    private String key;
    private String value;
}
