package testverktygfrontend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import testverktygfrontend.logic.Logic;


public class LogInController implements Initializable {
    
    private Logic logic;
    
    @FXML
    private TextField textFieldUserName, textFieldPassword;
    
    @FXML 
    private ImageView imageView;
    
    @FXML private Button btnLogIn;
    
    
    @FXML
    private void handleLogInButton(ActionEvent event) throws IOException {
        
        
        ///HÄr ska nog namnet på anchorPanen stå istället.
        
        //Går till nästa scen
        Parent root = FXMLLoader.load(getClass().getResource("Primary.fxml"));//skapar träd

        Scene s = new Scene(root);//lägger in trädet i ett nytt objekt av klassen Scene
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();//Här hämtar vi den gamla scenen. source=button
        stg.setScene(s);//byter gamla scenen mot den nya
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logic = Logic.getInstance();
        logic.updateList(); // läser in alla listor till logic
    }    
    
}
