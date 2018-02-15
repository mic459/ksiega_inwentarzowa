/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa;

import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsList;              //te importy są potrzebne żeby używać ItemsList zamiast AppUI.ItemsList
import static com.example.Ksiega_Inwentarzowa.AppUI.ItemsTable;
import static com.example.Ksiega_Inwentarzowa.AppUI.czyAdministrator;
import static com.example.Ksiega_Inwentarzowa.AppUI.updateItemsList;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import java.util.List;

/**
 *
 * @author Kamil
 */
public class FindByCell extends MyLayout{
    
    List<Inventory> getListByCell(Long cellId) {
        AppUI app = AppUI.getInstance();
        ItemsList = updateItemsList(ItemsList, app.inventoryRepository, app.employeeRepository);
        if(!czyAdministrator){
            for(int j = ItemsList.size() - 1; j >= 0; j--){
                if(ItemsList.get(j).getCellId() == null){
                    ItemsList.remove(ItemsList.get(j));
                }
                else if(!ItemsList.get(j).getCellId().equals(cellId)){
                    ItemsList.remove(ItemsList.get(j));
                }
            }
        }
        return ItemsList;
    }
}
