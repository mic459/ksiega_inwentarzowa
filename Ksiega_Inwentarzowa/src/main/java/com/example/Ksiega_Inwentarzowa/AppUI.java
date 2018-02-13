/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import com.example.Ksiega_Inwentarzowa.controller.Controller_Rest;
import com.example.Ksiega_Inwentarzowa.entities.Cell;
import com.example.Ksiega_Inwentarzowa.entities.Employee;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.example.Ksiega_Inwentarzowa.entities.InventoryBaza;
import com.example.Ksiega_Inwentarzowa.entities.LoggedUser;
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
    InventoryRepository repository;
    
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
    
    static List<Inventory> updateItemsList(List<Inventory> ItemsList, InventoryRepository repository) {
        Controller_Rest rest = new Controller_Rest();
        ItemsList = rest.getAllItems();
        inventoryBazaList = GetItemsFromOurBase(repository);
        addToDataBaseDefaultValuesFromERP(ItemsList, inventoryBazaList, repository);
        List<Cell> Cells = rest.getAllCells();
        List<Employee> Employees = rest.getAllEmployees();
        for (InventoryBaza itemBaza : inventoryBazaList){
            String id = itemBaza.getInventoryNumber();
            for(Inventory item : ItemsList) {
                if(item.getInventoryNumber().equals(id)) {
                    for (Cell cell : Cells){
                        if(cell.getCellId().equals(itemBaza.getCellNameId())){
                            item.setCellId(itemBaza.getCellNameId());
                            item.setCellName(cell.getCellName());
                            break;
                        }
                    }
                    for (Employee employee : Employees){
                        if(employee.getEmployeeId().equals(itemBaza.getEmployeeId())){
                            item.setEmployeeId(itemBaza.getEmployeeId());
                            item.setEmployeeName(employee.getName());
                            break;
                        }
                    }
                }
            }
        }
        /*for(InventoryBaza itemBaza : inventoryBazaList){
            repository.saveAndFlush(itemBaza);
        }*/
        return ItemsList;
    }
    
    static List<InventoryBaza> GetItemsFromOurBase(InventoryRepository repository){
        List<InventoryBaza> inventoryBazaList = repository.findAll();
        return inventoryBazaList;
    }
    
    private static void addToDataBaseDefaultValuesFromERP(List<Inventory> ItemsList, List<InventoryBaza> inventoryBazaList, InventoryRepository repository){
        for(Inventory item : ItemsList){
            InventoryBaza itemBaza = new InventoryBaza();
            itemBaza.setInventoryNumber(item.getInventoryNumber());
            itemBaza.setCellNameId(item.getCellId());
            itemBaza.setEmployeeId(item.getEmployeeId());
            repository.saveAndFlush(itemBaza);
        }
    }
    
    public void setInstance(AppUI instance){
        this.instance = instance;
    }
    
    public static AppUI getInstance(){
        return instance;
    }
}
