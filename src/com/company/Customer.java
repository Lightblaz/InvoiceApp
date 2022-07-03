package com.company;

import Data.DBConnector;

import java.sql.*;

public class Customer {


    private int cid;
    private String cname;
    private String email;
    private String address;
    private String contactnum;
    private String DOB;
    private String gender;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void insertData(int cid , String cname , String email , String Address , String ConNumber , String birth , String gender) throws SQLException, ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "insert into customer (CustomerId , CustomerName , Email , Address , ContactNumber , DOB , Gender) values ('" + cid + "','" + cname + "','" + email + "','" + Address + "','" + ConNumber + "','" + birth +"','" + gender +"')";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data inserted");
        }
    }

    public void displayData() throws SQLException , ClassNotFoundException{
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from customer";
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next()){

            int cid = rs.getInt("CustomerId");
            String cname = rs.getString("CustomerName");
            String email = rs.getString("Email");
            String Address = rs.getString("Address");
            String ConNumber = rs.getString("ContactNumber");
            String birth = rs.getString("DOB");
            String gen = rs.getString("Gender");

            System.out.println("CustomerId = "+cid+ " CustomerName = "+cname+" Email = "+email + " Address = " + Address + " ContactNumber = " + ConNumber + " DateofBirth = " + birth + "  Gender = " + gen);
        }
    }
    public void searchData(int cid) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "SELECT * FROM customer WHERE CustomerId='" + cid + "'";
        //System.out.println(queryString);
        //int i = stat.executeUpdate(queryString);
        ResultSet rs = stat.executeQuery(queryString);
        //System.out.println(rs.getString("first_name"));
        while (rs.next()){

            cid = rs.getInt("CustomerId");
            String cname = rs.getString("CustomerName");
            String email = rs.getString("Email");
            String Address = rs.getString("Address");
            String ConNumber = rs.getString("ContactNumber");
            String birth = rs.getString("DOB");
            String gen = rs.getString("Gender");

            System.out.println("CustomerId = "+cid+ " CustomerName = "+cname+" Email = "+email + " Address = " + Address + " ContactNumber = " + ConNumber + " DateofBirth = " + birth + "  Gender = " + gen);
        }
        stat.close();
        con.close();
    }

    public void UpdateData(int cid , String cname , String email , String address , String ConNumber , String Birth , String gen) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "update customer set CustomerName = '"+cname+"', Email = '"+ email + "', Address = '" + address + "', ContactNumber = '" + ConNumber + "', DOB = '" + Birth + "'," + " Gender = '" + gen+ "' where CustomerId='" + cid + "'";
        System.out.println(queryString);
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data updated");
        }
        stat.close();
        con.close();
    }

    public void deleteData(int cid) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "DELETE FROM customer WHERE CustomerId='" + cid + "'";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data Deleted");
        }
        stat.close();
        con.close();
    }
}
