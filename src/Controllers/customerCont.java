package Controllers;

import Data.DBConnector;
import com.company.Customer;
import com.company.Products;
import com.company.Readnwrite;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class customerCont {
    public void start(){
        displayoptions();
        ModifyCustomer();
    }

    public void displayoptions(){
        System.out.println("Select Correct number");
        System.out.println("1: Insert");
        System.out.println("2: Update");
        System.out.println("3: Delete");
        System.out.println("4: Search");
        System.out.println("5: DisplayAll");
        System.out.println("6: Exit");
    }

    public void ModifyCustomer(){
        int choice = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your Choice");
        choice = input.nextInt();
        while (choice < 1 || choice > 6){
            System.out.println("Wrong choice");
            choice = input.nextInt();
        }
        while (choice != 6) {
            try {
                //Products p = new Products();
                Customer c = new Customer();
                if (choice == 1 || (choice == 2)) {
                    System.out.println("Program initiated");
                    System.out.println("Enter Customer ID");
                    int cid = input.nextInt();
                    c.setCid(cid);
                    input.nextLine();
                    System.out.println("Enter Customer Name");
                    String cname = input.nextLine();
                    c.setCname(cname);
                    System.out.println("Enter Customer Email");
                    String email = input.nextLine();
                    c.setEmail(email);
                    System.out.println("Enter Customer Address");
                    String address = input.nextLine();
                    c.setAddress(address);
                    System.out.println("Enter Contact Number");
                    String ConNumber = input.nextLine();
                    c.setContactnum(ConNumber);
                    System.out.println("Enter Date of Birth");
                    String birth = input.nextLine();
                    c.setDOB(birth);
                    System.out.println("Enter gender");
                    String gen = input.nextLine();
                    c.setGender(gen);
                    if (choice == 1) {
                        //c.insertData(cid, cname, email, address, ConNumber, birth, gen);
                        insertData(c);
                    } else {
                        //c.UpdateData(cid, cname, email, address, ConNumber, birth , gen);
                        UpdateData(c);
                    }
                } else if (choice == 3) {
                    System.out.println("Enter Customer ID");
                    int cid = input.nextInt();
                    c.setCid(cid);
                    //c.deleteData(cid);
                    deleteData(c);
                } else if (choice == 4) {
                    System.out.println("Enter CustomerID");
                    int cid = input.nextInt();
                    c.setCid(cid);
                    //c.searchData(cid);
                    searchData(c);
                } else {
                    //c.displayData();
                    displayData(c);
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver not found");
            } catch (SQLException ex) {
                System.out.println("Database Error");
            } catch (IOException ex){
                System.out.println("File doesn't seem to exist");
            }
            System.out.println();
            displayoptions();
            System.out.println("Enter your Choice");
            choice = input.nextInt();
            while (choice < 1 || choice > 6){
                System.out.println("Wrong choice");
                choice = input.nextInt();
            }
            System.out.println();
        }
    }

    public void displayData(Customer c) throws SQLException , ClassNotFoundException{
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from customer";
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next()){

            c.setCid(rs.getInt("CustomerId"));
            c.setCname(rs.getString("CustomerName"));
            c.setEmail(rs.getString("Email"));
            c.setAddress(rs.getString("Address"));
            c.setContactnum(rs.getString("ContactNumber"));
            c.setDOB(rs.getString("DOB"));
            c.setGender(rs.getString("Gender"));

            System.out.println("CustomerId = "+c.getCid()+ " CustomerName = "+c.getCname()+" Email = "+c.getEmail() + " Address = " + c.getAddress() + " ContactNumber = " + c.getContactnum() + " DateofBirth = " + c.getDOB() + "  Gender = " + c.getGender());
        }
    }

    public void searchData(Customer c) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "SELECT * FROM customer WHERE CustomerId='" + c.getCid() + "'";
        //System.out.println(queryString);
        //int i = stat.executeUpdate(queryString);
        ResultSet rs = stat.executeQuery(queryString);
        //System.out.println(rs.getString("first_name"));
        while (rs.next()){

            c.setCid(rs.getInt("CustomerId"));
            c.setCname(rs.getString("CustomerName"));
            c.setEmail(rs.getString("Email"));
            c.setAddress(rs.getString("Address"));
            c.setContactnum(rs.getString("ContactNumber"));
            c.setDOB(rs.getString("DOB"));
            c.setGender(rs.getString("Gender"));

            System.out.println("CustomerId = "+c.getCid()+ " CustomerName = "+c.getCname()+" Email = "+c.getEmail() + " Address = " + c.getAddress() + " ContactNumber = " + c.getContactnum() + " DateofBirth = " + c.getDOB() + "  Gender = " + c.getGender());
        }
        stat.close();
        con.close();
    }

    public void insertData(Customer c) throws SQLException, ClassNotFoundException , IOException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)
        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "insert into customer (CustomerId , CustomerName , Email , Address , ContactNumber , DOB , Gender) values ('" + c.getCid() + "','" + c.getCname() + "','" + c.getEmail() + "','" + c.getAddress() + "','" + c.getContactnum() + "','" + c.getDOB() +"','" + c.getGender() +"')";
        int i = stat.executeUpdate(queryString);
        if (i != 0) {
            System.out.println("Data inserted");
            Readnwrite rw = new Readnwrite();
            String logs = "inserted into customer (CustomerId = " + c.getCid() + " , CustomerName = " + c.getCname() + " , Email = " + c.getEmail() + " , Address = " + c.getAddress() + " , ContactNumber = " + c.getContactnum() + " , DOB = " + c.getDOB() + " , Gender = " + c.getGender() + ")\n";
            rw.WriteToFile(logs);
        }
    }

    public void UpdateData(Customer c) throws SQLException , ClassNotFoundException , IOException{
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();


        //delete the values to the database table
        String queryString = "update customer set CustomerName = '"+c.getCname()+"', Email = '"+ c.getEmail() + "', Address = '" + c.getAddress() + "', ContactNumber = '" + c.getContactnum() + "', DOB = '" + c.getDOB() + "'," + " Gender = '" + c.getGender() + "' where CustomerId='" + c.getCid() + "'";
        System.out.println(queryString);
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data updated");
            Readnwrite rw = new Readnwrite();
            String logs = "Updated  customer  where CustomerId = " + c.getCid() + " and these values updated , CustomerName = " + c.getCname() + " , Email = " + c.getEmail() + " , Address = " + c.getAddress() + " , ContactNumber = " + c.getContactnum() + " , DOB = " + c.getDOB() + " , Gender = " + c.getGender() + ")\n";
            rw.WriteToFile(logs);
        }
        stat.close();
        con.close();
    }

    public void deleteData(Customer c) throws SQLException , ClassNotFoundException , IOException{
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "DELETE FROM customer WHERE CustomerId='" + c.getCid() + "'";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data Deleted");
            Readnwrite rw = new Readnwrite();
            String logs = "Deleted from customer where CustomerId = " + c.getCid();
            rw.WriteToFile(logs);
        }
        stat.close();
        con.close();
    }
    public boolean customerExist(String customerName)throws SQLException , ClassNotFoundException , IOException
    {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        Statement stat = con.createStatement();
        String queryString = "select * FROM customer WHERE CustomerName='" + customerName + "'";
        ResultSet rs = stat.executeQuery(queryString);
        if (rs.next()){
            return true;
        }
        stat.close();
        con.close();
        return false;
    }
}
