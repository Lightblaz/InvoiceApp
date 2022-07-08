package Controllers;

import Data.DBConnector;
import com.company.Products;
import com.company.Readnwrite;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductCont {

    public void start(){
        displayoptions();
        ModifyProduct();
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

    public void ModifyProduct(){
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
                Products p = new Products();
                Readnwrite rw = new Readnwrite();
                Getallproducts(p);
                if (choice == 1 || (choice == 2)) {
                    System.out.println("Program initiated");
                    System.out.println("Enter ProductID");
                    int pid = input.nextInt();
                    p.setProID(pid);
                    input.nextLine();
                    System.out.println("Enter Product Name");
                    String pname = input.nextLine();
                    p.setProName(pname);
                    System.out.println("Enter Product Description");
                    String Des = input.nextLine();
                    p.setPdes(Des);
                    System.out.println("Enter Product Price");
                    double pprice = input.nextDouble();
                    p.setPprice(pprice);
                    System.out.println("Enter Product selling Price");
                    double sprice = input.nextDouble();
                    p.setSpric(sprice);
                    System.out.println("Enter Product Quantity");
                    int quan = input.nextInt();
                    p.setQuan(quan);
                    if (choice == 1) {
                        //p.insertData(pid, pname, Des, pprice, sprice, quan);
                        insertData(p);
                    } else {
                        //p.UpdateData(pid, pname, Des, pprice, sprice, quan);
                        UpdateData(p);
                    }
                } else if (choice == 3) {
                    System.out.println("Enter ProductID");
                    int pid = input.nextInt();
                    p.setProID(pid);
                    //p.deleteData(pid);
                    deleteData(p);
                } else if (choice == 4) {
                    System.out.println("Enter ProductID");
                    int pid = input.nextInt();
                    p.setProID(pid);
                    //p.searchData(pid);
                    searchData(p);
                } else {
                    displaydata(p);
                    //p.displayData();
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver not found");
            } catch (SQLException ex) {
                System.out.println(ex.getErrorCode());
                System.out.println("Database Error");
            } catch (IOException ex){
                System.out.println("The file to write doesn't seem to exist");
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

    public void displaydata(Products p)  throws SQLException , ClassNotFoundException{
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from product";
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next()){

            p.setProID(rs.getInt("ProductId"));
            p.setProName(rs.getString("ProductName"));
            p.setPdes(rs.getString("Description"));
            p.setPprice(rs.getDouble("PurchasePrice"));
            p.setSpric(rs.getDouble("SellingPrice"));
            p.setQuan(rs.getInt("Quantity"));
            System.out.println("ProductId = "+ p.getProID()+ " ProductName = "+ p.getProName() + " Description = "+ p.getPdes() + " PurchasePrice = " + p.getPprice() + " Selling Price = " + p.getSpric() + " Quanity = " + p.getQuan());
        }
    }

    public void insertData(Products p) throws SQLException , ClassNotFoundException , IOException {

        String logs = "inserted into product (ProductId = " + p.getProID() + ", ProductName = " +  p.getProName() + ", Description = " +  p.getPdes() + ", PurchasePrice = " +  p.getPprice() + ", SellingPrice = " +  p.getSpric() + ", Quantity = " + p.getQuan() + ")\n";
        Readnwrite rw = new Readnwrite();
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();
        //insert the values to the database table
        String queryString = "insert into product (ProductId , ProductName , Description , PurchasePrice , SellingPrice , Quantity) values ('" + p.getProID() + "','" + p.getProName() + "','" + p.getPdes() + "','" + p.getPprice() + "','" + p.getSpric() + "','" + p.getQuan() +"')";
        rw.WriteToFile(logs);
        int i = stat.executeUpdate(queryString);
        if (i != 0) {
            System.out.println("Data inserted");
        }
    }

    public void searchData(Products p) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        boolean found = false;
        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "SELECT * FROM product WHERE ProductId='" + p.getProID() + "'";
        ResultSet rs = stat.executeQuery(queryString);
        while (rs.next()) {
            found = true;
            p.setProID(rs.getInt("ProductId"));
            p.setProName(rs.getString("ProductName"));
            p.setPdes(rs.getString("Description"));
            p.setPprice(rs.getDouble("PurchasePrice"));
            p.setSpric(rs.getDouble("SellingPrice"));
            p.setQuan(rs.getInt("Quantity"));
            System.out.println("ProductId = " + p.getProID() + " ProductName = " + p.getProName() + " Description = " + p.getPdes() + " PurchasePrice = " + p.getPprice() + " Selling Price = " + p.getSpric() + " Quanity = " + p.getQuan());
        }

        if(!found){
            System.out.println("Data not found");
        }
        stat.close();
        con.close();
    }


    public void UpdateData(Products p) throws SQLException , ClassNotFoundException , IOException{

        String logs = "updated into product (ProductId = " + p.getProID() + ", ProductName = " +  p.getProName() + ", Description = " +  p.getPdes() + ", PurchasePrice = " +  p.getPprice() + ", SellingPrice = " +  p.getSpric() + ", Quantity = " + p.getQuan() + ")\n";
        Readnwrite rw = new Readnwrite();
        rw.WriteToFile(logs);

        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "update product set ProductName = '"+p.getProName()+"', Description = '"+ p.getPdes() + "', PurchasePrice = '" + p.getPprice() + "', SellingPrice = '" + p.getSpric() + "', Quantity = '" + p.getQuan() + "' where ProductId='" + p.getProID() + "'";
        System.out.println(queryString);
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data updated");
        }
        stat.close();
        con.close();
    }

    public void deleteData(Products p) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "DELETE FROM product WHERE ProductId='" + p.getProID() + "'";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data Deleted");
        }
        stat.close();
        con.close();
    }

    public void Getallproducts(Products p)throws SQLException , ClassNotFoundException
    {

        //ArrayList<String> Prod = new ArrayList<String>();

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
            p.setProName(rs.getString("ProductName"));
            //Prod.add(p.getProName());
            System.out.println(" ProductName = "+ p.getProName());
            //System.out.println(Prod.get(0));
        }
        stat.close();
        con.close();
    }

    public boolean searchProduct(Products p) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT * FROM product WHERE ProductName = '" + p.getProName() + "'";
        ResultSet rs = stat.executeQuery(queryString);
        if(!rs.next()){
            System.out.println("Data not found");
            return false;
        }else{
            return true;
        }
    }

    public int GetProductQuantity(Products p) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT Quantity FROM product WHERE ProductName = '" + p.getProName() + "'";
        ResultSet rs = stat.executeQuery(queryString);
        int quan = 0;
        if (rs.next()) {
            quan = rs.getInt("Quantity");
        }
        return quan;
    }

    public double GetProductPrice(Products p) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "SELECT SellingPrice FROM product WHERE ProductName = '" + p.getProName() + "'";
        ResultSet rs = stat.executeQuery(queryString);
        double price = 0;
        if (rs.next()) {
            price = rs.getDouble("SellingPrice");
        }
        return price;
    }

    public void UpdateProductQuantity(String pname , int quan) throws SQLException , ClassNotFoundException {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        String queryString = "update product set Quantity = '" + quan + "' WHERE ProductName = '" + pname + "'";
        int i = stat.executeUpdate(queryString);
        if (i != 0) {
            System.out.println("Product Quantity Updated");
        }
    }


}
