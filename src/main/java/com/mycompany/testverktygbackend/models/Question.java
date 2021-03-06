package com.mycompany.testverktygbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;

@Entity
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private int questionId;
    private String question;

    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    private Test test;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Response> responses;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QuestionOption> questionOptions;

    public Question() {
    }

    public List<Response> getResponses() {
        return responses;
    }

    public List<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }
    
    public Question(int questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }   

    public void setQuestionOptions(List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }

}
