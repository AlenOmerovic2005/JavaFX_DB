package main;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {
    public static void main(String[] args)  {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void interData() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/4AHIT/SYT/Client/JavaFX_DB/DATABASE", "username", "password");

        String query = "INSERT INTO tableName (column1, column2, column3) VALUES (?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);

        pstmt.setString(1, value1);
        pstmt.setString(2, value2);
        pstmt.setString(3, value3);

        pstmt.executeUpdate();
        con.close();


    }
}