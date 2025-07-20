package pet_management;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController implements Initializable {

    @FXML
    private TextField textF_1;
    @FXML
    private TextField textF_2;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void sign_in(ActionEvent event) {
        String username = textF_1.getText();
        String password = textF_2.getText();

        if(username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter username and password.");
            return;
        }

        if(username.equals("rafsan") && password.equals("1234")) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome " + username + "!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void Signup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml")); 
            Stage stage = (Stage) btn2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
