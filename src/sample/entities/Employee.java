package sample.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.util.ArrayList;

public class Employee {
    private Integer id;
    private String name;
    private SimpleStringProperty databorn;
    private String post;
    private SimpleBooleanProperty remoteWork;
    private SimpleStringProperty address;
    private SimpleStringProperty departament;
    private ObservableList<String> days = FXCollections.observableArrayList();
    private String mark;

    public Employee(){

    }


    public Employee(Integer id, String name, String databorn, String post, Boolean remoteWork,
                    String address, String departament) {
        this.id = id;
        this.name = name;
        this.databorn = new SimpleStringProperty(databorn);
        this.post = post;
        this.remoteWork = new SimpleBooleanProperty(remoteWork);
        this.address = new SimpleStringProperty(address);
        this.departament = new SimpleStringProperty(departament);
    }


    public Employee(String name, String databorn, String post, Boolean remoteWork,
                    String address, String departament) {
        this.name = name;
        this.databorn = new SimpleStringProperty(databorn);
        this.post = post;
        this.remoteWork = new SimpleBooleanProperty(remoteWork);
        this.address = new SimpleStringProperty(address);
        this.departament = new SimpleStringProperty(departament);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDataborn() {
        return databorn.get();
    }

    public void setDataborn(String databorn) {
        this.databorn.set(databorn);
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post=post;
    }

    public Boolean getRemoteWork() {
        return remoteWork.get();
    }

    public void setRemoteWork(Boolean remoteWork) {
        this.remoteWork.set(remoteWork);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDepartament() {
        return departament.get();
    }

    public void setDepartament(String departament) {
        this.departament.set(departament);
    }

    public ObservableList<String> getDays() {
        return days;
    }

    public String getDays(int i) {
        this.mark = days.get(i);
        return mark;
    }

    public void setDays(ObservableList<String> days) {
        this.days = days;
    }
}
