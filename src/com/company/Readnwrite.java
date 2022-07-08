package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Readnwrite {

    public void WriteToFile(String logs) throws IOException {
        FileWriter fw=new FileWriter("Adminlogs.txt" , true);

        // read character wise from string and write
        // into FileWriter
        fw.write("\n");
        for (int i = 0; i < logs.length(); i++)
            fw.write(logs.charAt(i));

        System.out.println("Writing successful");
        //close the file
        fw.close();
    }

    public void ReadFile() throws  IOException {
        File myObj = new File("Adminlogs.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
    }
}
