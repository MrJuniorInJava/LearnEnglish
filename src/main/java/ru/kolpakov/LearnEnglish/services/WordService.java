package ru.kolpakov.LearnEnglish.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolpakov.LearnEnglish.models.Word;
import ru.kolpakov.LearnEnglish.repositories.WordsRepository;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class WordService {
    private final WordsRepository wordsRepository;

    @Autowired
    public WordService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }
    @Transactional
    public void addWord(Word word){
        wordsRepository.save(word);
    }
    @Transactional
    public void deleteWordById(int id){
        wordsRepository.deleteById(id);
    }
    public List<Word> findAll(){
        List<Word> words = wordsRepository.findAll();
        words.sort(Comparator.comparing(Word::getName));
        return  words;
    }
    public Word findWordById(int id){
        return wordsRepository.findById(id).orElse(null);
    }


}

