package com.svm.journalapp.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;

    private String password;

    private String email;

    private boolean sentimentAnalysis;
}
