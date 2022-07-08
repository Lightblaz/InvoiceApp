package Controllers;

import Data.DBConnector;
import com.company.Customer;
import com.company.InvoiceGeneration;
import com.company.Products;
import com.company.Readnwrite;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class InvoiceGenController {

    public InvoiceGenController() {
        displayoptions();
        ModifyInvoice();
    }

    public void ModifyInvoice(){

        //Declaration
        int choice = 0;
        Products p = new Products();
        ProductCont pc = new ProductCont();
        customerCont cc = new customerCont();
        String proname = "";
        String productlist = ""  , quanlist = "", pricelist = "" , totlist = "" , discountlist = "";
        int pquan = 0;
        double unitprice = 0.0 , tproductprice = 0.0 , pdiscountrate = 0.0 , pdiscount = 0.0;
        String startdate = "" , enddate = "";


        Scanner input = new Scanner(System.in);
        System.out.println("Enter your Choice");
        choice = input.nextInt();
        while (choice < 1 || choice > 6)
        {
            System.out.println("Wrong choice");
            choice = input.nextInt();
        }
        while (choice != 6) {
            try {
                //Products p = new Products();


                Customer c = new Customer();
                InvoiceGeneration in = new InvoiceGeneration();

                if (choice == 1 || (choice == 2)) {
                    System.out.println("Program initiated");
                    /*System.out.println("Enter Invoice NUmber");
                    int inNo = input.nextInt();
                    in.setInNo(inNo);*/

                    int inNo = getlastrInvoiceid();
                    in.setInNo(inNo);
                    System.out.println(inNo);
                    input.nextLine();


                    /*System.out.println("Enter Date");
                    String date = input.nextLine();*/
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dtf.format(now);

                    in.setIdate(date);
                    System.out.println("Enter Customer Name");
                    String cname = input.nextLine();
                    while(!cc.customerExist(cname)){
                        System.out.println("The name you entered doesn't exist in the database , try another name");
                        cname = input.nextLine();
                    }
                    in.setCusname(cname);
                    //Getting details for products
                    //p.Getallproducts();
                    pc.Getallproducts(p);
                    System.out.println("Enter the product name to add to the list");
                    proname = input.nextLine();
                    p.setProName(proname);

                    while(!proname.equals("exit"))
                    {

                        if(pc.searchProduct(p)){
                            System.out.println("Enter the product quantity");
                            pquan = input.nextInt();
                            if(pc.GetProductQuantity(p) > pquan){

                                if (productlist.equals("")){        //update the productlist with the product names
                                    productlist = productlist.concat(p.getProName());
                                }else{
                                    productlist = productlist + " , " + p.getProName();
                                }

                                if(quanlist.equals("")){        //update the product Quanity with the quantity
                                    quanlist = quanlist.concat(String.valueOf(pquan));
                                }else{
                                    quanlist = quanlist + " , " + pquan;
                                }

                                tproductprice = (pc.GetProductPrice(p)  * pquan);
                                pquan = (pc.GetProductQuantity(p) - pquan);
                                pc.UpdateProductQuantity(proname , pquan);

                                System.out.println("Enter the Product Discount");
                                pdiscountrate = input.nextDouble();
                                pdiscount = tproductprice * (pdiscountrate/100);
                                tproductprice = tproductprice * ((100.0 - pdiscountrate)/100);



                                if(totlist.equals("")){
                                    totlist = totlist.concat(Double.toString(tproductprice));
                                }else{
                                    totlist = totlist + " , " + (tproductprice);
                                }

                                if(discountlist.equals("")){
                                    discountlist = discountlist.concat(Double.toString(pdiscount));
                                }else{
                                    discountlist = discountlist + " , " + (pdiscount);
                                }

                                if(pricelist.equals(""))
                                {
                                    pricelist = pricelist.concat(Double.toString(p.GetProductPrice(proname)));
                                }else{
                                    pricelist = pricelist + " , " + p.GetProductPrice(proname);
                                }

                            }else{
                                System.out.println("Insufficient Quantity");
                            }
                        }else{
                            System.out.println("Product does not exist");
                        }

                        //p.Getallproducts();
                        pc.Getallproducts(p);
                        System.out.println("Enter the product name to add to the list");
                        input.nextLine();
                        proname = input.nextLine();
                    }

                    in.setPronames(productlist);
                    in.setUnitprices(pricelist);
                    in.setTotalprices(totlist);
                    in.setDiscounts(discountlist);
                    in.setProunits(quanlist);

                    if (choice == 1) {
                        //in.insertData(inNo, date, cname, productlist, quanlist, pricelist, totlist, discountlist);
                        insertData(in);
                    } else {
                        c.UpdateData(inNo, date, cname, productlist, pricelist, totlist, discountlist);
                    }
                } else if (choice == 3)
                {
                    System.out.println("Enter Customer Name");
                    input.nextLine();
                    String cname = input.nextLine();
                    in.displayCustomerdata(cname);
                } else if (choice == 4) {



                    input.nextLine();
                    System.out.println("Enter Start date");
                    startdate = input.nextLine();
                    System.out.println("Enter end date");
                    enddate = input.nextLine();
                    in.getDatedata(startdate , enddate);
                } else {
                    c.displayData();
                    displayCustomerdata(in);
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver not found");
            } catch (SQLException ex) {
                System.out.println(ex.getErrorCode());
                System.out.println("Database Error");
            } catch (Exception ex)
            {
                System.out.println("Input mismatch");
                input.nextLine();
                input.nextLine();
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

    public void displayoptions()
    {
        System.out.println("Select Correct number");
        System.out.println("1: Insert");
        //System.out.println("2: Update");
        System.out.println("3: Display Customer Invoices");
        System.out.println("4: Search Custommer Invoices based on Dates");
        //System.out.println("5: DisplayAll");
        System.out.println("6: Exit");
    }

    public void displayCustomerdata(InvoiceGeneration in) throws SQLException , ClassNotFoundException
    {

        boolean found = false;
        DBConnector.getDBConnection();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");

        Statement stat = con.createStatement();
        String queryString = "select * from invoicegen where CusName ='" + in.getCusname() + "'";
        ResultSet rs = stat.executeQuery(queryString);


        while (rs.next())
        {
            found = true;
            in.setInNo(rs.getInt("InvoiceNo"));
            in.setIdate(rs.getString("Indate"));
            in.setCusname(rs.getString("CusName"));
            in.setPronames(rs.getString("ProNames"));
            in.setProunits(rs.getString("ProUnits"));
            in.setUnitprices(rs.getString("UnitPrice"));
            in.setTotalprices(rs.getString("TotalPrice"));
            in.setDiscounts(rs.getString("Discount"));

            System.out.println("InvoiceNo = "+in.getInNo()+ " Date = "+in.getIdate()+" Customer Name = "+in.getCusname() + " Product list = " + in.getPronames() + " Quantity lists = " + in.getProunits() + " Price list = " + in.getUnitprices() + " Total price list = " + in.getTotalprices() + " Discount list = " + in.getDiscounts());
        }
        if(!found)
        {
            System.out.println("Customer Invoice not found");
        }
    }

    public void insertData(InvoiceGeneration in) throws SQLException, ClassNotFoundException , IOException
    {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "insert into invoicegen (InvoiceNo , Indate , CusName , ProNames , ProUnits , UnitPrice , TotalPrice , Discount) values ('" + in.getInNo() + "','" + in.getIdate() + "','" + in.getCusname() + "','" + in.getPronames() + "','" + in.getProunits() + "','" + in.getUnitprices() +"','" + in.getTotalprices() +"','" + in.getDiscounts()+"')";
        int i = stat.executeUpdate(queryString);
        if (i != 0)
        {
            System.out.println("Data inserted");
            Readnwrite rw = new Readnwrite();
            String logs = "inserted into Invoice (InvoiceNo = " + in.getInNo() + " , Indate = " + in.getIdate() + " , Cusname = " + in.getCusname() + " , Product Names = " + in.getPronames() + " , Product quantity = " + in.getProunits() + " , Unit price = " + in.getUnitprices() + " , Total price = " + in.getTotalprices() + " , Discount price = " + in.getDiscounts()  + ")";
            rw.WriteToFile(logs);
        }
    }

    public void getDatedata(String startDate , String enddate , InvoiceGeneration in) throws SQLException , ClassNotFoundException{

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
            in.setInNo(rs.getInt("InvoiceNo"));
            in.setIdate(rs.getString("Indate"));
            in.setCusname(rs.getString("CusName"));
            in.setPronames(rs.getString("ProNames"));
            in.setProunits(rs.getString("ProUnits"));
            in.setUnitprices(rs.getString("UnitPrice"));
            in.setTotalprices(rs.getString("TotalPrice"));
            in.setDiscounts(rs.getString("Discount"));

            System.out.println("InvoiceNo = "+in.getInNo()+ " Date = "+in.getIdate()+" Customer Name = "+in.getCusname() + " Product list = " + in.getPronames() + " Quantity lists = " + in.getProunits() + " Price list = " + in.getUnitprices() + " Total price list = " + in.getTotalprices() + " Discount list = " + in.getDiscounts());
        }
        if(!found)
        {
            System.out.println("Customer Invoice not found between these dates");
        }
    }

    public int getlastrInvoiceid() throws SQLException , ClassNotFoundException{
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //insert the values to the database table
        String queryString = "select * from invoicegen order by InvoiceNo desc limit 1";
        //System.out.println(queryString);
        ResultSet rs = stat.executeQuery(queryString);
        if (rs.next())
        {
            int inno = rs.getInt("InvoiceNo");
            return ++inno;
        }
        return 1;
    }
    public void deleteData(InvoiceGeneration in) throws SQLException , ClassNotFoundException , IOException
    {
        DBConnector.getDBConnection();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "");
        //create the statement to do CRUD(create, retrieve , update , delete)

        Statement stat = con.createStatement();

        //delete the values to the database table
        String queryString = "DELETE FROM invoicegen WHERE InvoiceNo ='" + in.getInNo() + "'";
        int i = stat.executeUpdate(queryString);

        if (i != 0) {
            System.out.println("Data Deleted");
            Readnwrite rw = new Readnwrite();
            String logs = "Deleted from Invoicegen where InvoiceNo = " + in.getInNo();
            rw.WriteToFile(logs);
        }
        stat.close();
        con.close();
    }
}
