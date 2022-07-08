package com.company;
import Data.DBConnector;
import java.sql.*;
import java.util.ArrayList;

public class Products {

    private int proID;
    private String ProName;
    private String pdes;
    private double pprice;
    private double spric;
    private int quan;

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public double getPprice() {
        return pprice;
    }

    public String getPdes() {
        return pdes;
    }

    public void setPdes(String pdes) {
        this.pdes = pdes;
    }

    public void setPprice(double pprice) {
        this.pprice = pprice;
    }

    public double getSpric() {
        return spric;
    }

    public void setSpric(double spric) {
        this.spric = spric;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    public double GetProductPrice(String pname) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT SellingPrice FROM product WHERE ProductName = '" + pname + "'";
        ResultSet rs = stat.executeQuery(queryString);
        double price = 0;
        if (rs.next()) {
            price = rs.getDouble("SellingPrice");
        }
        return price;
    }
}
