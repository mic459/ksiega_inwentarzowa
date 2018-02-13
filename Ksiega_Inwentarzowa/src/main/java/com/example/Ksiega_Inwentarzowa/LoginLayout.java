/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.*;
import com.example.Ksiega_Inwentarzowa.controller.Controller_Rest;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.example.Ksiega_Inwentarzowa.entities.LoggedUser;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kamil
 */
public class LoginLayout extends MyLayout{
    
    Button button;
    TextField login;
    PasswordField password;
    
    Writer output; 
    LoginLayout(){
        
        login = new TextField("Login:");
        password = new PasswordField("Hasło:");
        button = new Button("Zaloguj");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Login();
            }
        });
        this.addComponent(login);
        this.addComponent(password);
        this.addComponent(button);
        removeComponent(buttonBackToMenu);
    }
    
    void Login(){
        Controller_Rest rctrl = new Controller_Rest();
        loggedUser = rctrl.LoginLogout(login.getValue(), password.getValue());
        //PrintInfoAboutLoggedUserToConsole();        
        
        if(loggedUser.isSuccess()){
            CheckUserRuleInSystem();
            menu.showLayout();
            try {
                output = new BufferedWriter(new FileWriter("log.txt", true));
                output.append(login.getValue() + "\t"+ LocalDateTime.now() +  "\ttrue\n");
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Notification message = new Notification("Logowanie nieudane!", "Podano zły login i/lub hasło.");
            message.show(Page.getCurrent());
            try {
                output = new BufferedWriter(new FileWriter("log.txt", true));
                output.append(login.getValue() + "\t"+ LocalDateTime.now() +  "\tfalse\n");
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        login.setValue("");
        password.setValue("");
    }
    
    void Logout(){
        loginLayout.showLayout();
        loggedUser = new Controller_Rest().LoginLogout("", "");
        //PrintInfoAboutLoggedUserToConsole();
    }
    
    void CheckUserRuleInSystem(){
        czyUzytkownikOddelegowany = false;      //jak na razie nie mamy tego w bazie, więc daje tak żeby nie było null
        if(loggedUser.isSuccess()){
            if(loggedUser.getDetails().getFunction() == null){
                czyAdministrator = false;
                czyKierownik = false;                   
            }
            else {
                switch(loggedUser.getDetails().getFunction()){
                    case "kierownik zakładu":
                        czyAdministrator = false;
                        czyKierownik = true;                
                        break;
                    case "Dziekan":
                        czyAdministrator = false;
                        czyKierownik = true;                
                        break;
                    case "Administrator Bezpieczeńswta Informacji":
                        czyAdministrator = true;
                        czyKierownik = true;                
                        break;
                    default:
                        czyAdministrator = false;
                        czyKierownik = false;                
                }
            }
            if(loggedUser.getDetails().getCellName() == null){
                czyOsobaSEM = false;
            }
            else if(loggedUser.getDetails().getCellName().equals("Sekcja Ewidencji Majątku")){
                czyOsobaSEM = true;
            }
            else{
                czyOsobaSEM = false;
            } 
        } else {
            czyAdministrator = false;
            czyKierownik = false;
            czyOsobaSEM = false;
        }
    }
    
    void PrintInfoAboutLoggedUserToConsole(){
        System.out.println(loggedUser.isSuccess());
        System.out.println(loggedUser.isStudent());
        System.out.println(loggedUser.isEmployee());
        if(loggedUser.isSuccess()){
            System.out.println(loggedUser.getDetails().getLogin());
            System.out.println(loggedUser.getDetails().getName());
            System.out.println(loggedUser.getDetails().getDegree());
            System.out.println(loggedUser.getDetails().getEmployeeId());
            System.out.println(loggedUser.getDetails().getWorkplace());
            System.out.println(loggedUser.getDetails().getFunction());
            System.out.println(loggedUser.getDetails().getEmail());
            System.out.println(loggedUser.getDetails().getDepartmentName());
            System.out.println(loggedUser.getDetails().getCellId());
            System.out.println(loggedUser.getDetails().getCellName());
            System.out.println(loggedUser.getDetails().getIsManager());
            System.out.println(loggedUser.getDetails().getRoomId());
            System.out.println(loggedUser.getDetails().getIsAsi());
        } else {
            System.out.println(loggedUser.getDetails());
        }
        System.out.println("--------------------");
    }
}
