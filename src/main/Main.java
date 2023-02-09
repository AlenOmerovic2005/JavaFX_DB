import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Person;

import java.sql.*;

public class main extends Application {
    private Connection conn;
    private Statement stmt;
    private ObservableList<Person> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/personDB");
        stmt = conn.createStatement();
        stmt.execute("CREATE TABLE person(id INT PRIMARY KEY, name VARCHAR(50), wohnort VARCHAR(50))");

        // Hier wird das FXML-File geladen und die TableView und Buttons werden erstellt ...

        refreshData();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Hier wird das Add-Fenster geöffnet ...
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });
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
