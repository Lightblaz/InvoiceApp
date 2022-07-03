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

    public void displayData() throws SQLException , ClassNotFoundException{
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from product";
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next()){

            int pid = rs.getInt("ProductId");
            String pname = rs.getString("ProductName");
            String Des = rs.getString("Description");
            double pprice = rs.getDouble("PurchasePrice");
            double sprice = rs.getDouble("SellingPrice");
            int quan = rs.getInt("Quantity");

            System.out.println("ProductId = "+pid+ " ProductName = "+pname+" Description = "+Des + " PurchasePrice = " + pprice + " Selling Price = " + sprice + " Quanity = " + quan);
        }
    }

    public void insertData(int pid , String pname , String des , double pprice , double sprice , int quan) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "insert into product (ProductId , ProductName , Description , PurchasePrice , SellingPrice , Quantity) values ('" + pid + "','" + pname + "','" + des + "','" + pprice + "','" + sprice + "','" + quan
                +"')";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data inserted");
        }
    }

    public void searchData(int pid) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        boolean found = false;
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "SELECT * FROM product WHERE ProductId='" + pid + "'";
        ResultSet rs = stat.executeQuery(queryString);
        if(!rs.next()){
            System.out.println("Data not found");
        }
        while (rs.next()){
            found   = true;
            int id = rs.getInt("ProductId");
            String pname = rs.getString("ProductName");
            String Des = rs.getString("Description");
            double pprice = rs.getDouble("PurchasePrice");
            double sprice = rs.getDouble("SellingPrice");
            int quan = rs.getInt("Quantity");

            System.out.println("ProductId = "+id+ " ProductName = "+pname+" Description = "+Des + " PurchasePrice = " + pprice + " Selling Price = " + sprice + " Quanity = " + quan);
        }
        stat.close();
        con.close();
    }

    public void UpdateData(int pid , String pname , String des , double pprice , double sprice , int quan) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "update product set ProductName = '"+pname+"', Description = '"+ des + "', PurchasePrice = '" + pprice + "', SellingPrice = '" + sprice + "', Quantity = '" + quan + "' where ProductId='" + pid + "'";
        System.out.println(queryString);
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data updated");
        }
        stat.close();
        con.close();
    }

    public void deleteData(int pid) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "DELETE FROM product WHERE ProductId='" + pid + "'";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data Deleted");
        }
        stat.close();
        con.close();
    }
    /*public void Getallproducts()throws SQLException , ClassNotFoundException{

        ArrayList<String> Prod = new ArrayList<String>();

        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "SELECT ProductName FROM product group by ProductName";
        //System.out.println(queryString);
        //int i = stat.executeUpdate(queryString);
        ResultSet rs = stat.executeQuery(queryString);
        //System.out.println(rs.getString("first_name"));
        while (rs.next()){
            String pname = rs.getString("ProductName");
            Prod.add(pname);
            System.out.println(" ProductName = "+pname);
            //System.out.println(Prod.get(0));
        }
        stat.close();
        con.close();
    }*/


    public boolean searchProduct(String pname) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT * FROM product WHERE ProductName = '" + pname + "'";
        ResultSet rs = stat.executeQuery(queryString);
        if(!rs.next()){
            System.out.println("Data not found");
            return false;
        }else{
            return true;
        }
    }

    public int GetProductQuantity(String pname) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT Quantity FROM product WHERE ProductName = '" + pname + "'";
        ResultSet rs = stat.executeQuery(queryString);
        int quan = 0;
        if (rs.next()) {
            quan = rs.getInt("Quantity");
        }
        return quan;
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

    /*public void UpdateProductQuantity(String pname , int quan) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "update product set Quantity = '" + quan + "' WHERE ProductName = '" + pname + "'";
        int i = stat.executeUpdate(queryString);
        if (i != 0) {
            System.out.println("Product Quantity Updated");
        }
    }*/
}
