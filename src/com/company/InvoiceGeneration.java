package com.company;

import Data.DBConnector;

import java.sql.*;

public class InvoiceGeneration {

    private int inNo;
    private String idate;
    private String cusname;
    private String Pronames;
    private String prounits;
    private String unitprices;
    private String totalprices;
    private String discounts;

    public int getInNo() {
        return inNo;
    }

    public void setInNo(int inNo) {
        this.inNo = inNo;
    }

    public String getIdate() {
        return idate;
    }

    public void setIdate(String idate) {
        this.idate = idate;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getPronames() {
        return Pronames;
    }

    public void setPronames(String pronames) {
        Pronames = pronames;
    }

    public String getProunits() {
        return prounits;
    }

    public void setProunits(String prounits) {
        this.prounits = prounits;
    }

    public String getUnitprices() {
        return unitprices;
    }

    public void setUnitprices(String unitprices) {
        this.unitprices = unitprices;
    }

    public String getTotalprices() {
        return totalprices;
    }

    public void setTotalprices(String totalprices) {
        this.totalprices = totalprices;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }


    public void insertData(int inNo , String date , String cname , String productNames , String proUnits , String unitprice , String totalprice , String discount ) throws SQLException, ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "insert into invoicegen (InvoiceNo , Indate , CusName , ProNames , ProUnits , UnitPrice , TotalPrice , Discount) values ('" + inNo + "','" + date + "','" + cname + "','" + productNames + "','" + proUnits + "','" + unitprice +"','" + totalprice +"','" + discount+"')";
        int i = stat.executeUpdate(queryString);
        if (i != 0) {
            System.out.println("Data inserted");
        }
    }
    public void displayCustomerdata(String cname) throws SQLException , ClassNotFoundException{

        boolean found = false;
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from invoicegen where CusName ='" + cname + "'";
        ResultSet rs = stat.executeQuery(queryString);


        while (rs.next())
        {
            found = true;
            int inNo = rs.getInt("InvoiceNo");
            String date = rs.getString("Indate");
            String cusname = rs.getString("CusName");
            String pronames = rs.getString("ProNames");
            String prounits = rs.getString("ProUnits");
            String proprice = rs.getString("UnitPrice");
            String totprice = rs.getString("TotalPrice");
            String dis = rs.getString("Discount");

            System.out.println("InvoiceNo = "+inNo+ " Date = "+date+" Customer Name = "+cusname + " Product list = " + pronames + " Quantity lists = " + prounits + " Price list = " + proprice + " Total price list = " + totprice + " Discount list = " + dis);
        }

        if(!found)
        {
            System.out.println("Customer Invoice not found");
        }
    }

    public void getDatedata(String startDate , String enddate) throws SQLException , ClassNotFoundException{

        boolean found = false;
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from invoicegen where Indate between '" + startDate +"' and '" + enddate +"'";
        System.out.println(queryString);
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next())
        {
            found = true;
            int inNo = rs.getInt("InvoiceNo");
            String date = rs.getString("Indate");
            String cusname = rs.getString("CusName");
            String pronames = rs.getString("ProNames");
            String prounits = rs.getString("ProUnits");
            String proprice = rs.getString("UnitPrice");
            String totprice = rs.getString("TotalPrice");
            String dis = rs.getString("Discount");

            System.out.println("InvoiceNo = "+inNo+ " Date = "+date+" Customer Name = "+cusname + " Product list = " + pronames + " Quantity lists = " + prounits + " Price list = " + proprice + " Total price list = " + totprice + " Discount list = " + dis);
        }

        if(!found)
        {
            System.out.println("Customer Invoice not found between these dates");
        }
    }
}
