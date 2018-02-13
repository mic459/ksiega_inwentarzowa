/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.czyAdministrator;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyKierownik;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyOsobaSEM;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyUzytkownikOddelegowany;
import com.vaadin.ui.Button;
import static com.example.Ksiega_Inwentarzowa.AppUI.findByEvidence;

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
    
    
    Menu(){
        if(czyAdministrator | czyKierownik){
            PrzyciskOddelegowanieOsoby = new Button("Oddeleguj osobę do prowadzenia księgi jednostki");
            addComponent(PrzyciskOddelegowanieOsoby);
        }
        
        if(czyAdministrator | czyKierownik | czyUzytkownikOddelegowany){
            PrzyciskPodgladMajatkuJednostki = new Button("Podgląd majątku jednostki");
            addComponent(PrzyciskPodgladMajatkuJednostki);

            PrzyciskPrzypisanieMiejscaDoElementu = new Button("Przypisz miejsce do elementu");
            addComponent(PrzyciskPrzypisanieMiejscaDoElementu);
            
            PrzyciskPrzypisanieOsoby = new Button("Przypisz osobę do elementu");
            addComponent(PrzyciskPrzypisanieOsoby);
            
            PrzyciskZlozenieWniosku = new Button("Złóż wniosek do Sekcji Ewidencji Majątku");
            addComponent(PrzyciskZlozenieWniosku);

            PrzyciskGenerujRaport = new Button("Generuj Raport");
            addComponent(PrzyciskGenerujRaport);            
        }
        
        PrzyciskPodgladMajatkuOsoby = new Button("Podgląd zarządzanego majątku");
        addComponent(PrzyciskPodgladMajatkuOsoby);

        PrzyciskWyszukajElement = new Button("Wyszukaj element po numerze ewidencyjnym");
        PrzyciskWyszukajElement.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    showLayout(findByEvidence);
                }
            });
        addComponent(PrzyciskWyszukajElement);
            
        PrzyciskKonfigurujPowiadomienia = new Button("Konfiguruj powiadomienia");
        addComponent(PrzyciskKonfigurujPowiadomienia);
        
        if(czyAdministrator | czyOsobaSEM){
            PrzyciskPrzyjmijWnioski = new Button("Przyjmij wnioski");
            addComponent(PrzyciskPrzyjmijWnioski);
            
            PrzyciskZarzadzajWnioskami = new Button("Zarządzaj wnioskami");
            addComponent(PrzyciskZarzadzajWnioskami);            
        }
        
        removeComponent(buttonBackToMenu);
    }
}
