package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FormDepartament {
    @FXML
    public TextField editText;
    @FXML
    public Button btnSave, btnCancel;
    public int index;

    String str = "";
    String sql = "";

    public void setText(String str, Integer i) {
        this.editText.setText(str);
        this.str = editText.getText();
        this.index = i;
    }

    public String sqlRequest(Integer i) {
        if (i > 0) {
            sql = "UPDATE nauka.departament SET name = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO nauka.departament (name) VALUES (?);";
        }
        return sql;
    }

    public void saveAction(ActionEvent actionEvent) {
        if(editText.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Ошибка");
            errorAlert.setContentText("Заполните поле");
            errorAlert.showAndWait();
        } else {
            DAO dao = new DAO();
            Connection connection = dao.getConnection();
            ObservableList<String> items = FXCollections.observableArrayList();
            PreparedStatement ps = null;
            Statement st = null;
            ResultSet rs = null;
            if(index == 0) {
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, editText.getText());
                    ps.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    st = connection.createStatement();
                    rs = st.executeQuery("SELECT id FROM nauka.departament WHERE name = '" + str + "' ;");
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, editText.getText());
                        ps.setInt(2, id);
                        ps.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FXML/list_departament.fxml"));
                stage = new Stage();
                stage.setTitle("Список департаментов");
                stage.setScene(new Scene(root, 480, 300));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXML/list_departament.fxml"));
            stage = new Stage();
            stage.setTitle("Список департаментов");
            stage.setScene(new Scene(root, 480, 300));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
