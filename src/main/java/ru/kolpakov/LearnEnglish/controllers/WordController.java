package ru.kolpakov.LearnEnglish.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kolpakov.LearnEnglish.models.Word;
import ru.kolpakov.LearnEnglish.services.WordService;
import ru.kolpakov.LearnEnglish.utils.WordUniqueValidator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/words")
public class WordController {
    private List<Word> words = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private final WordService wordService;
    private final WordUniqueValidator wordUniqueValidator;

    @Autowired
    public WordController(WordService wordService, WordUniqueValidator wordUniqueValidator) {
        this.wordService = wordService;
        this.wordUniqueValidator = wordUniqueValidator;
    }

    @GetMapping()
    public String getAllWords(Model model) {
        model.addAttribute("words", wordService.findAll());
        return "words/all_words";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam(value = "name", required = false) String name,
                                Model model) {
        model.addAttribute("words", wordService.searchByFirstChars(name));
        return "words/all_words";
    }

    @PostMapping()
    public String sortWords(@RequestParam(value = "sort_type", required = false) String typeSort,
                            Model model) {
        model.addAttribute("words", wordService.sortWords(typeSort));
        return "words/all_words";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("word", new Word());
        return "words/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("word") @Valid Word word,
                      BindingResult bindingResult) {
        wordUniqueValidator.validate(word, bindingResult);
        if (bindingResult.hasErrors()) {
            return "words/add";
        }
        wordService.addWord(word);
        return "redirect:/words";
    }

    @PostMapping("/delete")
    public String deleteWord(@RequestParam("id") int id) {
        wordService.deleteWordById(id);
        return "redirect:/words";
    }

    @GetMapping("/test_preparation")
    public String testPreparationPage(Model model) {
        words = wordService.findAll();
        model.addAttribute("words", words);
        return "words/test_preparation";
    }

    @GetMapping("/test")
    public String showTest(@RequestParam(value = "selectedWords", required = false) List<Integer> selectedWordIds,
                           Model model) {
        List<Word> wordsForTest = new ArrayList<>();
        if (selectedWordIds != null) {
            for (Integer selectedWordId : selectedWordIds) {
                wordsForTest.add(wordService.findWordById(selectedWordId));
            }
            words = wordsForTest;
            Collections.shuffle(words);
        } else {
            return "redirect:/words";
        }
        model.addAttribute("word", words.get(currentQuestionIndex));
        model.addAttribute("correctAnswer", false);
        model.addAttribute("incorrectAnswer", false);
        model.addAttribute("testCompleted", false);
        return "words/test";
    }

    @PostMapping("/test")
    public String submitTest(@RequestParam("translation") String answer, Model model) {

        Word currentWord = words.get(currentQuestionIndex);

        if (answer.equalsIgnoreCase(currentWord.getTranslation())) {
            model.addAttribute("correctAnswer", true);
            currentQuestionIndex++;
        } else {
            model.addAttribute("incorrectAnswer", true);
        }


        if (currentQuestionIndex < words.size()) {
            model.addAttribute("word", words.get(currentQuestionIndex));
            return "words/test";
        } else {
            model.addAttribute("testCompleted", true);
            currentQuestionIndex = 0;
            return "words/test";
        }
    }

}
