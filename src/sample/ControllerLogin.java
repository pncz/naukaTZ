package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    public Button tabel;
    @FXML
    public Button listDepartament;
    @FXML
    public Button listEmployee;

    public void tabelAction(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample\\FXML\\tabel.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Табель");
            primaryStage.setScene(new Scene(root, 1366, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void employeeAction(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample\\FXML\\list_employee.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Список сотрудников");
            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void departamentAction(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample\\FXML\\list_departament.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Список департаментов");
            primaryStage.setScene(new Scene(root, 480, 300));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
