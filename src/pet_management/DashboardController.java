package pet_management;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML private VBox vbox;
    @FXML private TextField petname;
    @FXML private TextField breed;
    @FXML private TextField type;
    @FXML private TextField age;
    @FXML private TextField gender;
    @FXML private TextField weight;
    @FXML private Label labelname;
    @FXML private Button btn5; // Add
    @FXML private Button btn6; // Delete
    @FXML private Button btn7; // Update
    @FXML private TableView<Pet> tableview;
    @FXML private Button btn8; // Logout

    private ObservableList<Pet> petList = FXCollections.observableArrayList();
    private int selectedPetId = -1;
    @FXML
    private TextField searchfield;
    @FXML
    private Button btn9;

    public void setUserName(String name) {
        labelname.setText("Welcome, " + name);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        tableview.setItems(petList);
        loadPetsFromDB();

        tableview.setOnMouseClicked(event -> {
            Pet selected = tableview.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selectedPetId = selected.getId();
                petname.setText(selected.getName());
                breed.setText(selected.getBreed());
                type.setText(selected.getType());
                age.setText(selected.getAge());
                gender.setText(selected.getGender());
                weight.setText(selected.getWeight());
            }
        });
    }

    private void setupTableColumns() {
        TableColumn<Pet, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pet, String> breedCol = new TableColumn<>("Breed");
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));

        TableColumn<Pet, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Pet, String> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Pet, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Pet, String> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        tableview.getColumns().addAll(nameCol, breedCol, typeCol, ageCol, genderCol, weightCol);
    }

    private void loadPetsFromDB() {
        petList.clear();
        String query = "SELECT * FROM pets";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pet pet = new Pet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("breed"),
                    rs.getString("type"),
                    rs.getString("age"),
                    rs.getString("gender"),
                    rs.getString("weight")
                );
                petList.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Load Error", "Failed to load pets from database.");
        }
    }

    @FXML
    private void added(ActionEvent event) {
        if (petname.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Validation", "Pet name cannot be empty.");
            return;
        }

        String sql = "INSERT INTO pets (name, breed, type, age, gender, weight) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, petname.getText().trim());
            pstmt.setString(2, breed.getText().trim());
            pstmt.setString(3, type.getText().trim());
            pstmt.setString(4, age.getText().trim());
            pstmt.setString(5, gender.getText().trim());
            pstmt.setString(6, weight.getText().trim());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(AlertType.INFORMATION, "Success", "Pet added successfully.");
                loadPetsFromDB();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Insert Error", "Could not add pet.");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        if (selectedPetId == -1) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a pet to delete.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this pet?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String sql = "DELETE FROM pets WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, selectedPetId);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    showAlert(AlertType.INFORMATION, "Deleted", "Pet deleted successfully.");
                    loadPetsFromDB();
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Delete Error", "Could not delete pet.");
            }
        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (selectedPetId == -1) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a pet to update.");
            return;
        }

        String sql = "UPDATE pets SET name = ?, breed = ?, type = ?, age = ?, gender = ?, weight = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, petname.getText().trim());
            pstmt.setString(2, breed.getText().trim());
            pstmt.setString(3, type.getText().trim());
            pstmt.setString(4, age.getText().trim());
            pstmt.setString(5, gender.getText().trim());
            pstmt.setString(6, weight.getText().trim());
            pstmt.setInt(7, selectedPetId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(AlertType.INFORMATION, "Updated", "Pet updated successfully.");
                loadPetsFromDB();
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Update Error", "Could not update pet.");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) btn8.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pet Management - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Logout Error", "Failed to logout.");
        }
    }

    private void clearFields() {
        petname.clear();
        breed.clear();
        type.clear();
        age.clear();
        gender.clear();
        weight.clear();
        selectedPetId = -1;
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void search(ActionEvent event) {
    String searchText = searchfield.getText().trim().toLowerCase();

  
    loadPetsFromDB();

    if (searchText.isEmpty()) {
    
        tableview.setItems(petList);
        return;
    }

    ObservableList<Pet> filteredList = FXCollections.observableArrayList();

    for (Pet pet : petList) {
        if (pet.getName().toLowerCase().contains(searchText)) {
            filteredList.add(pet);
        }
    }

    tableview.setItems(filteredList);
}

}
