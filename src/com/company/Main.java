package com.company;
import Controllers.InvoiceGenController;
import Controllers.ProductCont;
import Controllers.customerCont;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // connect to  database
        // 1.publish the driver
        Scanner inputint = new Scanner(System.in);
        Scanner inputstr = new Scanner(System.in);


        //try {
        int choicemain = 0;
        DisplayMainOption();
        choicemain = inputint.nextInt();
        while ( (choicemain < 1)  || (choicemain > 5)){
            System.out.println("incorrect choice");
            DisplayMainOption();
            choicemain = inputint.nextInt();
        }
        while (choicemain != 5) {
            if (choicemain == 1) {
                ProductCont pc = new ProductCont();
                pc.start();
            }else if(choicemain == 2){
                customerCont cc = new customerCont();
                cc.start();
            }else if(choicemain == 3){
                InvoiceGenController ig = new InvoiceGenController();
            }else {
                try {
                    Readnwrite rw = new Readnwrite();
                    rw.ReadFile();
                }
                catch (IOException ex){
                    System.out.println("File doesn't seem to exist");
                }
            }


            DisplayMainOption();
            choicemain = inputint.nextInt();
            while ( (choicemain < 1)  || (choicemain > 5)){
                System.out.println("incorrect choice");
                DisplayMainOption();
                choicemain = inputint.nextInt();
            }
        }
    }

    private static void DisplayMainOption() {
        System.out.println("Enter your choice");
        System.out.println("1: Manage Products");
        System.out.println("2: Manage Customers");
        System.out.println("3: Invoice Generation");
        System.out.println("4: Admin Tasks");
        System.out.println("5 : Exit");
    }
}