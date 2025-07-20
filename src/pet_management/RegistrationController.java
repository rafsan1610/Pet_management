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

public class RegistrationController implements Initializable {

    @FXML
    private TextField testF_3;
    @FXML
    private TextField testF_4;
    @FXML
    private TextField testF_5;
    @FXML
    private TextField testF_6;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void SignUpp(ActionEvent event) {
        String username = testF_5.getText();
        String password = testF_6.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error", "Username and password cannot be empty.");
            return;
        }

        showAlert(AlertType.INFORMATION, "Registration Successful", "User '" + username + "' has been registered.");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) btn3.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backsignin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) btn4.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
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
