/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import com.example.Ksiega_Inwentarzowa.controller.Controller_Rest;
import com.example.Ksiega_Inwentarzowa.entities.Cell;
import com.example.Ksiega_Inwentarzowa.entities.Employee;
import com.example.Ksiega_Inwentarzowa.entities.EmployeeBaza;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.example.Ksiega_Inwentarzowa.entities.InventoryBaza;
import com.example.Ksiega_Inwentarzowa.entities.LoggedUser;
import com.example.Ksiega_Inwentarzowa.repositories.EmployeeRepository;
import com.example.Ksiega_Inwentarzowa.repositories.InventoryRepository;
import com.vaadin.annotations.PreserveOnRefresh;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mic459
 */
@SpringUI
@PreserveOnRefresh
public class AppUI extends UI{
    
    static LoggedUser loggedUser;
    
    @Autowired
    InventoryRepository inventoryRepository;
    
    @Autowired
    EmployeeRepository employeeRepository;
    
    @Autowired
    DataSource dataSource;
    
    static HorizontalLayout layout;
    
    static Menu menu;
    static FindByEvidence findByEvidence;
    static FindByEmployee findByEmployee;
    static LoginLayout loginLayout;
    
    static Boolean czyAdministrator;
    static Boolean czyKierownik;                
    static Boolean czyUzytkownikOddelegowany;
    static Boolean czyOsobaSEM;
    
    static Grid<Inventory> ItemsTable;      //static bo odwołujemy się z innych klas do tego
    static List<Inventory> ItemsList;
    static List<InventoryBaza> inventoryBazaList;
    static List<EmployeeBaza> employeeBazaList;
    
    public static AppUI instance;
    
    @Override
    protected void init(VaadinRequest request) {
        setInstance((AppUI) UI.getCurrent());
        
        loggedUser = new Controller_Rest().LoginLogout("", "");
        
        czyAdministrator = false;
        czyKierownik = false;                
        czyUzytkownikOddelegowany = false;
        czyOsobaSEM = false;

        createItemsTable(); 
        createLayout();
    }
    
    @Override
    protected void refresh(VaadinRequest request){
        if(loggedUser.isSuccess()){
            layout.removeAllComponents();
            layout.addComponent(menu);
        }
    }

    private void createLayout() {
   
        layout = new HorizontalLayout();
        setContent(layout);
        
        findByEvidence = new FindByEvidence();
        findByEmployee = new FindByEmployee();
        loginLayout = new LoginLayout();
        menu =  new Menu();
        
        layout.addComponent(loginLayout);
    }
    
    private void createItemsTable() {
        ItemsList = new ArrayList<Inventory>();
        ItemsTable = new Grid<>();
        
        ItemsTable.setWidth("1000");
        ItemsTable.setHeight("300");
        
        ItemsTable.addColumn(Inventory::getInventoryNumber).setCaption("inventoryNumber");
        ItemsTable.addColumn(Inventory::getStatus).setCaption("status");
        ItemsTable.addColumn(Inventory::getName).setCaption("name");
        ItemsTable.addColumn(Inventory::getDescription).setCaption("description");//.setWidth(700);
        ItemsTable.addColumn(Inventory::getIncomeDate).setCaption("incomeDate");
        ItemsTable.addColumn(Inventory::getSymbol).setCaption("symbol");
        ItemsTable.addColumn(Inventory::getVatNumber).setCaption("vatNumber");
        ItemsTable.addColumn(Inventory::getUnitPrice).setCaption("unitPrice");
        ItemsTable.addColumn(Inventory::getIncomeNumber).setCaption("incomeNumber");
        ItemsTable.addColumn(Inventory::getOutcomeNumber).setCaption("outcomeNumber");
        ItemsTable.addColumn(Inventory::getIncomePrice).setCaption("incomePrice");
        ItemsTable.addColumn(Inventory::getOutcomePrice).setCaption("outcomePrice");
        ItemsTable.addColumn(Inventory::getCellId).setCaption("cellId");
        ItemsTable.addColumn(Inventory::getCellName).setCaption("cellName");
        ItemsTable.addColumn(Inventory::getEmployeeId).setCaption("employeeId");
        ItemsTable.addColumn(Inventory::getEmployeeName).setCaption("employeeName");
    }
    
    static List<Inventory> updateItemsList(List<Inventory> ItemsList, InventoryRepository inventoryRepository, EmployeeRepository employeeRepository) {
        Controller_Rest rest = new Controller_Rest();
        ItemsList = rest.getAllItems();
        List<Cell> Cells = rest.getAllCells();
        List<Employee> Employees = rest.getAllEmployees();
        employeeBazaList = GetEmployeesFromOurBase(employeeRepository);
        inventoryBazaList = GetItemsFromOurBase(inventoryRepository);
        //addToDatabaseDefaultValuesFromERP(ItemsList, Employees, inventoryRepository, employeeRepository);
        for (InventoryBaza itemBaza : inventoryBazaList){
            String id = itemBaza.getInventoryNumber();
            for(Inventory item : ItemsList) {
                if(item.getInventoryNumber().equals(id)) {
                    for (Cell cell : Cells){
                        if(cell.getCellId().equals(itemBaza.getCellId())){
                            item.setCellId(itemBaza.getCellId());
                            item.setCellName(cell.getCellName());
                            break;
                        }
                    }
                    for (Employee employee : Employees){
                        if(itemBaza.getEmployeeId() != null && employee.getEmployeeId().equals(itemBaza.getEmployeeId().getEmployeeId())){
                            item.setEmployeeId(itemBaza.getEmployeeId().getEmployeeId());
                            item.setEmployeeName(employee.getName());
                            break;
                        }
                    }
                }
            }
        }
        //for(EmployeeBaza employeeBaza : employeeBazaList){
        //    employeeRepository.saveAndFlush(employeeBaza);
        //}
        //for(InventoryBaza itemBaza : inventoryBazaList){
        //    inventoryRepository.saveAndFlush(itemBaza);
        //}
        return ItemsList;
    }
    
    static List<InventoryBaza> GetItemsFromOurBase(InventoryRepository repository){
        List<InventoryBaza> inventoryBazaList = repository.findAll();
        for(InventoryBaza i : inventoryBazaList){
            if (i.getEmployeeId() == null){
                System.out.println(i.getInventoryNumber() + " - " + null);
            } else {
                System.out.println(i.getInventoryNumber() + " - " + i.getEmployeeId().getEmployeeId());
            }
        }
        return inventoryBazaList;
    }
    
    static List<EmployeeBaza> GetEmployeesFromOurBase(EmployeeRepository repository){
        List<EmployeeBaza> employeeBazaList = repository.findAll();
        return employeeBazaList;
    }
    
    private static void addToDatabaseDefaultValuesFromERP(List<Inventory> ItemsList, List<Employee> Employees, InventoryRepository inventoryRepository, EmployeeRepository employeeRepository){
        List<EmployeeBaza> employeeBazaList = new ArrayList<EmployeeBaza>();
        List<InventoryBaza> inventoryBazaList = new ArrayList<InventoryBaza>();
        for(Employee employee : Employees){//tworzymy dla bazy obiekty EmployeeBaza i dodajemy do listy tymczasowej
            EmployeeBaza employeeBaza = new EmployeeBaza();
            employeeBaza.setEmployeeId(employee.getEmployeeId());
            employeeBaza.setOddelegowany(false);
            employeeBaza.setCellId(employee.getCellId());
            employeeBazaList.add(employeeBaza);
            employeeRepository.saveAndFlush(employeeBaza);
        }
        for(Inventory item : ItemsList){//tworzymy dla bazy obiekty InventoryBaza i dodajemy do listy tymczasowej
            InventoryBaza itemBaza = new InventoryBaza();
            itemBaza.setInventoryNumber(item.getInventoryNumber());
            itemBaza.setCellId(item.getCellId());
            if(item.getEmployeeId() != null){
                itemBaza.setEmployeeId(employeeRepository.findOne(item.getEmployeeId()));
            }
            //itemBaza.setRoomId(null);
            inventoryBazaList.add(itemBaza);
            inventoryRepository.saveAndFlush(itemBaza);
        }
        for(EmployeeBaza employeeBaza : employeeBazaList){
            for(InventoryBaza itemBaza : inventoryBazaList){
                if (itemBaza.getEmployeeId() != null && itemBaza.getEmployeeId().getEmployeeId().equals(employeeBaza.getEmployeeId())){
                    employeeBaza.getInventoryBazaSet().add(itemBaza);
                }
            }
            employeeRepository.saveAndFlush(employeeBaza);
        }
        for(InventoryBaza itemBaza : inventoryBazaList){
            inventoryRepository.saveAndFlush(itemBaza);
        }
    }
    
    
    public void setInstance(AppUI instance){
        this.instance = instance;
    }
    
    public static AppUI getInstance(){
        return instance;
    }
}
