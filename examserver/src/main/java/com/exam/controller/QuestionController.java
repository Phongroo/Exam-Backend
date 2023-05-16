package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionService questionService;
    @PostMapping("/")
    public ResponseEntity<?>addQuestion(@RequestBody Question question){
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }
    @PutMapping("/")
    public ResponseEntity<?>updateQuestion(@RequestBody Question question){
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?>getQuestionOfQuiz(@PathVariable("quizId")Long quizId){
//        Quiz quiz=new Quiz();
//        quiz.setqId(quizId);
//        Set<Question>questions= this.questionService.getQuestionOfQuiz(quiz);
//        return ResponseEntity.ok(questions);

        Quiz quiz=this.quizService.getQuiz(quizId);
        Set<Question>questions=quiz.getQuestions();
        List list=new ArrayList(questions);
        if (list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }
        Collections.shuffle(list);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/all/{quizId}")
    public ResponseEntity<?>getQuestionOfQuizAdmin(@PathVariable("quizId")Long quizId){
        Quiz quiz=new Quiz();
        quiz.setqId(quizId);
        Set<Question>questions= this.questionService.getQuestionOfQuiz(quiz);
        return ResponseEntity.ok(questions);
    }


    @GetMapping("/{quesId}")
    public Question getQues(@PathVariable("quesId") Long quesId){
        return this.questionService.getQuestion(quesId);
    }
    @DeleteMapping("/{quesId}")
    public void deleteQues(@PathVariable("quesId") Long quesId){
        this.questionService.deleteQuestion(quesId);
    }
    @PostMapping("/eval-quiz")
    public ResponseEntity<?>evalQuiz(@RequestBody List<Question> questions){
        System.out.println(questions);
      double  marksGot=0;
       int correctAnswers=0;
       int attempted=0;
        for (Question q:questions){
           Question question= this.questionService.get(q.getQuesId());
           if(question.getAnswer().equals(q.getGiveAnswer())){
                correctAnswers++;
                double markSingle=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot+=markSingle;
           }if (q.getGiveAnswer()!=null){
                attempted++;
           }
        };
        Map<String, Object> map =Map.of("marksGot",marksGot,"correctAnswer",correctAnswers,"attempted",attempted);
        return ResponseEntity.ok(map);
    }
}
