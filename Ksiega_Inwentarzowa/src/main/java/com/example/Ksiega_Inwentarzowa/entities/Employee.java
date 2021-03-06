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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mic459
 */
//@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    
    String login;
    String name;
    String degree;
    Integer employeeId;
    String workplace;
    String function;
    String email;
    String departmentName;
    Long cellId;
    String cellName;
    Boolean isManager;
    String roomId;
    Boolean isAsi;
    
    /*@Override
    public String toString() {
        return name + ' ' + surname;
    }*/
    
}
