package ru.kolpakov.LearnEnglish.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kolpakov.LearnEnglish.models.Word;
import ru.kolpakov.LearnEnglish.repositories.WordsRepository;
@Component
public class WordUniqueValidator implements Validator {
    private final WordsRepository wordsRepository;

    @Autowired
    public WordUniqueValidator(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Word.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Word word = (Word) target;
        if (wordsRepository.findByName(word.getName()).isPresent()){
            errors.rejectValue("name","","Такое слово уже есть в вашей коллекции");
        }
    }
}
