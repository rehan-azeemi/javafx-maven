/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.dao;

import com.javafx.model.Person;
import javafx.collections.ObservableList;

/**
 *
 * @author hc
 */
public interface PersonDAO {

    public Integer addPerson(Person person);

    public Integer updatePerson(Person person);

    public Integer deletePerson(Integer personId);

    public Person getPersonById(Integer personId);

    public ObservableList<Person> getAllPersons();

}
