package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @PostMapping("/")
    public ResponseEntity<?>addQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }
    @PutMapping("/")
    public ResponseEntity<?>updateQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }
    @GetMapping("/")
    public ResponseEntity<?>quizes(){
        return ResponseEntity.ok(this.quizService.getQuizess());
    }
    @GetMapping("/{quizId}")
    public Quiz quiz(@PathVariable("quizId")Long quizId){
        return this.quizService.getQuiz(quizId);
    }
    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable("quizId")Long quizId){
        this.quizService.deleteQuiz(quizId);
    }
    @GetMapping("/category/{cId}")
    public ResponseEntity<?>getQuizzesOfCategory(@PathVariable("cId") Long cId){
        Category category=new Category();
        category.setcId(cId);
        return ResponseEntity.ok(this.quizService.getQuizzesOfCategory(category));
    }
    @GetMapping("/active")
    public List<Quiz>getActiveQuizzes(){
        return this.quizService.getActiveQuiz();
    }
    @GetMapping("/category/active/{cId}")
    public List<Quiz>getAciveQuizOfCategory(@PathVariable("cId")Long cId){
        Category category=new Category();
        category.setcId(cId);
        return this.quizService.getActiveQuizzesOfCategory(category);   
    }
}
