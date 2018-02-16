/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.*;
import com.vaadin.ui.Button;

/**
 *
 * @author Kamil
 */
public class Menu extends MyLayout{
    
    Button PrzyciskOddelegowanieOsoby;
    
    Button PrzyciskPodgladMajatkuJednostki;
    Button PrzyciskPrzypisanieMiejscaDoElementu;
    Button PrzyciskPrzypisanieOsoby;
    Button PrzyciskZlozenieWniosku;
    Button PrzyciskGenerujRaport;
    
    Button PrzyciskPodgladMajatkuOsoby;
    Button PrzyciskWyszukajElement;
    Button PrzyciskKonfigurujPowiadomienia;
    
    Button PrzyciskPrzyjmijWnioski;
    Button PrzyciskZarzadzajWnioskami;
    
    Button Wyloguj;
    
    Menu(){
        PrzyciskOddelegowanieOsoby = new Button("Oddeleguj osobę do prowadzenia księgi jednostki");
        PrzyciskOddelegowanieOsoby.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setOddelegowany.getEmployeeNames(AppUI.getInstance().employeeRepository);
                setOddelegowany.showLayout();
            }
        });
        
        PrzyciskPodgladMajatkuJednostki = new Button("Podgląd majątku jednostki");
        PrzyciskPodgladMajatkuJednostki.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                findByCell.addComponent(ItemsTable);
                ItemsList = findByCell.getListByCell(loggedUser.getDetails().getCellId());
                ItemsTable.setItems(ItemsList);
                findByCell.showLayout();
            }
        });
        
        PrzyciskPrzypisanieMiejscaDoElementu = new Button("Przypisz miejsce do elementu");
        PrzyciskPrzypisanieMiejscaDoElementu.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setRoomToInventory.getRoomNames();
                setRoomToInventory.getInventoryNames(AppUI.getInstance().inventoryRepository);
                setRoomToInventory.showLayout();
            }
        });
        
        PrzyciskPrzypisanieOsoby = new Button("Przypisz osobę do elementu");
        PrzyciskPrzypisanieOsoby.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setPersonToInventory.getEmployeeNames(AppUI.getInstance().employeeRepository);
                setPersonToInventory.getInventoryNames(AppUI.getInstance().inventoryRepository);
                setPersonToInventory.showLayout();
            }
        });
        
        PrzyciskZlozenieWniosku = new Button("Złóż wniosek do Sekcji Ewidencji Majątku");
        
        PrzyciskGenerujRaport = new Button("Generuj Raport");
        
        PrzyciskPodgladMajatkuOsoby = new Button("Podgląd zarządzanego majątku");
        PrzyciskPodgladMajatkuOsoby.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                findByEmployee.addComponent(ItemsTable);
                ItemsList = findByEmployee.getListByEmployee(loggedUser.getDetails().getEmployeeId());
                ItemsTable.setItems(ItemsList);
                findByEmployee.showLayout();
            }
        });
        
        PrzyciskWyszukajElement = new Button("Wyszukaj element po numerze ewidencyjnym");
        PrzyciskWyszukajElement.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                findByEvidence.addComponent(ItemsTable);
                ItemsTable.setItems();
                findByEvidence.showLayout();
            }
        });
        
        PrzyciskKonfigurujPowiadomienia = new Button("Konfiguruj powiadomienia");
        
        PrzyciskPrzyjmijWnioski = new Button("Przyjmij wnioski");
        
        PrzyciskZarzadzajWnioskami = new Button("Zarządzaj wnioskami");
        
        Wyloguj = new Button("Wyloguj");
        Wyloguj.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                loginLayout.Logout();
            }
        });
    }
    
    @Override
    protected void showLayout(){
        super.showLayout(); //wywołuje metodę showLayout() z klasy nadrzędnej
        this.removeAllComponents();
        if(czyAdministrator | czyKierownik){
            this.addComponent(PrzyciskOddelegowanieOsoby);
        }
        
        if(czyAdministrator | czyKierownik | czyUzytkownikOddelegowany){
            this.addComponent(PrzyciskPodgladMajatkuJednostki);
            this.addComponent(PrzyciskPrzypisanieMiejscaDoElementu);
            this.addComponent(PrzyciskPrzypisanieOsoby);
            this.addComponent(PrzyciskZlozenieWniosku);
            this.addComponent(PrzyciskGenerujRaport);            
        }
        
        this.addComponent(PrzyciskPodgladMajatkuOsoby);
        this.addComponent(PrzyciskWyszukajElement);
        this.addComponent(PrzyciskKonfigurujPowiadomienia);
        
        if(czyAdministrator | czyOsobaSEM){
            this.addComponent(PrzyciskPrzyjmijWnioski);
            this.addComponent(PrzyciskZarzadzajWnioskami);            
        }
        
        this.addComponent(Wyloguj);
        this.removeComponent(buttonBackToMenu);
    }
}
