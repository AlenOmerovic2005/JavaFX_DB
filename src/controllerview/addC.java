package controllerview;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Person;

public class addC {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField wohnortField;

    private dbC mainController;

    public void setMainController(dbC mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleOK() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String wohnort = wohnortField.getText();
        mainController.addPerson(new Person(id, name, wohnort));
        handleCancel();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
