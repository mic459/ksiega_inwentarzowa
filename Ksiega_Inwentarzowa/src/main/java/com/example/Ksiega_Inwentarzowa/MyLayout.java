/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.layout;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import static com.example.Ksiega_Inwentarzowa.AppUI.menu;
import com.vaadin.ui.Layout;

/**
 *
 * @author Kamil
 */
public abstract class MyLayout extends VerticalLayout{

    Button buttonBackToMenu;
    MyLayout() {
        buttonBackToMenu = new Button("Powrót do Menu");
        buttonBackToMenu.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    menu.showLayout();
                }
        });
        this.addComponent(buttonBackToMenu); 
    }
    protected void showLayout(){
        layout.removeAllComponents();
        layout.addComponent(this);
    }
}
