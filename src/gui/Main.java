package gui;

import api.Car;
import api.Employee;
import api.RentalService;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RentalService service;
        try {
            service = RentalService.loadData();
        } catch (Exception s){
            try{
                service=new RentalService();
                service.saveData();
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        new LoginFrame(service).setVisible(true);
    }
}