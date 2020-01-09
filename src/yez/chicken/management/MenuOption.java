package yez.chicken.management;

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
        System.out.println("1) Menu Pelanggan");
        System.out.println("2) Menu Orders");
        System.out.println("3) Menu Detail Orders");
        System.out.println("4) Menu Pelayan");
        System.out.println("5) Menu Produk");
        System.out.println("0) Exit");
        System.out.println("=====================");
        System.out.println("Pilih > ");
    }
    
    // MENU PELANGAN FUNCTION
    
    public void pelangganMenu()
    {
        System.out.println("MENU EDIT DATA PELANGGAN");
        System.out.println("======================");
        System.out.println("1) Tambah Data Pelanggan");
        System.out.println("2) Tampil Data Pelanggan");
        System.out.println("3) Hapus Data Pelangan");
        System.out.println("0) Kembali");
        System.out.println("======================");
        System.out.println("Pilih > ");
    }
    
    public void tambahPelanggan()
    {
        System.out.println("TAMBAH DATA PELANGGAN");
        System.out.println("=====================");
        System.out.print("Masukkan Nama     : ");
        String nama = scn.nextLine();
        
        System.out.print("Masukkan No. Telp : ");
        String telp = scn.nextLine();
        
        System.out.print("Masukkan Alamat   : ");
        String alamat = scn.nextLine();
        
        // Add data to the PELANGGAN table
        try{ db.addRowPelanggan(nama, telp, alamat); }catch(SQLException e){ System.err.println(e.getClass().getName() + ": " + e.getMessage()); }
    }
    
    public void tampilPelanggan()
    {
        System.out.println("TAMPIL DATA PELANGGAN");
        System.out.println("=====================");
        System.out.println();
        
        // Fetch table PELANGGAN
        try
        { 
            rs = db.fetchTable(TableName.PELANGGAN);
            
            System.out.printf("%s", new String(new char[71]).replace("\0", "="));
            System.out.println();
            System.out.printf("|| %-20.20s|| %-20.20s|| %-20.20s||\n", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s", new String(new char[71]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[71]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[71]).replace("\0", "="));
            System.out.println();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
    }
    
    public void hapusPelanggan()
    {
        System.out.println("HAPUS DATA PELANGGAN");
        System.out.println("=====================");
    }
    
    // MENU ORDERS FUNCTION
    
    public void orderMenu()
    {
        System.out.println("MENU EDIT DATA ORDERS");
        System.out.println("=====================");
        System.out.println("1) Tambah Orders");
        System.out.println("2) Tampilkan Orders");
        System.out.println("3) Hapus Orders");
        System.out.println("0) Kembali");
        System.out.println("=====================");
        System.out.println("Pilih > ");
    }
    
    public void tambahOrders()
    {
        
    }
    
    public void tampilOrders()
    {
        
    }
    
    public void hapusOrders()
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
        
        db.addRowPelanggan(nama, telp, alamat);
        
        System.out.println("PILIH ID PELAYAN");
        System.out.println("================");
        
        rs = db.fetchTable(TableName.PELAYAN);
        
        int i = 1;
        while(rs.next())
        {
            System.out.println("[" + rs.getInt("ID_PELAYAN") + "] " + rs.getString("NAMA"));
        }
        
        System.out.println("================");
        System.out.print("Pilih > ");
        int id_pelayan = scn.nextInt();
    }
    
    // MENU DETAIL_ORDERS FUNCTION
    
    public void detailOrdersMenu()
    {
        System.out.println("MENU EDIT DATA DETAIL ORDERS");
        System.out.println("============================");
        System.out.println("1) Tambah Data Detail Orders");
        System.out.println("2) Tampil Data Detail Orders");
        System.out.println("3) Hapus Data Detail Orders");
        System.out.println("0) Kembali");
        System.out.println("============================");
        System.out.println("Pilih > ");
    }
    
    public void tambahDetailOrders()
    {
        
    }
    
    public void tampilDetailOrders()
    {
        
    }
    
    public void hapusDetailOrders()
    {
        
    }
    
    // MENU PELAYAN FUNCTION
    
    public void pelayanMenu()
    {
        System.out.println("MENU EDIT DATA PELAYAN");
        System.out.println("======================");
        System.out.println("1) Tambah Data Pelayan");
        System.out.println("2) Tampil Data Pelayan");
        System.out.println("3) Hapus Data Pelayan");
        System.out.println("0) Kembali");
        System.out.println("======================");
        System.out.println("Pilih > ");
    }
    
    public void tambahPelayan()
    {
        
    }
    
    public void tampilPelayan()
    {
        
    }
    
    public void hapusPelayan()
    {
        
    }
    
    // MENU PRODUCT FUNCTION
    
    public void produkMenu()
    {
        System.out.println("MENU EDIT DATA PRODUK");
        System.out.println("=====================");
        System.out.println("1) Tambah Data Produk");
        System.out.println("2) Tampil Data Produk");
        System.out.println("3) Hapus Data Produk");
        System.out.println("0) Kembali");
        System.out.println("=====================");
        System.out.println("Pilih > ");
    }
    
    public void tambahProduk()
    {
        
    }
    
    public void tampilProduk()
    {
        
    }
    
    public void hapusProduk()
    {
        
    }
    
    public static void clearScreen()
    {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}