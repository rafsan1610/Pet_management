package pet_management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField textF_1; // Username Field
    @FXML
    private TextField textF_2; // Password Field (using TextField instead of PasswordField)
    @FXML
    private Button btn1; // Login Button
    @FXML
    private Button btn2; // Signup Button

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void sign_in(ActionEvent event) {
        String username = textF_1.getText().trim();
        String password = textF_2.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter username and password.");
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null || conn.isClosed()) {
                showAlert(AlertType.ERROR, "Database Error", "Failed to connect to the database.");
                return;
            }

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.setUserName(username);
                Stage stage = (Stage) btn1.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Pet Management Dashboard");
                stage.show();
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Login failed: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void Signup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
            Stage stage = (Stage) btn2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Registration");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to load registration page.");
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
