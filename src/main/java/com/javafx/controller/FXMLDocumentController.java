/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.controller;

import com.javafx.dao.PersonDAO;
import com.javafx.daoImpl.PersonDAOImpl;
import com.javafx.model.Person;
import com.sun.javafx.collections.ElementObservableListDecorator;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author hc
 */
public class FXMLDocumentController implements Initializable {

    PersonDAO personDAO = new PersonDAOImpl();
    static Integer personId = 0;

    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private TextField nickNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker date;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox categoryCombo;

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> personIdColumn;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> nickNameColumn;
    @FXML
    private TableColumn<Person, String> birthDateColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private TableColumn<Person, String> phoneColumn;
    @FXML
    private TableColumn<Person, String> categoryColumn;

    private ObservableList<Person> parsePersonList() {
        ObservableList<Person> ps=personDAO.getAllPersons();
        for(Person p:ps){
            System.out.println("Categiryy "+p.getCategory());
        }
        return personDAO.getAllPersons();

    }

    public void filterProductTable(String query) {

    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        Person person = new Person();
        person.setFirstName(fnameField.getText());
        person.setLastName(lnameField.getText());
        person.setNickName(nickNameField.getText());
        person.setPhoneNumber(phoneField.getText());
        person.setEmailAddress(emailField.getText());
        person.setAddress(addressField.getText());
        person.setCategory(categoryCombo.getSelectionModel().getSelectedItem().toString()); 
        java.util.Date birthDate = java.sql.Date.valueOf(date.getValue());
        person.setBirthDate(birthDate);
        int row = personDAO.addPerson(person);
        if (row > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Data Added Successfully").showAndWait();
            fillPersonTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Occured!").showAndWait();
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        Person person = new Person();
        person.setPersonId(personId);
        person.setFirstName(fnameField.getText());
        person.setLastName(lnameField.getText());
        person.setNickName(nickNameField.getText());
        person.setPhoneNumber(phoneField.getText());
        person.setEmailAddress(emailField.getText());
        person.setAddress(addressField.getText());
        person.setCategory(categoryCombo.getSelectionModel().getSelectedItem().toString());
        java.util.Date birthDate = java.sql.Date.valueOf(date.getValue());
        person.setBirthDate(birthDate);
        int row = personDAO.updatePerson(person);
        if (row > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Data Updated Successfully").showAndWait();
            fillPersonTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Occured!").showAndWait();
        }

    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        int row = personDAO.deletePerson(personId);
        if (row > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Data Deleted Successfully").showAndWait();
            fillPersonTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error Occured!").showAndWait();
        }

    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        fnameField.setText("");
        lnameField.setText("");
        nickNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        date.setValue(null);

    }

    @FXML
    private void handleRowClickAction() {
        personId = tableView.getSelectionModel().getSelectedItem().getPersonId();
        Person person = personDAO.getPersonById(personId);
        fnameField.setText(person.getFirstName());
        lnameField.setText(person.getLastName());
        nickNameField.setText(person.getNickName());
        emailField.setText(person.getEmailAddress());
        phoneField.setText(person.getPhoneNumber());
        addressField.setText(person.getAddress());
        categoryCombo.setValue(person.getCategory()); 
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthDate()));
        date.setValue(localDate);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> categories=FXCollections.observableArrayList("Friend","Family","Work");
        categoryCombo.setItems(categories);
        fillPersonTable();
    }

    public void fillPersonTable() {
        tableView.setItems(parsePersonList());
        personIdColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("personId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        nickNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("nickName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("emailAddress"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("birthDate"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("category"));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Person> filteredData = new FilteredList<>(parsePersonList(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getNickName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getEmailAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (person.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                 else if (person.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Person> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }

}
