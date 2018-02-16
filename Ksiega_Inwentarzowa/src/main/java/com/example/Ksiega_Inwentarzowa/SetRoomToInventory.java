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
public class SetRoomToInventory extends MyLayout{
    
    //AppUI app;
    ComboBox<String> comboRoom;
    ComboBox<InventoryBaza> comboInventories;
    Button przypisz;
    Button usunPokoj;
    
    SetRoomToInventory(){
        comboRoom = new ComboBox<>("Wybierz pokój:", new ArrayList<>());
        comboInventories = new ComboBox<>("Wybierz przedmiot:", new ArrayList<>());
        przypisz = new Button("Przypisz");
        przypisz.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                assignRoomToItem(AppUI.getInstance().inventoryRepository);
                Notification message = new Notification("Przedmiot o ID: " + comboInventories.getValue().getInventoryNumber() +
                        " został przypisany do pokoju:", comboRoom.getValue());
                Log.logUpdate(comboInventories.getValue().getInventoryNumber()  + " located true in " + comboRoom.getValue());
                setRoomToInventory.getRoomNames();
                setRoomToInventory.getInventoryNames(AppUI.getInstance().inventoryRepository);
                message.show(Page.getCurrent());
            }
        });
        usunPokoj = new Button("Wyrzuć przedmiot z pokoju");
        usunPokoj.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                removeRoomFromItem(AppUI.getInstance().inventoryRepository);
                Notification message = new Notification("Przedmiot o ID: " + comboInventories.getValue().getInventoryNumber() +
                        " nie jest przypisany do żadnego pokoju!");
                Log.logUpdate(comboInventories.getValue().getInventoryNumber()  + " located false");
                setRoomToInventory.getRoomNames();
                setRoomToInventory.getInventoryNames(AppUI.getInstance().inventoryRepository);
                message.show(Page.getCurrent());
            }
        });
        this.addComponent(comboRoom);
        this.addComponent(comboInventories);
        this.addComponent(przypisz);
        this.addComponent(usunPokoj);
    }
    
    void getRoomNames(){
        List<Room> rooms = new Controller_Rest().getAllRooms();
        List<String> allRoomNames;
        if(czyAdministrator){
            allRoomNames = new ArrayList<>();
            for(Room room : rooms){
                allRoomNames.add(room.getRoomName());
            }
        } else {
            allRoomNames = allRoomNamesFromCell(rooms);
        }
        comboRoom.setSelectedItem(null);
        comboRoom.setItems(allRoomNames);
    }
        
    List<String> allRoomNamesFromCell(List<Room> rooms) {
        List<String> allRoomNames = new ArrayList<>();
        for(Room room : rooms){
            if(room.getCellId().equals(loggedUser.getDetails().getCellId())){
                allRoomNames.add(room.getRoomName());
            }
        }
        return allRoomNames;
    }
    
    void getInventoryNames(InventoryRepository inventoryRepository){
        List<InventoryBaza> inventoryListByCell = null;
        if(czyAdministrator){
            inventoryListByCell = inventoryRepository.findAll();
        } else {
            inventoryListByCell = inventoryRepository.findByCellId(loggedUser.getDetails().getCellId());
        }
        comboInventories.setSelectedItem(null);
        comboInventories.setItems(inventoryListByCell);
        comboInventories.setItemCaptionGenerator(InventoryBaza::getInventoryNumber);
    }
    
    void assignRoomToItem(InventoryRepository inventoryRepository){
        List<Room> rooms = new Controller_Rest().getAllRooms();
        String roomId = null;
        for(Room room : rooms){
            if(room.getRoomName().equals(comboRoom.getValue())){
                roomId = room.getRoomId();
                break;
            }
        }
        InventoryBaza item = comboInventories.getValue();
        item.setRoomId(roomId);
        inventoryRepository.saveAndFlush(item);
    }
    
    void removeRoomFromItem(InventoryRepository inventoryRepository){
        InventoryBaza item = comboInventories.getValue();
        item.setRoomId(null);
        inventoryRepository.saveAndFlush(item);
    }
}
