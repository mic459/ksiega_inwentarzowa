/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.controller;

import com.example.Ksiega_Inwentarzowa.AppUI;
import com.example.Ksiega_Inwentarzowa.entities.Cell;
import com.example.Ksiega_Inwentarzowa.entities.Employee;
import com.example.Ksiega_Inwentarzowa.entities.Inventory;
import com.example.Ksiega_Inwentarzowa.entities.LoggedUser;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mic459
 */
    
@RestController
public class Controller_Rest {
    
    @RequestMapping(value = "/inventory", method=RequestMethod.GET)
    public List<Inventory> getAllItems()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Inventory>> response =
        restTemplate.exchange("http://212.122.192.216:8097/api/v1/inventory/all",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Inventory>>() {
            });
        List<Inventory> items = response.getBody();
        return items;
    }
    
    @RequestMapping(value = "/cell",method=RequestMethod.GET)
    public List<Cell> getAllCells()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Cell>> response =
        restTemplate.exchange("http://212.122.192.216:8097/api/v1/cell/all",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Cell>>() {
            });
        List<Cell> cells = response.getBody();
        return cells;
    }
    
    @RequestMapping(value = "/employee", method=RequestMethod.GET)
    public List<Employee> getAllEmployees()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Employee>> response =
        restTemplate.exchange("http://212.122.192.216:8097/api/v1/employee/all",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
            });
        List<Employee> employees = response.getBody();
        return employees;
    }
    
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public LoggedUser LoginLogout(String login, String password)
    {
        //build json with json-simple-1.1.1.jar
        JSONObject json = new JSONObject();
        json.put("login", login);
        json.put("password", password);
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(json, headers);
        ResponseEntity<LoggedUser> response = restTemplate.postForEntity("http://212.122.192.216:8097/api/v1/user/checklogin", request , LoggedUser.class );
        
        LoggedUser loggedUser = response.getBody();
        return loggedUser;
    }
    
    
    //prowizoryczny POST do wysyłania maili, ponieważ metoda do wysyłania maili z API - nie działa
    @RequestMapping(value = "/email", method=RequestMethod.POST)
    public Object SendEmail(String toaddress, String title, String content)
    {
        //build json with json-simple-1.1.1.jar
        JSONObject json = new JSONObject();
        json.put("toaddress", toaddress);
        json.put("title", title);
        json.put("content", content);
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(json, headers);
        ResponseEntity<Object> response = restTemplate.postForEntity("http://212.122.192.216:8097/api/v1/email/send", request , Object.class );
        
        Object respons = response.getBody();
        return respons;
    }
}
