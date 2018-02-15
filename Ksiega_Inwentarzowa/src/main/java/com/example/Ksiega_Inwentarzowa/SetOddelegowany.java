/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.*;
import com.example.Ksiega_Inwentarzowa.controller.Controller_Rest;
import com.example.Ksiega_Inwentarzowa.entities.Employee;
import com.example.Ksiega_Inwentarzowa.entities.EmployeeBaza;
import com.example.Ksiega_Inwentarzowa.repositories.EmployeeRepository;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kamil
 */
public class SetOddelegowany extends MyLayout{
    
    //AppUI app;
    ComboBox<String> comboBox;
    Button oddeleguj;
    Button anuluj;
    
    SetOddelegowany(){
        comboBox = new ComboBox<>("Wybierz użytkownika:", new ArrayList<>());
        oddeleguj = new Button("Oddeleguj pracownika");
        oddeleguj.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setOddelegowanyProperty(true, comboBox.getValue(), AppUI.getInstance().employeeRepository);
                Notification message = new Notification("Pracownik oddelegowany!", comboBox.getValue());
                message.show(Page.getCurrent());
            }
        });
        anuluj = new Button("Anuluj oddelegowanie pracownika");
        anuluj.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setOddelegowanyProperty(false, comboBox.getValue(), AppUI.getInstance().employeeRepository);
                Notification message = new Notification("Pracownik już nie jest oddelegowany!", comboBox.getValue());
                message.show(Page.getCurrent());
            }
        });
        this.addComponent(comboBox);
        this.addComponent(oddeleguj);
        this.addComponent(anuluj);
    }
    
    /*List<EmployeeBaza> getEmployeesFromDataBase(EmployeeRepository employeeRepository){
        List<EmployeeBaza> employeeBazaList = employeeRepository.findAll();
        return employeeBazaList;
    }*/
    
    void getEmployeeNames(EmployeeRepository employeeRepository){
        List<EmployeeBaza> employeeBazaList = employeeRepository.findAll();
        List<String> employeeNames = new ArrayList<String>();
        List<Employee> employees = new Controller_Rest().getAllEmployees();
        for(EmployeeBaza employee : employeeBazaList){
            for(Employee e : employees){
                if(e.getEmployeeId().equals(employee.getEmployeeId()) && (czyAdministrator || e.getCellId().equals(loggedUser.getDetails().getCellId()))){
                    employeeNames.add(e.getName());
                    break;
                }
            }
        }
        comboBox.setSelectedItem("");
        comboBox.setItems(employeeNames);
    }
    
    void setOddelegowanyProperty(boolean oddelegowany, String selectedUser, EmployeeRepository employeeRepository){
        List<Employee> employees = new Controller_Rest().getAllEmployees();
        Integer id = null;
        for(Employee e : employees){
            if(e.getName().equals(selectedUser)){
                id = e.getEmployeeId();
                break;
            }
        }
        EmployeeBaza updatedEmployee = new EmployeeBaza();
        updatedEmployee.setEmployeeId(employeeRepository.findOne(id).getEmployeeId());
        updatedEmployee.setOddelegowany(oddelegowany);
        updatedEmployee.setCellId(employeeRepository.findOne(id).getCellId());
        updatedEmployee.setInventoryBazaSet(employeeRepository.findOne(id).getInventoryBazaSet());
        employeeRepository.saveAndFlush(updatedEmployee);
    }
}
