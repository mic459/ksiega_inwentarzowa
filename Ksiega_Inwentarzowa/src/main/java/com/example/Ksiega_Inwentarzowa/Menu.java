/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsList;
import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsTable;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyAdministrator;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyKierownik;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyOsobaSEM;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyUzytkownikOddelegowany;
import com.vaadin.ui.Button;
import static com.example.Ksiega_Inwentarzowa.AppUI.findByEvidence;
import static com.example.Ksiega_Inwentarzowa.AppUI.findByEmployee;
import static com.example.Ksiega_Inwentarzowa.AppUI.layout;
import static com.example.Ksiega_Inwentarzowa.AppUI.loggedUser;
import static com.example.Ksiega_Inwentarzowa.AppUI.loginLayout;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.vaadin.ui.Layout;
import java.util.ArrayList;
import java.util.List;

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
        
        PrzyciskPodgladMajatkuJednostki = new Button("Podgląd majątku jednostki");
        
        PrzyciskPrzypisanieMiejscaDoElementu = new Button("Przypisz miejsce do elementu");
        
        PrzyciskPrzypisanieOsoby = new Button("Przypisz osobę do elementu");
        
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
