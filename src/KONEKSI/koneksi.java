/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KONEKSI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import com.mysql.jdbc.Driver;


/**
 *
 * @author hp
 */
public class koneksi {
    Connection sambungan;
    
    public void sambung(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Sukses Driver JDBC ditemukan..");
            try {
                String url = "jdbc:mysql://localhost:3306/barbershop?user=root&password=daniel";
                sambungan = DriverManager.getConnection(url);
                System.out.println("Sukses koneksi = ");
            }catch (SQLException se){
                System.out.println("GAGAL koneksi = "+ se);
                System.exit(0);
            }
        }
        catch (ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, "Class Tidak Ditemukan.. Error : "+cnfe);
            System.exit(0);
        }
    }
    
    /*
    Connection sambung;
    public Connection getConnection(){
        try {
            sambung = DriverManager.getConnection("jdbc:mysql://localhost/barbershop","root","daniel");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Koneksi Database GAGAL.. !!", "informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        return sambung;
    }*/
}