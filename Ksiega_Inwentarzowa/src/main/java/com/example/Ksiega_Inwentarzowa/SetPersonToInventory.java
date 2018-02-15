/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.*;
import com.example.Ksiega_Inwentarzowa.Utils.Log;
import com.example.Ksiega_Inwentarzowa.controller.Controller_Rest;
import com.example.Ksiega_Inwentarzowa.entities.Cell;
import com.example.Ksiega_Inwentarzowa.entities.Employee;
import com.example.Ksiega_Inwentarzowa.entities.EmployeeBaza;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.example.Ksiega_Inwentarzowa.entities.InventoryBaza;
import com.example.Ksiega_Inwentarzowa.entities.Room;
import com.example.Ksiega_Inwentarzowa.repositories.EmployeeRepository;
import com.example.Ksiega_Inwentarzowa.repositories.InventoryRepository;
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
public class SetPersonToInventory extends MyLayout{
    
    //AppUI app;
    ComboBox<String> comboEmployee;
    ComboBox<InventoryBaza> comboInventories;
    Button przypisz;
    Button usunWlasciciela;
    
    SetPersonToInventory(){
        comboEmployee = new ComboBox<>("Wybierz użytkownika:", new ArrayList<>());
        comboInventories = new ComboBox<>("Wybierz przedmiot:", new ArrayList<>());
        przypisz = new Button("Przypisz");
        przypisz.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                assignPersonToItem(AppUI.getInstance().employeeRepository, AppUI.getInstance().inventoryRepository);
                Notification message = new Notification(comboEmployee.getValue() + " został właścicielem przedmiotu:!",
                        comboInventories.getValue().getInventoryNumber());
                getEmployeeNames(AppUI.getInstance().employeeRepository);
                getInventoryNames(AppUI.getInstance().inventoryRepository);
                message.show(Page.getCurrent());
            }
        });
        usunWlasciciela = new Button("Usuń właściciela");
        usunWlasciciela.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                removePersonFromItem(AppUI.getInstance().employeeRepository, AppUI.getInstance().inventoryRepository);
                Notification message = new Notification("Usunięto właściciela przedmiotu: ", comboInventories.getValue().getInventoryNumber());
                message.show(Page.getCurrent());
            }
        });
        this.addComponent(comboEmployee);
        this.addComponent(comboInventories);
        this.addComponent(przypisz);
        this.addComponent(usunWlasciciela);
    }
    
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
        comboEmployee.setSelectedItem("");
        comboEmployee.setItems(employeeNames);
    }
    
    void getInventoryNames(InventoryRepository inventoryRepository){
        AppUI app = AppUI.getInstance();
        List<InventoryBaza> inventoryListByCell = null;
        if(czyAdministrator){
            inventoryListByCell = app.inventoryRepository.findAll();
        } else {
            inventoryListByCell = app.inventoryRepository.findByCellId(loggedUser.getDetails().getCellId());
        }
        comboInventories.setSelectedItem(null);
        comboInventories.setItems(inventoryListByCell);
        comboInventories.setItemCaptionGenerator(InventoryBaza::getInventoryNumber);
    }
    
    void assignPersonToItem(EmployeeRepository employeeRepository, InventoryRepository inventoryRepository){
        System.out.println(comboEmployee.getValue());
        System.out.println(comboInventories.getValue().getInventoryNumber());
        AppUI app = AppUI.getInstance();
        String employName = comboEmployee.getValue();
        List<Employee> employees = new Controller_Rest().getAllEmployees();
        Integer id = null;
        for(Employee e : employees){
            if(e.getName().equals(employName)){
                id = e.getEmployeeId();
                break;
            }
        }
        EmployeeBaza updatedEmployee = new EmployeeBaza();//2. dodadnie przedmiotu do nowego właściciela
        updatedEmployee.setEmployeeId(employeeRepository.findOne(id).getEmployeeId());
        updatedEmployee.setOddelegowany(employeeRepository.findOne(id).getOddelegowany());
        updatedEmployee.setCellId(employeeRepository.findOne(id).getCellId());
        updatedEmployee.setInventoryBazaSet(employeeRepository.findOne(id).getInventoryBazaSet());
        //employeeRepository.saveAndFlush(updatedEmployee);
        
        InventoryBaza item = comboInventories.getValue();
        EmployeeBaza oldItemOwner = item.getEmployeeId();
        if(oldItemOwner != null){
            oldItemOwner.getInventoryBazaSet().remove(item);
            //1. usunięcie przedmiotu od poprzedniego właściciela, jeśli nie null
            employeeRepository.saveAndFlush(oldItemOwner);
            //item.setEmployeeId(null);
            //inventoryRepository.saveAndFlush(item);
        }
        //inventoryRepository.delete(item);
        //inventoryRepository.flush();
        item.setEmployeeId(updatedEmployee);//3. zmiana właściciela przedmiotu
        updatedEmployee.getInventoryBazaSet().add(item);
        
        employeeRepository.saveAndFlush(updatedEmployee);
        inventoryRepository.saveAndFlush(item);
    }
    
    void removePersonFromItem(EmployeeRepository employeeRepository, InventoryRepository inventoryRepository){
        AppUI app = AppUI.getInstance();
        InventoryBaza item = comboInventories.getValue();
        EmployeeBaza oldItemOwner = item.getEmployeeId();
        if(oldItemOwner != null){
            oldItemOwner.getInventoryBazaSet().remove(item);
            //1. usunięcie przedmiotu od poprzedniego właściciela, jeśli nie null
            employeeRepository.saveAndFlush(oldItemOwner);
        }
        item.setEmployeeId(null);//2. zmiana właściciela przedmiotu
        inventoryRepository.saveAndFlush(item);
    }
}
