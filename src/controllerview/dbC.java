package controllerview;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class dbC {
    public Button refreshBtn;
    public Button addBtn;
    public ListView listView;

    public static void show(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(dbC.class.getResource("dbC"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            Platform.exit();
        }
    }


}
