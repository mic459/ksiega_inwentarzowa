/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mic459
 */
//@Entity
@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {
    
    String inventoryNumber;
    String status;
    String name;
    String description;
    Date incomeDate;
    String symbol;
    String vatNumber;
    Double unitPrice;
    Integer incomeNumber;
    Integer outcomeNumber;
    Double incomePrice;
    Double outcomePrice;
    Long cellId;
    String cellName;
    Integer employeeId;
    String employeeName;
    
    /*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "User_OSSystem", joinColumns = {
    @JoinColumn(name = "UserID", referencedColumnName = "IdUser")}, inverseJoinColumns = {
    @JoinColumn(name = "SystemID", referencedColumnName = "IdSystem")})
    private Set<OSSystem> systemsSet = new HashSet<>();
    
    @Override
    public String toString() {
        return name + ' ' + surname;
    }*/
    
}
