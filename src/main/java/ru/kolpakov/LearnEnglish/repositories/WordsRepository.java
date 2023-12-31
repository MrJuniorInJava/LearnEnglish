package ru.kolpakov.LearnEnglish.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolpakov.LearnEnglish.models.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordsRepository extends JpaRepository<Word, Integer> {
     Optional <Word> findByName(String name);
}
