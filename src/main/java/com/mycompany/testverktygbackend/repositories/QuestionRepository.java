package com.mycompany.testverktygbackend.repositories;

import com.mycompany.testverktygbackend.models.Question;
import com.mycompany.testverktygbackend.models.QuestionOption;
import com.mycompany.testverktygbackend.models.Response;
import com.mycompany.testverktygbackend.models.Test;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author rille
 */
public class QuestionRepository {
    
    Session session;
    TestRepository testRepository = new TestRepository();

    public QuestionRepository() {
        session = HibernateUtil.getSession();
    }
    
    public Question addQuestion(int testId, Question question){
        
        session.beginTransaction();
        
        Question addQuestion = new Question();
        Test test = new Test();
        addQuestion.setQuestion(question.getQuestion());
        for(Test t : testRepository.getAllTest()){
            if(t.getIdTest()==testId){
                addQuestion.setTest(t);
            }
        }
        
        session.save(addQuestion);
        List<Response> responses=(List<Response>) question.getResponses();
        addQuestion.setResponses(responses);
        List<QuestionOption> questionOptions=(List<QuestionOption>) question.getQuestionOptions();
        addQuestion.setQuestionOptions(questionOptions);
        session.saveOrUpdate(addQuestion);
        
        session.getTransaction().commit();
        
        session.close();
        
        return addQuestion;
    }

    public Question getQuestion(int questionId) {
        
        Question question = (Question) session.get(Question.class, questionId);
        return question;
    }

    public Question updateQuestion(Question question) {
        session.beginTransaction();
        session.update(question);
        session.getTransaction().commit();
        session.close();
        return question;
    }

    public void deleteQuestion(int questionId) {
        session.beginTransaction();
        Question persistentInstance = (Question) session.load(Question.class, questionId);
        session.delete(persistentInstance);
        session.getTransaction().commit();
        session.close();
    }

}
