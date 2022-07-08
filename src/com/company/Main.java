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

            /*System.out.println("Program initiated");
            System.out.println("Enter ProductID");
            int pid = inputint.nextInt();
            System.out.println("Enter Product Name");
            String pname = inputstr.nextLine();
            System.out.println("Enter Product Description");
            String Des = inputstr.nextLine();
            System.out.println("Enter Product Price");
            double pprice = inputint.nextDouble();
            System.out.println("Enter Product selling Price");
            double sprice = inputint.nextDouble();
            System.out.println("Enter Product Quantity");
            int quan = inputint.nextInt();
            Products p = new Products();
            p.insertData(pid , pname , Des , pprice , sprice , quan);
            Products p = new Products();
            //p.displayData();
            //p.searchData(5);
            //p.UpdateData(5 , "cream","good for you" , 6.7 , 8.9 , 90);
            //p.insertData(6 , "home","stay safe" , 6.7 , 8.9 , 50);
            p.deleteData(6);
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            System.out.println("Database Error");
        }*/
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