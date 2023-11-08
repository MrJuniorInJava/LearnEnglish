package ru.kolpakov.LearnEnglish.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kolpakov.LearnEnglish.models.Word;
import ru.kolpakov.LearnEnglish.services.WordService;

@Controller
@RequestMapping("/words")
public class WordController {
    private final WordService wordService;
@Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping()
    public String getAllWords(Model model){
    model.addAttribute("words",wordService.findAll());
        return "words/all_words";
    }
    @GetMapping("/add")
    public String addPage(Model model){
        model.addAttribute("word", new Word());
        return "words/add";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute("word") @Valid Word word){
        wordService.addWord(word);
        return "redirect:/words";
    }
    @DeleteMapping("/{id}")
    public String deleteWord(@PathVariable("id") int id){
        wordService.deleteWordById(id);
        return "redirect:/words";
    }

}
