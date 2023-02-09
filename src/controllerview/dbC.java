package controllerview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;

import java.io.IOException;
import java.sql.*;

public class dbC {

    @FXML
    private TableView<Person> tableView;
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

    private Connection conn;
    private Statement stmt;

    private ObservableList<Person> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {

        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/personDB");
        stmt = conn.createStatement();
        stmt.execute("CREATE TABLE person(id INT PRIMARY KEY, name VARCHAR(50), wohnort VARCHAR(50))");

        // Hier wird das FXML-File geladen und die TableView und Buttons werden erstellt ...

        refreshData();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPerson.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        wohnortColumn.setCellValueFactory(new PropertyValueFactory<>("wohnort"));
        tableView.setItems(data);
    }

    private void refreshData() {
        data.clear();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String wohnort = rs.getString("wohnort");
                data.add(new Person(id, name, wohnort));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
    }

    private void addPerson(Person person) {
        try {
            stmt.executeUpdate("INSERT INTO person VALUES(" + person.getId() + ", '" + person.getName() + "', '" + person.getWohnort() + "')");
            refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
