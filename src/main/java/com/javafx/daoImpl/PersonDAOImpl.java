/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.javafx.connection.DbConnection;
import com.javafx.dao.PersonDAO;
import com.javafx.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hc
 */
public class PersonDAOImpl implements PersonDAO {

    static Connection con = DbConnection.getConnection();

    @Override
    public Integer addPerson(Person person) {
        int row=0;
        try {
            PreparedStatement stmt = con.prepareStatement("insert into person(first_name,last_name,nick_name,phone_number,address,email_address,birth_date) values(?,?,?,?,?,?,?)");
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setString(3, person.getNickName());
            stmt.setString(4, person.getPhoneNumber());
            stmt.setString(5, person.getAddress());
            stmt.setString(6, person.getEmailAddress());
            stmt.setDate(7, (Date) person.getBirthDate());
            row=stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    @Override
    public Integer updatePerson(Person person) {
        int row=0;
       try {
            PreparedStatement stmt = con.prepareStatement("update person set first_name=?,last_name=?,nick_name=?,phone_number=?,address=?,email_address=?,birth_date=? where person_id=?");
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setString(3, person.getNickName());
            stmt.setString(4, person.getPhoneNumber());
            stmt.setString(5, person.getAddress());
            stmt.setString(6, person.getEmailAddress());
            stmt.setDate(7, (Date) person.getBirthDate());
            stmt.setInt(8, person.getPersonId());
            row=stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    @Override
    public Integer deletePerson(Integer personId) {
        int row=0;
        try {
            PreparedStatement stmt = con.prepareStatement("delete from person where person_id=?");
            stmt.setInt(1, personId);
            row=stmt.executeUpdate();   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    @Override
    public Person getPersonById(Integer personId) {
        Person person=new Person();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from person where person_id=?");
            stmt.setInt(1, personId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                person.setPersonId(resultSet.getInt("person_id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setNickName(resultSet.getString("nick_name"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setAddress(resultSet.getString("address"));
                person.setEmailAddress(resultSet.getString("email_address"));
                person.setBirthDate(resultSet.getDate("birth_date"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return person;
    }

    @Override
    public ObservableList<Person> getAllPersons() {
        ResultSet resultSet = null;
        ObservableList<Person> persons = FXCollections.observableArrayList();
        try {
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery("select * from person");
            while (resultSet.next()) {
                Person person = new Person();
                person.setPersonId(resultSet.getInt("person_id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setNickName(resultSet.getString("nick_name"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setAddress(resultSet.getString("address"));
                person.setEmailAddress(resultSet.getString("email_address"));
                person.setBirthDate(resultSet.getDate("birth_date"));
                persons.add(person);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return persons;
    }

}
