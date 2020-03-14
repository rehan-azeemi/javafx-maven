/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.testCase;

import static org.junit.Assert.assertEquals;

import com.javafx.dao.PersonDAO;
import com.javafx.daoImpl.PersonDAOImpl;
import com.javafx.model.Person;
import org.junit.Test;

/**
 *
 * @author hc
 */
public class TestCase {
    
    PersonDAO personDAO=new PersonDAOImpl();
    
    @Test
    public void testCaseAdd() {
        Integer result=1;
        System.out.println("Test Case for Add");
        Person person=new Person();
        person.setFirstName("Smith");
        person.setLastName("David"); 
        assertEquals(result, personDAO.addPerson(person));
        System.out.println("Successfully Executed");
    }
    @Test
    public void testCaseUpdate() {
        Integer result=1;
        System.out.println("Test Case for Update");
        Person person=new Person();
        person.setPersonId(2);
        person.setFirstName("John");
        person.setLastName("Deo"); 
        assertEquals(result, personDAO.updatePerson(person));
        System.out.println("Successfully Executed");
    }
    @Test
    public void testCaseDelete() {
        Integer result=1;
        System.out.println("Test Case for Delete");
        Integer personId=1;
        assertEquals(result, personDAO.deletePerson(personId));
        System.out.println("Successfully Executed");
    }
  
    public static void main(String args[]) throws Exception {
        TestCase testCase=new TestCase();
        testCase.testCaseDelete();
        
    }
}
