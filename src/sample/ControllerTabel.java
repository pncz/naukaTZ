package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import sample.DAO.DAO;
import sample.entities.Employee;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class ControllerTabel implements Initializable {
    @FXML
    public ListView<String> listView;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> marks = FXCollections.observableArrayList();
    private Employee employee;

    @FXML
    TableView<Employee> januaryTable, februaryTable, marchTable, aprilTable, mayTable, juneTable, julyTable,
            augustTable, septemberTable, octoberTable, novemberTable, decemberTable;
    @FXML
    TabPane tabPane;



    final String INSERT_CALENDAR = "INSERT INTO nauka.calendar (employee_id,data, status_id) VALUES (?,?,?);";
    final String INSERT_TABEL = "INSERT INTO nauka.tabel (employee_id, calendar_id, marks_id) VALUES (?,?,?);";
    final String UPDATE_TABEL = "UPDATE nauka.tabel SET marks_id = ? WHERE employee_id = ? AND calendar_id = ?;";
    final String SELECT_DEPARTAMENT = "SELECT id, name FROM nauka.departament;";
    final String SELECT_MARK = "SELECT id FROM nauka.marks;";

    DAO dao = new DAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = dao.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_DEPARTAMENT);
            while (rs.next()) {
                String name = rs.getString("name");
                items.add(name);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SELECT_MARK);
            while (rs.next()) {
                String id = rs.getString("id");
                marks.add(id);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listView.setItems(items);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                if (selectionModel.isSelected(0)) {
                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=1 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(0, januaryTable, sql);

                } else if (selectionModel.isSelected(1)) {
                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=2 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                   monthChoice(1, februaryTable, sql);

                } else if (selectionModel.isSelected(2)) {
                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=3 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(2, marchTable, sql);

                } else if (selectionModel.isSelected(3)) {
                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=4 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(3, aprilTable, sql);

                } else if (selectionModel.isSelected(4)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=5 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(4, mayTable, sql);

                } else if (selectionModel.isSelected(5)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=6 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(5, juneTable, sql);

                } else if (selectionModel.isSelected(6)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=7 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(6, julyTable, sql);

                } else if (selectionModel.isSelected(7)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=8 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(7, augustTable, sql);

                } else if (selectionModel.isSelected(8)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=9 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(8, septemberTable, sql);

                } else if (selectionModel.isSelected(9)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=10 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(9, octoberTable, sql);

                } else if (selectionModel.isSelected(10)) {

                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=11 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(10, novemberTable, sql);

                } else if (selectionModel.isSelected(11)) {
                    int i = listView.getSelectionModel().getSelectedIndex() + 1;
                    final String sql = "SELECT \n" +
                            "e.id\n" +
                            ",e.name\n" +
                            ",e.post\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=1 and tt.employee_id=e.id),'') as \"1\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=2 and tt.employee_id=e.id),'') as \"2\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=3 and tt.employee_id=e.id),'') as \"3\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=4 and tt.employee_id=e.id),'') as \"4\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=5 and tt.employee_id=e.id),'') as \"5\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=6 and tt.employee_id=e.id),'') as \"6\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=7 and tt.employee_id=e.id),'') as \"7\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=8 and tt.employee_id=e.id),'') as \"8\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=9 and tt.employee_id=e.id),'') as \"9\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=10 and tt.employee_id=e.id),'') as \"10\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=11 and tt.employee_id=e.id),'') as \"11\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=12 and tt.employee_id=e.id),'') as \"12\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=13 and tt.employee_id=e.id),'') as \"13\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=14 and tt.employee_id=e.id),'') as \"14\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=15 and tt.employee_id=e.id),'') as \"15\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=16 and tt.employee_id=e.id),'') as \"16\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=17 and tt.employee_id=e.id),'') as \"17\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=18 and tt.employee_id=e.id),'') as \"18\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=19 and tt.employee_id=e.id),'') as \"19\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=20 and tt.employee_id=e.id),'') as \"20\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=21 and tt.employee_id=e.id),'') as \"21\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=22 and tt.employee_id=e.id),'') as \"22\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=23 and tt.employee_id=e.id),'') as \"23\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=24 and tt.employee_id=e.id),'') as \"24\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=25 and tt.employee_id=e.id),'') as \"25\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=26 and tt.employee_id=e.id),'') as \"26\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=27 and tt.employee_id=e.id),'') as \"27\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=28 and tt.employee_id=e.id),'') as \"28\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=29 and tt.employee_id=e.id),'') as \"29\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=30 and tt.employee_id=e.id),'') as \"30\"\n" +
                            ",coalesce((select max(tt.marks_id) from nauka.tabel tt inner join nauka.calendar cc where tt.calendar_id=cc.id \n" +
                            "and year(cc.data)=2020 and month(cc.data)=12 and day(cc.data)=31 and tt.employee_id=e.id),'') as \"31\"\n" +
                            " FROM nauka.employees e\n" +
                            "WHERE e.dep_id=" + i + ";";
                    monthChoice(11, decemberTable, sql);

                }
            }
        });
    }
    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col;
        return null;
    }
    public void monthChoice(int month, TableView<Employee> tableView, String sql) {
        ObservableList<String> columnNames = FXCollections.observableArrayList();
        ObservableList<Employee> data = FXCollections.observableArrayList();
        Connection connection = dao.getConnection();
        Calendar calendar = new GregorianCalendar(2020, month, 1);
        int days = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        int day = calendar.get(calendar.DAY_OF_WEEK);
        Statement stmt = null;
        ResultSet rs = null;
        tableView.getItems().clear();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPost(rs.getString("post"));
                for (int temp = 1; temp<=days;temp++){
                    columnNames.add(rs.getString(String.valueOf(temp)));
                }
                employee.getDays().addAll(columnNames);
                data.add(employee);
                columnNames.clear();
            }
            tableView.setItems(data);
            TableColumn<Employee,Integer> idColumn = new TableColumn("Табельный номер");
            idColumn.setCellValueFactory(
                    new PropertyValueFactory<>("id"));
            TableColumn<Employee,String> nameColumn = new TableColumn("ФИО");
            nameColumn.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            TableColumn<Employee,String> postColumn = new TableColumn("Должность");
            postColumn.setCellValueFactory(
                    new PropertyValueFactory<>("post"));
            if (getTableColumnByName(tableView, idColumn.getText()) == null) {
                tableView.getColumns().addAll(idColumn, nameColumn, postColumn);
            }
            for (int score = 1; score <= days; score++) {
                final int j = score - 1;
                TableColumn<Employee, String> column = new TableColumn<>(String.valueOf(score));
                column.setStyle(null);
                column.setPrefWidth(30);
                column.setMaxWidth(30);
                column.setMinWidth(30);
                column.setCellValueFactory(((Callback<TableColumn.CellDataFeatures<Employee, String>, ObservableValue<String>>)
                        param -> new SimpleStringProperty(param.getValue().getDays().get(j))));
                column.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), marks));
                check(column, month, score);
                if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
                    column.setStyle("-fx-background-color: tomato;");
                } if (day == 7) {
                    day =0;
                }
                day++;
                column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Employee, String> event) {
                        PreparedStatement ps = null;
                        Statement st = null;
                        ResultSet rs = null;
                        int statusDay;
                        calendar.set(2020, month, Integer.parseInt(column.getText()));
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        int id = 0;
                        if (event.getOldValue().isEmpty()) {
                            if (column.getStyle() == null) {
                                statusDay = 2;
                            } else {
                                statusDay = 1;
                            }
                            try {
                                ps = connection.prepareStatement(INSERT_CALENDAR);
                                ps.setInt(1, tableView.getSelectionModel().getSelectedItem().getId());
                                ps.setString(2, df.format(calendar.getTime()));
                                ps.setInt(3, statusDay);
                                ps.execute();
                                ps.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                st = connection.createStatement();
                                rs = st.executeQuery("SELECT id FROM nauka.calendar WHERE data = '" + df.format(calendar.getTime()) + "' AND employee_id = " + tableView.getSelectionModel().getSelectedItem().getId() + ";");
                                while (rs.next()) {
                                    id = (Integer) rs.getInt(1);
                                }
                                rs.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ps = connection.prepareStatement(INSERT_TABEL);
                                ps.setInt(1, tableView.getSelectionModel().getSelectedItem().getId());
                                ps.setInt(2, id);
                                ps.setString(3, event.getNewValue());
                                ps.execute();
                                ps.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                st = connection.createStatement();
                                rs = st.executeQuery("SELECT c.id FROM nauka.calendar c inner join nauka.tabel t on c.id = t.calendar_id WHERE data = '" + df.format(calendar.getTime()) + "';");
                                while (rs.next()) {
                                    id = (Integer) rs.getInt(1);
                                }
                                rs.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ps = connection.prepareStatement(UPDATE_TABEL);
                                ps.setString(1, event.getNewValue());
                                ps.setInt(2, tableView.getSelectionModel().getSelectedItem().getId());
                                ps.setInt(3, id);
                                ps.executeUpdate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                if (getTableColumnByName(tableView, String.valueOf(days)) == null) {
                    tableView.getColumns().add(column);
                }
                tableView.setEditable(true);
            }
            tableView.setItems(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void check(TableColumn<Employee,String> column, int month, int score) {
        if(month == 0) {
            if (score == 1 || score == 2 || score == 3 || score == 4 || score == 5 || score == 6 || score == 7 || score == 8)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 1) {
            if (score == 24)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 2) {
            if (score == 9 || score == 30 || score == 31)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 3) {
            column.setStyle("-fx-background-color: tomato;");
        } else if(month == 4) {
            if (score == 1 || score == 4 || score == 5 || score == 6 || score == 7 || score == 8 || score == 11)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 5) {
            if (score == 11 || score == 12)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 10) {
            if (score == 3 || score == 4)
                column.setStyle("-fx-background-color: tomato;");
        } else if(month == 11) {
            if (score == 31)
                column.setStyle("-fx-background-color: tomato;");
        }
    }
}
