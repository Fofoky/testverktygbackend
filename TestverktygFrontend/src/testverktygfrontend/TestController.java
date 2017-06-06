/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

import testverktygfrontend.logic.Logic;
import testverktygfrontend.model.Course;
import testverktygfrontend.model.Question;
import testverktygfrontend.model.QuestionOption;
import testverktygfrontend.model.Response;
import testverktygfrontend.model.Test;
import testverktygfrontend.model.User;

/**
 * FXML Controller class
 *
 * @author annafock
 */
public class TestController implements Initializable {

    private Logic logic;
    private Test selectedTest;
    private User selectedUser;
    private Question selectedQuestion;
    private Course selectedCourse;
    private List<Question> questionList;
    private ObservableList<QuestionOption> questionOptionList;
    private List<QuestionOption> savedAnswers;
    private IntegerProperty counter;
    private boolean ended = false;
    
    
    

    @FXML
    Label labelTestName, labelQuestionId, labelProgress;

    @FXML
    TextArea textAreaQuestion;

    @FXML
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;

    //Previous och next, disabled vid start och slut. SaveTest blir visible vid sista frågan. 
    @FXML
    Button buttonPrevious, buttonNext, buttonSaveTest;

    @FXML
    ProgressBar progressBar; //Går att koppla till property med bind

    public void handleButtonNextAction(ActionEvent event) {
        buttonPrevious.setDisable(false);
        counter.set(counter.getValue() + 1);
        
        checkBox1.setDisable(false);
        checkBox2.setDisable(false);
        checkBox3.setDisable(false);
        checkBox4.setDisable(false);
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBox4.setSelected(false);

    }

    public void handleButtonSaveTestAction(ActionEvent event) throws IOException {
        
        Response addedResponse;
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekräfta ditt val.");
        alert.setContentText("Är du säker på att du vill spara och avsluta testet?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            
            for (QuestionOption q : savedAnswers) {

                addedResponse = logic.addResponse(q, selectedUser.getUserId(), q.getQuestion().getQuestionId());
                q.getQuestion().setResponse(addedResponse);

            }
            
            URL studentCourse = getClass().getResource("StudSelectedCourse.fxml");
            LogInController.getRoot().setCenter(FXMLLoader.load(studentCourse));
            
        } else {

        }

    }

    public void handleButtonBackAction(ActionEvent event) {
        counter.set(counter.getValue() - 1);
        
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBox4.setSelected(false);
        
        if (questionList.get(0) == selectedQuestion) {
            buttonPrevious.setDisable(true);
        }
    }

     ChangeListener checkIfChecked1 = new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue == true) {
                checkBox2.setDisable(true);
                checkBox3.setDisable(true);
                checkBox4.setDisable(true);
                buttonNext.setDisable(false);
                
                
                
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == true) {
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(false);
                }
                
                
                savedAnswers.add((QuestionOption) checkBox1.getUserData());
                savedAnswers.get(counter.getValue()).setQuestion(selectedQuestion);
                

            } else {
                checkBox2.setDisable(false);
                checkBox3.setDisable(false);
                checkBox4.setDisable(false);
                buttonNext.setDisable(true);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == false){
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(true);
                }
                

                savedAnswers.removeIf(s -> s == checkBox1.getUserData());
                
            }

        }

    };

    ChangeListener checkIfChecked2 = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue == true) {
                checkBox1.setDisable(true);
                checkBox3.setDisable(true);
                checkBox4.setDisable(true);
                buttonNext.setDisable(false);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == true) {
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(false);
                }
                
                savedAnswers.add((QuestionOption) checkBox2.getUserData());
                savedAnswers.get(counter.getValue()).setQuestion(selectedQuestion);
   
            } else {
                checkBox1.setDisable(false);
                checkBox3.setDisable(false);
                checkBox4.setDisable(false);
                buttonNext.setDisable(true);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == false){
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(true);
                }

                savedAnswers.removeIf(s -> s == checkBox2.getUserData());
            }

        }

    };

    ChangeListener checkIfChecked3 = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue == true) {
                checkBox2.setDisable(true);
                checkBox1.setDisable(true);
                checkBox4.setDisable(true);
                buttonNext.setDisable(false);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == true) {
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(false);
                }
                

                savedAnswers.add((QuestionOption) checkBox3.getUserData());
                savedAnswers.get(counter.getValue()).setQuestion(selectedQuestion);
                
            } else {
                checkBox2.setDisable(false);
                checkBox1.setDisable(false);
                checkBox4.setDisable(false);
                buttonNext.setDisable(true);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == false){
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(true);
                }

                savedAnswers.removeIf(s -> s == checkBox3.getUserData());
            }

        }

    };

    ChangeListener checkIfChecked4 = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue == true) {
                checkBox2.setDisable(true);
                checkBox3.setDisable(true);
                checkBox1.setDisable(true);
                buttonNext.setDisable(false);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == true) {
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(false);
                }
                
                savedAnswers.add((QuestionOption) checkBox4.getUserData());
                savedAnswers.get(counter.getValue()).setQuestion(selectedQuestion);
                
            } else {
                checkBox2.setDisable(false);
                checkBox3.setDisable(false);
                checkBox1.setDisable(false);
                buttonNext.setDisable(true);
                
                if(selectedQuestion == questionList.get(questionList.size()-1) && newValue == false){
                    buttonNext.setDisable(true);
                    buttonSaveTest.setDisable(true);
                }

                savedAnswers.removeIf(s -> s == checkBox4.getUserData());
            }

        }

    };

    /*
    Lyssnar efter ändringar från IntegerProperty 'counter'. Counter bestämmer
    de värden som skall sättas på testsidan.
    */
    ChangeListener changeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

            selectedQuestion = selectedTest.getQuestions().get((int) newValue);
            textAreaQuestion.setText(selectedQuestion.getQuestion());
            labelTestName.setText(selectedTest.getTitle());
            labelQuestionId.setText(String.valueOf(selectedQuestion.getQuestionId()));
            labelProgress.setText(String.valueOf(selectedQuestion.getQuestionId()) + " / " + questionList.size());
            checkBox1.setText(selectedQuestion.getQuestionOptions().get(0).getQuestionOption());
            checkBox2.setText(selectedQuestion.getQuestionOptions().get(1).getQuestionOption());
            checkBox3.setText(selectedQuestion.getQuestionOptions().get(2).getQuestionOption());
            checkBox4.setText(selectedQuestion.getQuestionOptions().get(3).getQuestionOption());
            checkBox1.setUserData(selectedQuestion.getQuestionOptions().get(0));
            checkBox2.setUserData(selectedQuestion.getQuestionOptions().get(1));
            checkBox3.setUserData(selectedQuestion.getQuestionOptions().get(2));
            checkBox4.setUserData(selectedQuestion.getQuestionOptions().get(3));

        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logic = Logic.getInstance();
        selectedTest = logic.getSelectedTest();
        selectedUser = logic.getSelectedUser();
        selectedCourse = logic.getSelectedCourse();
        savedAnswers = new ArrayList();
        counter = new SimpleIntegerProperty();
        buttonNext.setDisable(true);
        buttonPrevious.setDisable(true);
        buttonSaveTest.setDisable(true);
        questionList = new ArrayList();
        questionOptionList = FXCollections.observableArrayList();

        // Sparar de questions som finns för ett test i en ny lista.
        selectedTest.getQuestions().forEach((a) -> {
            questionList.add(a);

        });
        // Sparar de questionOptions som finns i en mer lättåtkomlig lista.
        questionList.forEach((a) -> {
            a.getQuestionOptions().forEach((b) -> {
                questionOptionList.add(b);
            });
        });

        /*
        Här sätts alla värden som behövs när testet startar från första början.
        Därefter sätts alla värden igenom ChangeListener 'changed', som lyssnar av efter ett värde
        från IntegerProperty 'counter'. Counter ändras då man trycker på framåt eller bakåt knappen i testet.
         */
        selectedQuestion = selectedTest.getQuestions().get(0);
        textAreaQuestion.setText(selectedQuestion.getQuestion());
        labelTestName.setText(selectedTest.getTitle());
        labelQuestionId.setText(String.valueOf(selectedQuestion.getQuestionId()));
        labelProgress.setText(String.valueOf(selectedQuestion.getQuestionId()) + " / " + questionList.size());
        checkBox1.setText(selectedQuestion.getQuestionOptions().get(0).getQuestionOption());
        checkBox2.setText(selectedQuestion.getQuestionOptions().get(1).getQuestionOption());
        checkBox3.setText(selectedQuestion.getQuestionOptions().get(2).getQuestionOption());
        checkBox4.setText(selectedQuestion.getQuestionOptions().get(3).getQuestionOption());
        checkBox1.setUserData(selectedQuestion.getQuestionOptions().get(0));
        checkBox2.setUserData(selectedQuestion.getQuestionOptions().get(1));
        checkBox3.setUserData(selectedQuestion.getQuestionOptions().get(2));
        checkBox4.setUserData(selectedQuestion.getQuestionOptions().get(3));

        /* 
        Alla allListeners som finns: 
        För progressbar som håller koll på vilka frågor man gjort. 
        För counter som räknar alla frågor som man gör. 
        Sen för alla checkboxar, som ska sätta/ta bort från en lokal lista när man klickar på de olika valen som finns.
         */
        progressBar.progressProperty().bind(counter.divide(questionList.size() * 1.0));
        counter.addListener(changeListener);
        checkBox1.selectedProperty().addListener(checkIfChecked1);
        checkBox2.selectedProperty().addListener(checkIfChecked2);
        checkBox3.selectedProperty().addListener(checkIfChecked3);
        checkBox4.selectedProperty().addListener(checkIfChecked4);

    }

}
