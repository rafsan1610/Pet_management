package pet_management;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    private VBox vbox;
    @FXML
    private TextField petname;
    @FXML
    private TextField breed;
    @FXML
    private TextField type;
    @FXML
    private TextField age;
    @FXML
    private TextField gender;
    @FXML
    private TextField weight;
    @FXML
    private Label labelname;
    @FXML
    private Button btn5; // Add
    @FXML
    private Button btn6; // Delete
    @FXML
    private Button btn7; // Update
    @FXML
    private TableView<Pet> tableview;
    @FXML
    private Button btn8; // Logout

    private ObservableList<Pet> petList = FXCollections.observableArrayList();

    public void setUserName(String name) {
        labelname.setText("Welcome, " + name);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        tableview.setItems(petList);
        tableview.setOnMouseClicked(event -> {
            Pet selected = tableview.getSelectionModel().getSelectedItem();
            if (selected != null) {
                petname.setText(selected.getName());
                breed.setText(selected.getBreed());
                type.setText(selected.getType());
                age.setText(selected.getAge());
                gender.setText(selected.getGender());
                weight.setText(selected.getWeight());
            }
        });
    }

    @FXML
    private void added(ActionEvent event) {
        Pet pet = new Pet(
                petname.getText(),
                breed.getText(),
                type.getText(),
                age.getText(),
                gender.getText(),
                weight.getText()
        );
        petList.add(pet);
        clearFields();
    }

    @FXML
    private void delete(ActionEvent event) {
        Pet selected = tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to delete this pet?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                petList.remove(selected);
                clearFields();
            }
        } else {
            showAlert("Please select a pet to delete.");
        }
    }

    @FXML
    private void update(ActionEvent event) {
        Pet selected = tableview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(petname.getText());
            selected.setBreed(breed.getText());
            selected.setType(type.getText());
            selected.setAge(age.getText());
            selected.setGender(gender.getText());
            selected.setWeight(weight.getText());
            tableview.refresh();
            clearFields();
        } else {
            showAlert("Please select a pet to update.");
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
            showAlert("Logout failed. Try again.");
        }
    }

    private void clearFields() {
        petname.clear();
        breed.clear();
        type.clear();
        age.clear();
        gender.clear();
        weight.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
