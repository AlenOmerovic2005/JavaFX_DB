package controllerview;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbC {


        private ObservableList<Person> personData = FXCollections.observableArrayList();

        @FXML
        private TableView<Person> personTable;
        @FXML
        private TableColumn<Person, Integer> idColumn;
        @FXML
        private TableColumn<Person, String> nameColumn;
        @FXML
        private TableColumn<Person, String> wohnortColumn;
        @FXML
        private Button refreshButton;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        wohnortColumn.setCellValueFactory(new PropertyValueFactory<>("wohnort"));

        refreshData();
    }

    @FXML
    private void handleRefresh() {
        refreshData();
    }

    private void refreshData() {
        personData.clear();
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/personDB")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT PERSON.id, name, wohnort FROM person  join ADRESSE A on PERSON.ID = A.ID ");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String wohnort = resultSet.getString("wohnort");
                personData.add(new Person(id, name, wohnort));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        personTable.setItems(personData);
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addV.fxml"));
            Parent root = loader.load();
            addC addPersonController = loader.getController();
            addPersonController.setMainController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPerson(Person person) {
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/personDB")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO person (id, name) VALUES (" + person.getId() + ", '" + person.getName() + "')");
            statement.executeUpdate("INSERT INTO ADRESSE (id, WOHNORT) VALUES (" + person.getId() + ", '" + person.getWohnort() + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}