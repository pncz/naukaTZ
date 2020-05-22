package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.DAO.DAO;
import sample.entities.Employee;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerEmployee implements Initializable {

    @FXML
    public Button btnAdd, btnEdit;

    @FXML
    public TextField textSearch;

    @FXML
    public TableView<Employee> table;
    public TableColumn<Employee, Integer> idColumn;
    public TableColumn<Employee, String> nameColumn;
    public TableColumn<Employee, String> databornColumn;
    public TableColumn<Employee, String> postColumn;
    public TableColumn<Employee, CheckBox> rwColumn;
    public TableColumn<Employee, String> addressColumn;
    public TableColumn<Employee, Integer> departamentColumn;

    private final ObservableList<Employee> data = FXCollections.observableArrayList();
    Employee employee;

    String selectedItem;
    int index;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DAO dao = new DAO();
        Connection connection = dao.getConnection();
        final String SELECT_EMPLOYEE = "SELECT e.id, e.name, e.databorn, e.post, e.remote_work, e.address, d.name FROM nauka.employees e INNER JOIN nauka.departament d ON e.dep_id = d.id;";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_EMPLOYEE);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String databorn = rs.getString(3);
                String post = rs.getString(4);
                Boolean remoteWork = rs.getBoolean(5);
                String address = rs.getString(6);
                String departament = rs.getString(7);
                employee = new Employee(id, name, databorn, post, remoteWork, address, departament);
                data.addAll(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        databornColumn.setCellValueFactory(new PropertyValueFactory<>("databorn"));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("post"));
        rwColumn.setCellValueFactory(new PropertyValueFactory<>("remoteWork"));
        rwColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Employee, CheckBox> arg0) {
                Employee empl = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(empl.getRemoteWork());



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {

                        empl.setRemoteWork(new_val);

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        departamentColumn.setCellValueFactory(new PropertyValueFactory<>("departament"));

        FilteredList<Employee> filteredList = new FilteredList<Employee>(data, b -> true);

        textSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(employee -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (employee.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (employee.getDepartament().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (employee.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (employee.getPost().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (employee.getDataborn().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
        });
        SortedList<Employee> sortedList = new SortedList<Employee>(filteredList);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    public void addAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("sample\\FXML\\form_employee.fxml"));
        loader.load();
        FormEmployee formEmployee = loader.getController();
        formEmployee.sqlRequest(index);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Добавление");
        stage.show();
        stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    public void editAction(ActionEvent actionEvent) throws IOException {
        if(table.getSelectionModel().getSelectedCells().isEmpty()) {

        } else {
            employee = null;
            employee = table.getSelectionModel().getSelectedItem();
            index = employee.getId();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("sample\\FXML\\form_employee.fxml"));
            loader.load();
            FormEmployee formEmployee = loader.getController();
            formEmployee.setEmployee(employee);
            formEmployee.sqlRequest(index);
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
