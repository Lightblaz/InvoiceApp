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
}
