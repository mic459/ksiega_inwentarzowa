/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.repositories;

import com.example.Ksiega_Inwentarzowa.entities.EmployeeBaza;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Wanted
 */
public interface EmployeeRepository extends JpaRepository<EmployeeBaza, Integer>{}
