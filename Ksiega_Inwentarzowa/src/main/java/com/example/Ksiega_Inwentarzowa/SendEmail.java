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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kamil
 */
public class SendEmail extends MyLayout{
    
    //AppUI app;
    HorizontalLayout layout;
    Button przeniesienie;
    Button likwidacja;
    Button ot;
    TextField address;
    TextField title;
    TextArea email;
    Button wyslij;
    
    SendEmail(){
        layout = new HorizontalLayout();
        przeniesienie = new Button("Przeniesienie sprzętu");
        likwidacja = new Button("Likwidacja sprzętu");
        ot = new Button("Przyjęcie sprzętu (OT)");
        address = new TextField("Adres odbiorcy");
        address.setWidth("400");
        title = new TextField("Tytuł wiadomości");
        title.setWidth("400");
        email = new TextArea("Treść maila");
        email.setWidth("800");
        email.setHeight("300");
        wyslij = new Button("Wyślij");
        
        wyslij.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                new Controller_Rest().SendEmail(address.getValue(), title.getValue(), email.getValue());
                Notification message = new Notification("Wysłano wniosek");
                message.show(Page.getCurrent());
            }
        });
        
        przeniesienie.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                address.setValue("joanna.wozniak@fakeemail.com");
                title.setValue("Przeniesienie sprzętu");
                email.setValue("Prosimy o zaktualizowanie w bazie ERP następującej zmiany:"
                        + "\nSprzęt o ID = <tu wpisz ID> został przeniesiony do jednostki <Wpisz nazwę jednostki>."
                        + "\nWysłane przez: " + loggedUser.getDetails().getName() + ".");
            }
        });
        
        likwidacja.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                address.setValue("joanna.wozniak@fakeemail.com");
                title.setValue("Likwidacja sprzętu");
                email.setValue("Prosimy o zaktualizowanie w bazie ERP następującej zmiany:"
                        + "\nSprzęt o ID = <tu wpisz ID> został usunięty z jednostki <Wpisz nazwę jednostki>."
                        + "\nWysłane przez: " + loggedUser.getDetails().getName() + ".");
            }
        });
                
        ot.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                address.setValue("joanna.wozniak@fakeemail.com");
                title.setValue("OT");
                email.setValue("Prosimy o zaktualizowanie w bazie ERP następującej zmiany:"
                        + "\nSprzęt o ID = <tu wpisz ID oraz dane dotyczące nowego sprzętu>"
                        + "został przyjęty do jednostki <Wpisz nazwę jednostki>."
                        + "\nWysłane przez: " + loggedUser.getDetails().getName() + ".");
            }
        });
        
        this.addComponent(layout);
        layout.addComponent(przeniesienie);
        layout.addComponent(likwidacja);
        layout.addComponent(ot);
        this.addComponent(address);
        this.addComponent(title);
        this.addComponent(email);
        this.addComponent(wyslij);
    }
}