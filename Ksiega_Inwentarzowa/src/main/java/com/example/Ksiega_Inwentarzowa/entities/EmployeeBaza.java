/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "employee")
//@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBaza {
    
    @Id
    @Column(columnDefinition = "integer")
    private Integer employeeId;
    @Column(columnDefinition = "boolean")
    private Boolean oddelegowany;
    @Column(columnDefinition = "bigint")
    private Long cellId;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<InventoryBaza> inventoryBazaSet = new HashSet<>();
    
    @Override
    public String toString() {
        return "Employee";//{" +
                //"employeeId = " + employeeId +
                //", oddelegowany = '" + oddelegowany + '\'' +
                //", cell_id = '" + cellId + '\'' +
                //", inventoryBazaSet = " + inventoryBazaSet +
                //'}';
    }
}
