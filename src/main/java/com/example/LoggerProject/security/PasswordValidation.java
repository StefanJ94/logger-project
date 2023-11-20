package com.example.LoggerProject.security;

import org.passay.*;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidation {

    public final PasswordValidator passwordValidator;

    public PasswordValidation() {
         passwordValidator = new PasswordValidator(new LengthRule(8, 16),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Alphabetical, 1),
                new WhitespaceRule());
    }

    public RuleResult validate(String password) {
        final char[] charArrayPassword = password.toCharArray();
        return passwordValidator.validate(new PasswordData(new String(charArrayPassword)));
    }

}
