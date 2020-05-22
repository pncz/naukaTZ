package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.DAO.DAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerDepartament implements Initializable{
    @FXML
    public ListView<String> listView;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnDelete;

    String selectedItem;
    int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DAO dao = new DAO();
        Connection connection = dao.getConnection();
        final String SELECT_DEPARTAMENT = "SELECT name FROM nauka.departament;";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_DEPARTAMENT);
            while (rs.next()) {
                String name = rs.getString(1);
                listView.getItems().add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addDepartament(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("sample\\FXML\\form_departament.fxml"));
        loader.load();
        FormDepartament formDepartament = loader.getController();
        formDepartament.sqlRequest(index);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Добавление");
        stage.show();
        stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }

    public void editDepartament(ActionEvent actionEvent) throws IOException {
        if (!listView.getSelectionModel().getSelectedItem().isEmpty()) {
            selectedItem = listView.getSelectionModel().getSelectedItem();
            index = listView.getSelectionModel().getSelectedIndex()+1;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("sample\\FXML\\form_departament.fxml"));
            loader.load();
            FormDepartament formDepartament = loader.getController();
            formDepartament.setText(selectedItem, index);
            formDepartament.sqlRequest(index);
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование");
            stage.show();
            stage = (Stage) btnEdit.getScene().getWindow();
            stage.close();
        }
    }
}
