package ru.kolpakov.LearnEnglish.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "Word")
public class Word {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Поле не должено быть пустым")
    @Pattern(regexp = "[а-яА-Я ]+$", message = "Слово должно содержать только русские буквы")
    private String name;
    @Column(name = "translation")
    @NotEmpty(message = "Поле не должено быть пустым")
    @Pattern(regexp = "[a-zA-Z ]+$", message = "Перевод должен содержать только буквы английского алфавита")
    private String translation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
