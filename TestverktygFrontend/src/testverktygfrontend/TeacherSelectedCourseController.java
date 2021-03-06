package testverktygfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
public class TeacherSelectedCourseController implements Initializable {

    private Logic logic;
    private ObservableList<Test> testList;
    private ObservableList<User> usersWithSelectedTest;

    @FXML
    private Label labelCourse;

    @FXML
    private TableView<Test> tableTests;

    @FXML
    private TableView<User> tableStudentTestResult;

    @FXML
    private TableColumn<Test, String> columnTest, columnStart, columnStop;

    @FXML
    private TableColumn<User, String> columnStudent, columnResult;

    @FXML
    private Label labelSelectedTest;

    @FXML
    private void createTestButton(ActionEvent event) throws IOException {
        logic.setSelectedTest(null);

        try {
            URL paneOneUrl = getClass().getResource("CreateTest.fxml");
            AnchorPane paneOne = (AnchorPane) FXMLLoader.load(paneOneUrl);

            BorderPane border = LogInController.getRoot();
            border.setCenter(paneOne);

        } catch (IOException ee) {
            ee.printStackTrace();
        }

    }

    @FXML
    private void updateTestButton(ActionEvent event) throws IOException {
        try {
            logic.setSelectedTest(tableTests.getSelectionModel().selectedItemProperty().get());
            logic.getSelectedTest().getTitle();
            URL paneOneUrl = getClass().getResource("CreateTest.fxml");
            AnchorPane paneOne = (AnchorPane) FXMLLoader.load(paneOneUrl);

            BorderPane border = LogInController.getRoot();
            border.setCenter(paneOne);

        } catch (NullPointerException e) {

        }
    }

    @FXML
    private void deleteTestButton(ActionEvent event) throws IOException {
        try {
            Test test = tableTests.getSelectionModel().selectedItemProperty().get();
            tableTests.getSelectionModel().setSelectionMode(null);

            logic.deleteTest(test.getIdTest());
            logic.getSelectedCourse().deleteTest(test);
            updateTestList();
            usersWithSelectedTest.clear();

        } catch (NullPointerException e) {

        }

    }

    @FXML
    private void handleEditStartTime(CellEditEvent<Test, String> t) {

        Test test = ((Test) t.getTableView().getItems().get(t.getTablePosition().getRow()));

        test.setStartTime(t.getNewValue());

        ((Test) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setStartTime(test.getStartTime());
        
        logic.updateTest(test);

    }

    @FXML
    private void handleEditStopTime(CellEditEvent<Test, String> t) {

        Test test = ((Test) t.getTableView().getItems().get(t.getTablePosition().getRow()));

        test.setEndTime(t.getNewValue());

        ((Test) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).setStartTime(test.getEndTime());
        
        logic.updateTest(test);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logic = Logic.getInstance();
        labelCourse.setText(logic.getSelectedCourse().getName());

        testList = FXCollections.observableArrayList();
        usersWithSelectedTest = FXCollections.observableArrayList();
        updateTestList();

        columnTest.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        columnStop.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        columnTest.setCellFactory(TextFieldTableCell.forTableColumn());
        columnStart.setCellFactory(TextFieldTableCell.forTableColumn());
        columnStop.setCellFactory(TextFieldTableCell.forTableColumn());

        tableTests.setItems(testList);

        columnStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnResult.setCellValueFactory(new PropertyValueFactory<>("currentResult"));
        columnStudent.setCellFactory(TextFieldTableCell.forTableColumn());
        columnResult.setCellFactory(TextFieldTableCell.forTableColumn());

        tableStudentTestResult.setItems(usersWithSelectedTest);

        startListener();

    }

    private void updateTestList() {
        testList.clear();

        logic.getSelectedCourse().getTests().forEach((a) -> {
            testList.add(a);
        });

    }

    private void startListener() {

        //*************** LISTENER *************************
        tableTests.getSelectionModel().selectedItemProperty().addListener((property, oldValue, newValue) -> {

            try {
                labelSelectedTest.setText(newValue.getTitle());
                logic.setSelectedTest(newValue);

                usersWithSelectedTest.clear();
                for (User user : logic.getUsers()) {
                    int studentResponse = 0;
                    for (Course course : user.getCourses()) {
                        if (course.getCourseId() == logic.getSelectedCourse().getCourseId() && user.getUserRole().equals("Student")) {

                            for (Question question : logic.getSelectedTest().getQuestions()) {
                                for (QuestionOption option : question.getQuestionOptions()) {
                                    if (option.isTrueFalse()) {
                                        try {
                                            for (Response response : question.getResponses()) {
                                                if (response.getResponse().equals(option.getQuestionOption()) && response.getUserId() == user.getUserId()) {
                                                    studentResponse++;
                                                }
                                            }
                                        } catch (NullPointerException ex) {
                                        }
                                    }
                                }
                            }

                            String result = studentResponse + "/" + logic.getSelectedTest().getQuestions().size();
                            user.setCurrentResult(result);
                            usersWithSelectedTest.add(user);
                        }
                    }
                }
            } catch (NullPointerException e) {

            }

        });

    }

}
