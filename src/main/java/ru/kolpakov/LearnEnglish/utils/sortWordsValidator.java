package ru.kolpakov.LearnEnglish.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kolpakov.LearnEnglish.models.Word;

public class sortWordsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Word.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
