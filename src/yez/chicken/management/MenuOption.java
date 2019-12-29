package yez.chicken.management;

import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class MenuOption
{
    private Scanner scn = null;
    private DBManager db = null;
    private ResultSet rs = null;
    
    MenuOption()
    {
        scn = new Scanner(System.in);
        db = new DBManager();
        
        db.ConnectDB();
    }
    
    public void mainMenu()
    {
        System.out.printf(
                  "1) Menu Order\n"
                + "2) Menu Produk\n"
                + "0) Exit\n"
                + "================\n"
                + "Pilih > "
        );
    }
    
    public void orderMenu()
    {
        System.out.printf(
                  "1) Lihat Order\n"
                + "2) Tambah Order\n"
                + "3) Hapus Order\n"
                + "0) Kembali\n"
                + "================\n"
                + "Pilih > "
        );
    }
    
    public void showOrderMenu()
    {
        
    }
    
    public void addOrderMenu() throws SQLException
    {
        System.out.println("MASUKKAN DATA PELANGGAN");
        System.out.println("=======================");
        System.out.print("Nama     : ");
        String nama = scn.nextLine();
        
        System.out.print("No. Telp : ");
        String telp = scn.nextLine();
        
        System.out.print("Alamat   : ");
        String alamat = scn.nextLine();
        
        db.addPelanggan(nama, telp, alamat);
        
        System.out.println("PILIH ID PELAYAN");
        System.out.println("================");
        
        rs = db.fetchTable(DBManager.fetchTable.PELAYAN);
        
        int i = 1;
        while(rs.next())
        {
            System.out.println("[" + rs.getInt("ID_PELAYAN") + "] " + rs.getString("NAMA"));
        }
        
        System.out.println("================");
        System.out.print("Pilih > ");
        int id_pelayan = scn.nextInt();
    }
    
    public void delOrderMenu()
    {
    }
    
    public void productMenu()
    {
        System.out.printf(
                  "1) Lihat Produk\n"
                + "2) Tambah Produk\n"
                + "3) Hapus Produk\n"
                + "0) Kembali\n"
                + "================\n"
                + "Pilih > "
        );
    }
    
    public void showProductMenu()
    {
        
    }
    
    public void addProductMenu()
    {
        
    }
    
    public void delProductMenu()
    {
        
    }
    
    public static void clearScreen()
    {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}