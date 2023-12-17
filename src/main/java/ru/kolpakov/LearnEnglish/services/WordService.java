package ru.kolpakov.LearnEnglish.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolpakov.LearnEnglish.models.Word;
import ru.kolpakov.LearnEnglish.repositories.WordsRepository;
import ru.kolpakov.LearnEnglish.utils.IncorrectSortTypeName;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WordService {
    private final WordsRepository wordsRepository;

    @Autowired
    public WordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Transactional
    public void addWord(Word word) {
        word.setDateOfCreation(LocalDateTime.now());
        wordsRepository.save(word);
    }

    @Transactional
    public void deleteWordById(int id) {
        wordsRepository.deleteById(id);
    }

    public List<Word> sortWords(String typeSort) {
        List<Word> words = wordsRepository.findAll();
        if (typeSort.equals("Name") || typeSort.equals("DateOfCreation")) {
            switch (typeSort) {
                case "Name":
                    words.sort(Comparator.comparing(Word::getName));
                    break;
                case "DateOfCreation":
                    words.sort((o1, o2) -> {
                        if (o1.getDateOfCreation().isBefore(o2.getDateOfCreation())) {
                            return 1;
                        } else if (o1.getDateOfCreation().isAfter(o2.getDateOfCreation())) {
                            return -1;
                        } else {
                            return 0;
                        }
                    });
                    break;
            }
            return words;
        } else {
            throw new IncorrectSortTypeName("Incorrect sort type name " + typeSort);
        }
    }
    public List<Word> searchByFirstChars(String name){
        String lowercaseName = name.toLowerCase();

        boolean isEnglish = lowercaseName.matches(".*[a-zA-Z].*");

        return wordsRepository.findAll().stream()
                .filter(word -> {
                    String wordName = word.getName().toLowerCase();
                    String translation = word.getTranslation().toLowerCase();

                    if (isEnglish) {
                        return wordName.contains(lowercaseName) || translation.contains(lowercaseName);
                    } else {
                        return wordName.contains(lowercaseName);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Word> findAll() {
        List<Word> words = wordsRepository.findAll();
        words.sort((o1, o2) -> {
            if (o1.getDateOfCreation().isBefore(o2.getDateOfCreation())) {
                return 1;
            } else if (o1.getDateOfCreation().isAfter(o2.getDateOfCreation())) {
                return -1;
            } else {
                return 0;
            }
        });
        return words;
    }


}

