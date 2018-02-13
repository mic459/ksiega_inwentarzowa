/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Wanted
 */

@Data
@Table(name = "inventory")
@Builder
@Entity
//@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBaza {
    
    @Id
    @Column(name = "inventory_number", unique=true, columnDefinition="varchar(45)")
    private String inventoryNumber;
    @Column(name = "cell_name_id", columnDefinition = "bigint")
    private Long cellNameId;
    @Column(name = "employee_id", columnDefinition = "integer")
    private Integer employeeId;
}
