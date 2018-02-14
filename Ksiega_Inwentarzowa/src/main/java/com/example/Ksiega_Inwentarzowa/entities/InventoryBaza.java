/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Wanted
 */

//@Data
@Table(name = "inventory")
//@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBaza {
    
    @Id
    @Column(unique=true, columnDefinition="varchar(45)")
    private String inventoryNumber;
    
    @Column(columnDefinition = "bigint")
    private Long cellId;
    
    @ManyToOne
    @JoinColumn(name="employee_id", columnDefinition = "integer", referencedColumnName="EmployeeId")
    private EmployeeBaza employeeId;
    
    @Column(columnDefinition = "varchar(45)")
    private String roomId;
    
    @Override
    public String toString() {
    return "Inventory";//{" +
            //"inventoryNumber = " + inventoryNumber +
            //", cellId = '" + cellId + '\'' +
            //", employeeId = '" + employeeId + '\'' +
            //", roomId = " + roomId +
            //'}';
    }
}
