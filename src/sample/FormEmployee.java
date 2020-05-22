package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.DAO;
import sample.entities.Employee;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FormEmployee implements Initializable {

    @FXML
    public TextField textName, textBorn, textAddress, textPost;
    @FXML
    public ChoiceBox boxDepartament;
    @FXML
    public CheckBox boxRemote;
    @FXML
    public Button btnSave, btnCancel;

    Employee employee;
    int index;
    String sql = "";

    public void setEmployee(Employee employee) {
        this.textName.setText(employee.getName());
        this.textBorn.setText(employee.getDataborn());
        this.textPost.setText(employee.getPost());
        this.boxRemote.setSelected(employee.getRemoteWork());
        this.textAddress.setText(employee.getAddress());
    }

    public String sqlRequest(Integer i) {
        this.index = i;
        if (i > 0) {
            sql = "UPDATE nauka.employees SET name = ?, databorn = ?, post = ?, remote_work = ?, address = ?, dep_id = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO nauka.employees (name, databorn, post, remote_work, address, dep_id) VALUES (?,?,?,?,?,?);";
        }
        return sql;
    }


    public void saveAction(ActionEvent actionEvent) {
        if(textName.getText().isEmpty() || textBorn.getText().isEmpty() || textAddress.getText().isEmpty()
                || boxDepartament.getSelectionModel().isEmpty() || textPost.getText().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Ошибка");
            errorAlert.setContentText("Заполните поле");
            errorAlert.showAndWait();
        } else {
            DAO dao = new DAO();
            Connection connection = dao.getConnection();
            PreparedStatement ps = null;
            Statement st = null;
            ResultSet rs = null;
            if(index == 0) {
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, textName.getText());
                    ps.setString(2, textBorn.getText());
                    ps.setString(3, textPost.getText());
                    ps.setBoolean(4, boxRemote.isSelected());
                    ps.setString(5, textAddress.getText());
                    ps.setInt(6,boxDepartament.getSelectionModel().getSelectedIndex()+1);
                    ps.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, textName.getText());
                    ps.setString(2, textBorn.getText());
                    ps.setString(3, textPost.getText());
                    ps.setBoolean(4, boxRemote.isSelected());
                    ps.setString(5, textAddress.getText());
                    ps.setInt(6, boxDepartament.getSelectionModel().getSelectedIndex()+1);
                    ps.setInt(7, index);
                    ps.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("FXML/list_employee.fxml"));
                stage = new Stage();
                stage.setTitle("Список сотрудников");
                stage.setScene(new Scene(root, 700, 400));
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
            root = FXMLLoader.load(getClass().getResource("FXML/list_employee.fxml"));
            stage = new Stage();
            stage.setTitle("Список сотрудников");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DAO dao = new DAO();
        Connection connection = dao.getConnection();
        ObservableList<String> items = FXCollections.observableArrayList();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if(index == 0) {
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery("SELECT name FROM nauka.departament;");
                while (rs.next()) {
                    String name = rs.getString(1);
                    items.add(name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

        }
        boxDepartament.setItems(items);
    }
}
