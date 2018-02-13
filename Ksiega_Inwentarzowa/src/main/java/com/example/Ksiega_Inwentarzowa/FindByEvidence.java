/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsList;              //te importy są potrzebne żeby używać ItemsList zamiast AppUI.ItemsList
import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsTable;
import static com.example.Ksiega_Inwentarzowa.AppUI.updateItemsList;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import java.util.List;

/**
 *
 * @author Kamil
 */
public class FindByEvidence extends MyLayout{
    
    //AppUI app;
    Button button;
    TextField textField;
    
    FindByEvidence(){
        textField = new TextField();
        button = new Button("Wyszukaj po numerze");
        button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    ItemsList = getListByEvidenceNumber(ItemsList, textField.getValue());
                    ItemsTable.setItems(ItemsList);
                }
            });
        this.addComponent(textField);
        this.addComponent(button);
    }
    
    List<Inventory> getListByEvidenceNumber(List<Inventory> ItemsList, String WrittenNumber) {
        AppUI app = AppUI.getInstance();
        ItemsList = updateItemsList(ItemsList, app.repository);
        
        int numberOfItems = ItemsList.size();
        for(int i = numberOfItems - 1; i >= 0; i--){
            String EvidenceNumber = ItemsList.get(i).getInventoryNumber();
            if(!(EvidenceNumber.equals(WrittenNumber))){
                ItemsList.remove(i);
            }
        }
        return ItemsList;
    }
}
