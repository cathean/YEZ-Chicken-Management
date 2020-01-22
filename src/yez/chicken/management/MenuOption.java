package yez.chicken.management;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuOption 
{
    private BufferedReader scn = null;
    private Scanner brk = null;
    private DBManager db = null;
    private ResultSet rs = null;
    
    MenuOption()
    {
        scn = new BufferedReader(new InputStreamReader(System.in));
        brk = new Scanner(System.in);
        db = new DBManager();
        
        db.ConnectDB();
    }
    
    public void showLogo()
    {
        try
        {
            FileReader fr = new FileReader("logo.dat");
            int i;

            while((i = fr.read()) != -1)
                System.out.print((char)i);
            
            System.out.println();
            System.out.printf("%s", new String(new char[85]).replace("\0", "~"));
            System.out.println();
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    public void mainMenu()
    {
        System.out.println("OPERASI TRANSAKSI");
        System.out.println("=====================");
        System.out.println("11) Lakukan Transaksi Baru");
        System.out.println("22) Tampil Transaksi");
        System.out.println("33) Hapus Transaksi");
        System.out.println("=====================");
        System.out.println("OPERASI DATA TABLE");
        System.out.println("=====================");
        System.out.println("1) Menu Pelanggan");
        System.out.println("2) Menu Orders");
        System.out.println("3) Menu Detail Orders");
        System.out.println("4) Menu Pelayan");
        System.out.println("5) Menu Produk");
        System.out.println("0) Exit");
        System.out.println("=====================");
        System.out.print("Pilih > ");
    }
    
    // MENU TRANSAKSI
    
    public void tambahTransaksi() throws InterruptedException
    {
        try
        {
            // TAMBAH DATA PELANGGAN
            System.out.println("TAMBAH DATA PELANGGAN");
            System.out.println("=====================");
            System.out.print("Masukkan Nama     : ");
            String nama = scn.readLine();

            System.out.print("Masukkan No. Telp : ");
            String telp = scn.readLine();

            System.out.print("Masukkan Alamat   : ");
            String alamat = scn.readLine();
        
            // Add data to the PELANGGAN table
            db.addRowPelanggan(nama, telp, alamat);
            System.out.println("Data berhasil ditambahkan!");
            brk.nextLine();
            
            clearScreen();
            showLogo();
            
            // TAMBAH DATA ORDERS
            System.out.println("TAMBAH DATA ORDERS");
            System.out.println("==================");
            int id_pelanggan = db.fetchLastRowID(TableName.PELANGGAN);
            rs = db.fetchTable(TableName.PELAYAN);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Daya yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            System.out.print("Masukkan ID Pelayan   : ");
            int id_pelayan = Integer.parseInt(scn.readLine());
            
            // Add data to the ORDERS table
            db.addRowOrders(id_pelanggan, id_pelayan);
            System.out.println("Data berhasil ditambahkan!");
            brk.nextLine();
            
            int id_orders = db.fetchLastRowID(TableName.ORDERS);
            
            int opt = 0;
            do
            {
                clearScreen();
                showLogo();
                System.out.println("TAMBAH DATA ORDERS DETAIL");
                System.out.println("=========================");

                rs = db.fetchTable(TableName.PRODUK);
                rowCount = 0;

                if(rs.last())
                {
                    rowCount = rs.getRow();
                    rs.beforeFirst();
                }

                if(rowCount <= 0)
                {
                    System.out.println("Daya yang dicari tidak ada!");
                    return;
                }

                System.out.printf("%s", new String(new char[100]).replace("\0", "-"));
                System.out.println();
                System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "NAMA", "JENIS", "STOK", "HARGA");
                System.out.printf("%s", new String(new char[100]).replace("\0", "+"));
                System.out.println();

                while(rs.next())
                {
                    System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20d|| %-20d||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("JENIS"), rs.getInt("STOK"), rs.getInt("HARGA"));

                    if(rs.next())
                    {
                        System.out.printf("%s", new String(new char[100]).replace("\0", "-"));
                        System.out.println();
                        rs.previous();
                    }
                }

                System.out.printf("%s", new String(new char[100]).replace("\0", "="));
                System.out.println();
                System.out.println();
                System.out.print("Masukkan ID makanan yang di order (0 untuk keluar) : ");
                opt = Integer.parseInt(scn.readLine());
                
                if(opt == 0)
                    break;
                
                System.out.print("Masukkan kuantitas : ");
                int Qty = Integer.parseInt(scn.readLine());
                int total = Qty * db.getPrice(opt);
                db.addRowOrdersDetail(Qty, total, id_orders, opt);
                System.out.println("Data berhasil ditambahkan!");
                brk.nextLine();
            }while(opt != 0);
            
            // Transaksi telah ditambahkan!
            clearScreen();
            showLogo();
            System.out.println();
            System.out.println("Transaksi baru telah ditambahkan!!!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void tampilTransaksi() throws InterruptedException
    {
        try
        {
            System.out.println("TAMPIL DATA TRANSAKSI");
            System.out.println("=====================");
            System.out.print("Masukkan ID Pelanggan : ");
            int id_pelanggan = Integer.parseInt(scn.readLine());
            String identity = "ID=" + String.valueOf(id_pelanggan);
            rs = db.srcRowPelanggan(identity);
            rs.next();
            
            // Data Pelanggan
            String nama_pelanggan = rs.getString("NAMA");
            String no_pelangan = rs.getString("NO_TELP");
            String alamat_pelanggan = rs.getString("ALAMAT");
            
            identity = "ID_PELANGGAN=" + String.valueOf(id_pelanggan);
            rs = db.srcRowOrders(identity);
            rs.next();
            
            // Data Orders
            int id_orders = rs.getInt("ID");
            java.sql.Date tgl_pembelian = rs.getDate("TGL_PEMBELIAN");
            int id_pelayan = rs.getInt("ID_PELAYAN");
            
            identity = "ID=" + String.valueOf(id_pelayan);
            rs = db.srcRowPelayan(identity);
            rs.next();
            
            // Data Pelayan
            String nama_pelayan = rs.getString("NAMA");
            String no_pelayan = rs.getString("NO_TELP");
            String alamat_pelayan = rs.getString("ALAMAT");
            
            clearScreen();
            
            showLogo();
            System.out.println("TAMPIL DATA TRANSAKSI");
            System.out.println("=====================");
            System.out.println("Nomor Order    : " + id_orders);
            System.out.println("---");
            System.out.println("Nama Pelanggan : " + nama_pelanggan);
            System.out.println("No. Telp       : " + no_pelangan);
            System.out.println("Alamat         : " + alamat_pelanggan);
            System.out.println("---");
            System.out.println("Dilayani oleh  : " + nama_pelayan);
            System.out.println("No. Telp       : " + no_pelayan);
            System.out.println("Alamat         : " + alamat_pelayan);
            System.out.println("---");
            System.out.println();
            
            List<Integer> kuantitas = new ArrayList<Integer>();
            List<Integer> total = new ArrayList<Integer>();
            List<String> nama_produk = new ArrayList<String>();
            List<String> jenis = new ArrayList<String>();
            List<Integer> harga = new ArrayList<Integer>();
            
            rs = db.fetchProdukFromOrders(id_orders);
            
            while(rs.next())
            {
                nama_produk.add(rs.getString("NAMA"));
                kuantitas.add(rs.getInt("KUANTITAS_PRODUK"));
                total.add(rs.getInt("TOTAL"));
                jenis.add(rs.getString("JENIS"));
                harga.add(rs.getInt("HARGA"));
            }

            int total_harga = 0;
            
            System.out.printf("%s", new String(new char[91]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-12.12s|| %-12.12s|| %-12.12s|| %-12.12s||\n", "No", "Nama", "Jenis", "Kuantitas", "Harga", "Total");
            System.out.printf("%s", new String(new char[91]).replace("\0", "+"));
            System.out.println();
            
            for(int j = 0; j < nama_produk.size(); j++)
            {
                System.out.printf("|| %-3s|| %-20.20s|| %-12.12s|| %-12d|| %-12d|| %-12d||\n", j, nama_produk.get(j), jenis.get(j), kuantitas.get(j), harga.get(j), total.get(j));
                total_harga += total.get(j);
                
                if(nama_produk.size() != j + 1)
                {
                    System.out.printf("%s", new String(new char[91]).replace("\0", "-"));
                    System.out.println();
                }
            }
            
            System.out.printf("%s", new String(new char[91]).replace("\0", "="));
            System.out.println();
            System.out.println("Total Harga = " + total_harga);
            System.out.println();
            System.out.println("Tekan apa saja untuk kembali...");     
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusTransaksi() throws InterruptedException
    {
        try
        {
            System.out.println("HAPUS TRANSAKSI");
            System.out.println("===============");
            System.out.print("Masukkan ID Pelanggan : ");
            int id_pelanggan = Integer.parseInt(scn.readLine());
            db.remLinked(id_pelanggan);
            
            clearScreen();
            showLogo();
            System.out.println("HAPUS TRANSAKSI");
            System.out.println("===============");
            System.out.println("Data transaksi berhasil di hapus!");
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    // MENU PELANGAN FUNCTION
    
    public void pelangganMenu()
    {
        System.out.println("MENU EDIT DATA PELANGGAN");
        System.out.println("======================");
        System.out.println("1) Tambah Data Pelanggan");
        System.out.println("2) Cari/Ubah Data Pelanggan");
        System.out.println("3) Hapus Data Pelangan");
        System.out.println("0) Kembali");
        System.out.println("======================");
        System.out.print("Pilih > ");
    }
    
    public void tambahPelanggan()
    {
        try
        {
            System.out.println("TAMBAH DATA PELANGGAN");
            System.out.println("=====================");
            System.out.print("Masukkan Nama     : ");
            String nama = scn.readLine();

            System.out.print("Masukkan No. Telp : ");
            String telp = scn.readLine();

            System.out.print("Masukkan Alamat   : ");
            String alamat = scn.readLine();
        
        // Add data to the PELANGGAN table
            db.addRowPelanggan(nama, telp, alamat);
            System.out.println("Data berhasil ditambahkan!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void cariPelanggan() throws InterruptedException
    {
        try
        {
            System.out.println("CARI DATA PELANGGAN");
            System.out.println("=====================");
            System.out.println("1) Berdasarkan ID");
            System.out.println("2) Berdasarkan NAMA");
            System.out.println("3) Berdasarkan NO. TELPON");
            System.out.println("4) Berdsarkan ALAMAT");
            System.out.println("=====================");
            System.out.print("Pilih > ");

            int opt = Integer.parseInt(scn.readLine());
            String identity = "";

            System.out.println();
            System.out.print("Masukkan valuenya : ");
            String value = scn.readLine();

            if(opt == 1)
                identity += "ID=" + Integer.parseInt(value);
            else if(opt == 2)
                identity += "NAMA='" + value + "'";
            else if(opt == 3)
                identity += "NO_TELP='" + value + "'";
            else if(opt == 4)
                identity += "ALAMAT='" + value + "'";

            clearScreen();
            showLogo();

            System.out.println("HASIL PENCARIAN PELANGGAN");
            System.out.println("=========================");
        

            rs = db.srcRowPelanggan(identity);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Data yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            // Give option to the user to modify the data if only single result
            if(rowCount == 1)
            {
                while(true)
                {
                    System.out.print("Ubah data yang bersangkutan? (Y/N) : ");
                    String ch = scn.readLine();

                    if(ch.equals("Y") || ch.equals("y"))
                    {
                        System.out.println("(Ketik 000 bila tidak ingin perubahan)");
                        System.out.print("Nama     : ");
                        String nama = scn.readLine();
                        System.out.print("No. telp : ");
                        String no_telp = scn.readLine();
                        System.out.print("Alamat   : ");
                        String alamat = scn.readLine();
                        
                        rs.first();
                        int id_pelanggan = rs.getInt("ID");
                        
                        db.updtRowPelanggan(id_pelanggan, nama, no_telp, alamat);
                        System.out.println("Berhasil diubah!");
                        System.out.println();
                        
                        break;
                    }
                    else if(ch.equals("N") || ch.equals("n"))
                    {
                        System.out.println();
                        break;
                    }
                    else
                    {
                        System.out.println("Masukkan huruf Y/y untuk \"Iya\" atau N/n untuk \"Tidak\"!");
                    }

                }
            }
            else
            {
                System.out.println("Apabila ingin mengubah data pastikan");
                System.out.println("Hasil data pencarian hanya satu data");
                System.out.println("---");
            }

            System.out.println("Tekan apa saja untuk kembali...");
            System.in.read();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusPelanggan()
    {
        try
        {
            System.out.println("HAPUS DATA PELANGGAN");
            System.out.println("=====================");
            System.out.print("Masukkan ID Pelanggan : ");
            int id_pelanggan = Integer.parseInt(scn.readLine());
        

            // Check first the data if it's exist
            String sql = "ID=" + String.valueOf(id_pelanggan);
            rs = db.srcRowPelanggan(sql);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount == 0)
            {
                System.out.println("Data yang dimaksud tidak ada!");
                return;
            }
            // Else, then delete it
            else
            {
                db.remRowPelanggan(id_pelanggan);
                System.out.println("Berhasil dihapus!");
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public int tampilPelanggan(int counter)
    {
        int rowCount = 0;
        int currentPage = counter;
        int maxPage = 1;
        
        System.out.println("TABLE DATA PELANGGAN");
        System.out.println("====================");
        
        // Fetch table PELANGGAN
        try
        { 
            // Count the rows from table
            rowCount = db.countRows(TableName.PELANGGAN);
            
            // Count the max page for every 5 rows
            maxPage = rowCount / 5;
            
            if(rowCount % 5 > 0)
                maxPage++;
            
            // Get the result of the query depends on the current page
            // If the currentPage is 1 then it mean will show the query from the 1st row to the next 5th row
            rs = db.fetchTable(TableName.PELANGGAN, (currentPage - 1) * 5);
            

            
            // Print the table layout
            System.out.printf("%s    Panduan :", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||    n) Untuk Next Page\n", "ID", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s    p) Untuk Prev Page", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s    PAGE %d/%d", new String(new char[77]).replace("\0", "="), currentPage, maxPage);
            System.out.println();
            System.out.println();  
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
        return maxPage;
    }
    
    // MENU ORDERS FUNCTION
    
    public void orderMenu()
    {
        System.out.println("MENU EDIT DATA ORDERS");
        System.out.println("=====================");
        System.out.println("1) Tambah Data Orders");
        System.out.println("2) Cari/Ubah Data Orders");
        System.out.println("3) Hapus Data Orders");
        System.out.println("0) Kembali");
        System.out.println("=====================");
        System.out.print("Pilih > ");
    }
    
    public void tambahOrders()
    {
        try
        {
            System.out.println("TAMBAH DATA ORDERS");
            System.out.println("==================");
            System.out.print("Masukkan ID Pelanggan : ");
            int id_pelanggan = Integer.parseInt(scn.readLine());

            System.out.print("Masukkan ID Pelayan   : ");
            int id_pelayan = Integer.parseInt(scn.readLine());
            
            // Add data to the ORDERS table
            db.addRowOrders(id_pelanggan, id_pelayan);
            System.out.println("Data berhasil ditambahkan!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void cariOrders() throws InterruptedException, ParseException
    {
        try
        {
            System.out.println("CARI DATA ORDERS");
            System.out.println("=====================");
            System.out.println("1) Berdasarkan ID Orders");
            System.out.println("1) Berdasarkan ID Pelanggan");
            System.out.println("2) Berdasarkan ID Pelayan");
            System.out.println("3) Berdasarkan Tanggal(yyyy-MM-dd)");
            System.out.println("=====================");
            System.out.print("Pilih > ");

            int opt = Integer.parseInt(scn.readLine());
            String identity = "";

            System.out.println();
            System.out.print("Masukkan valuenya : ");
            String value = scn.readLine();

            if(opt == 1)
                identity += "ID=" + Integer.parseInt(value);
            else if(opt == 2)
                identity += "ID_PELANGGAN=" + Integer.parseInt(value);
            else if(opt == 3)
                identity += "ID_PELAYAN=" + Integer.parseInt(value);
            else if(opt == 4)
                identity += "TGL_PEMBELIAN=" + value;

            clearScreen();
            showLogo();

            System.out.println("HASIL PENCARIAN ORDERS");
            System.out.println("=========================");
        

            rs = db.srcRowOrders(identity);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Daya yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "TGL. Pembelian", "ID Pelanggan", "ID Pelayan");
            System.out.printf("%s", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("TGL_PEMBELIAN"), rs.getString("ID_PELANGGAN"), rs.getString("ID_PELAYAN"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            // Give option to the user to modify the data if only single result
            if(rowCount == 1)
            {
                while(true)
                {
                    System.out.print("Ubah data yang bersangkutan? (Y/N) : ");
                    String ch = scn.readLine();

                    if(ch.equals("Y") || ch.equals("y"))
                    {
                        System.out.println("(Ketik 000 bila tidak ingin perubahan)");
                        System.out.print("Tgl Pembelian(yyyy-MM-dd) : ");
                        String dateString = scn.readLine();
                        
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date myDate = formatter.parse(dateString);
                        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
                        
                        System.out.print("ID Pelanggan              : ");
                        int id_pelanggan = Integer.parseInt(scn.readLine());
                        System.out.print("ID Pelayan                : ");
                        int id_pelayan = Integer.parseInt(scn.readLine());
                        
                        rs.first();
                        int id_orders = rs.getInt("ID");
                        
                        db.updtRowOrders(id_orders, sqlDate, id_pelanggan, id_pelayan);
                        System.out.println("Berhasil diubah!");
                        System.out.println();
                        
                        break;
                    }
                    else if(ch.equals("N") || ch.equals("n"))
                    {
                        System.out.println();
                        break;
                    }
                    else
                    {
                        System.out.println("Masukkan huruf Y/y untuk \"Iya\" atau N/n untuk \"Tidak\"!");
                    }

                }
            }
            else
            {
                System.out.println("Apabila ingin mengubah data pastikan");
                System.out.println("Hasil data pencarian hanya satu data");
                System.out.println("---");
            }

            System.out.println("Tekan apa saja untuk kembali...");
            System.in.read();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusOrders()
    {
        try
        {
            System.out.println("HAPUS DATA ORDERS");
            System.out.println("=====================");
            System.out.print("Masukkan ID Orders : ");
            int id_orders = Integer.parseInt(scn.readLine());
        

            // Check first the data if it's exist
            String sql = "ID=" + String.valueOf(id_orders);
            rs = db.srcRowOrders(sql);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount == 0)
            {
                System.out.println("Data yang dimaksud tidak ada!");
                return;
            }
            // Else, then delete it
            else
            {
                db.remRowOrders(id_orders);
                System.out.println("Berhasil dihapus!");
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public int tampilOrders(int counter)
    {
        int rowCount = 0;
        int currentPage = counter;
        int maxPage = 1;
        
        System.out.println("TABLE DATA ORDERS");
        System.out.println("====================");
        
        // Fetch table PELANGGAN
        try
        { 
            // Count the rows from table
            rowCount = db.countRows(TableName.ORDERS);
            
            // Count the max page for every 5 rows
            maxPage = rowCount / 5;
            
            if(rowCount % 5 > 0)
                maxPage++;
            
            // Get the result of the query depends on the current page
            // If the currentPage is 1 then it mean will show the query from the 1st row to the next 5th row
            rs = db.fetchTable(TableName.ORDERS, (currentPage - 1) * 5);
            

            
            // Print the table layout
            System.out.printf("%s    Panduan :", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||    n) Untuk Next Page\n", "ID", "TGL. Pembelian", "ID Pelanggan", "ID Pelayan");
            System.out.printf("%s    p) Untuk Prev Page", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20d|| %-20d||\n", rs.getInt("ID"), rs.getString("TGL_PEMBELIAN"), rs.getInt("ID_PELANGGAN"), rs.getInt("ID_PELAYAN"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s    PAGE %d/%d", new String(new char[77]).replace("\0", "="), currentPage, maxPage);
            System.out.println();
            System.out.println();  
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
        return maxPage;
    }
    
    // MENU DETAIL_ORDERS FUNCTION
    
    public void detailOrdersMenu()
    {
        System.out.println("MENU EDIT DATA DETAIL ORDERS");
        System.out.println("============================");
        System.out.println("1) Tambah Data Detail Orders");
        System.out.println("2) Cari/Ubah Data Detail Orders");
        System.out.println("3) Hapus Data Detail Orders");
        System.out.println("0) Kembali");
        System.out.println("============================");
        System.out.print("Pilih > ");
    }
    
    public void tambahDetailOrders()
    {
        try
        {
            System.out.println("TAMBAH DATA DETAIL ORDERS");
            System.out.println("=====================");
            System.out.print("Masukkan ID Orders : ");
            int id_orders = Integer.parseInt(scn.readLine());

            System.out.print("Masukkan ID Produk : ");
            int id_produk = Integer.parseInt(scn.readLine());

            System.out.print("Masukkan Kuantitas : ");
            int kuantitas = Integer.parseInt(scn.readLine());
            
            int total = db.getPrice(id_produk) * kuantitas;
            
            // Add data to the ORDERS_DETAIL table
            db.addRowOrdersDetail(kuantitas, total, id_orders, id_produk);
            System.out.println("Data berhasil ditambahkan!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void cariDetailOrders() throws InterruptedException
    {
        try
        {
            System.out.println("CARI DATA DETAIL ORDERS");
            System.out.println("=======================");
            System.out.println("1) Berdasarkan ID Detail Orders");
            System.out.println("1) Berdasarkan ID Orders");
            System.out.println("2) Berdasarkan ID Produk");
            System.out.println("=======================");
            System.out.print("Pilih > ");

            int opt = Integer.parseInt(scn.readLine());
            String identity = "";

            System.out.println();
            System.out.print("Masukkan valuenya : ");
            String value = scn.readLine();

            if(opt == 1)
                identity += "ID=" + Integer.parseInt(value);
            else if(opt == 2)
                identity += "ID_ORDERS=" + Integer.parseInt(value);
            else if(opt == 3)
                identity += "ID_PRODUK=" + Integer.parseInt(value);

            clearScreen();
            showLogo();

            System.out.println("HASIL PENCARIAN DETAIL ORDERS");
            System.out.println("=============================");
        

            rs = db.srcRowOrdersDetail(identity);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Data yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[67]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-12.12s|| %-12.12s|| %-12.12s|| %-12.12s||\n", "ID", "ID Orders", "ID Produk", "Kuantitas", "Total");
            System.out.printf("%s", new String(new char[67]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3s|| %-12d|| %-12d|| %-12d|| %-12d||\n", rs.getInt("ID"), rs.getInt("ID_ORDERS"), rs.getInt("ID_PRODUK"), rs.getInt("KUANTITAS_PRODUK"), rs.getInt("TOTAL"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[67]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[67]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            // Give option to the user to modify the data if only single result
            if(rowCount == 1)
            {
                while(true)
                {
                    System.out.print("Ubah data yang bersangkutan? (Y/N) : ");
                    String ch = scn.readLine();

                    if(ch.equals("Y") || ch.equals("y"))
                    {
                        System.out.println("(Ketik 000 bila tidak ingin perubahan)");
                        System.out.print("ID Orders : ");
                        int id_orders = Integer.parseInt(scn.readLine());
                        
                        System.out.print("ID Produk : ");
                        int id_produk = Integer.parseInt(scn.readLine());
                        
                        System.out.print("Kuantitas : ");
                        int kuantitas = Integer.parseInt(scn.readLine());
                        
                        rs.first();
                        int id_detail_orders = rs.getInt("ID");
                        
                        db.updtRowOrdersDetail(id_detail_orders, id_orders, id_produk, kuantitas);
                        System.out.println("Berhasil diubah!");
                        System.out.println();
                        
                        break;
                    }
                    else if(ch.equals("N") || ch.equals("n"))
                    {
                        System.out.println();
                        break;
                    }
                    else
                    {
                        System.out.println("Masukkan huruf Y/y untuk \"Iya\" atau N/n untuk \"Tidak\"!");
                    }

                }
            }
            else
            {
                System.out.println("Apabila ingin mengubah data pastikan");
                System.out.println("Hasil data pencarian hanya satu data");
                System.out.println("---");
            }

            System.out.println("Tekan apa saja untuk kembali...");
            System.in.read();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusDetailOrders()
    {
        try
        {
            System.out.println("HAPUS DATA DETAIL ORDERS");
            System.out.println("========================");
            System.out.print("Masukkan ID Detail Orders : ");
            int id_orders_detail = Integer.parseInt(scn.readLine());

            // Check first the data if it's exist
            String sql = "ID=" + String.valueOf(id_orders_detail);
            rs = db.srcRowOrdersDetail(sql);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount == 0)
            {
                System.out.println("Data yang dimaksud tidak ada!");
                return;
            }
            // Else, then delete it
            else
            {
                db.remRowOrdersDetail(id_orders_detail);
                System.out.println("Berhasil dihapus!");
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public int tampilDetailOrders(int counter)
    {
        int rowCount = 0;
        int currentPage = counter;
        int maxPage = 1;
        
        System.out.println("TABLE DATA ORDERS DETAIL");
        System.out.println("========================");
        
        // Fetch table PELANGGAN
        try
        { 
            // Count the rows from table
            rowCount = db.countRows(TableName.DETAIL_ORDERS);
            
            // Count the max page for every 5 rows
            maxPage = rowCount / 5;
            
            if(rowCount % 5 > 0)
                maxPage++;
            
            // Get the result of the query depends on the current page
            // If the currentPage is 1 then it mean will show the query from the 1st row to the next 5th row
            rs = db.fetchTable(TableName.DETAIL_ORDERS, (currentPage - 1) * 5);
            
            // Print the table layout
            System.out.printf("%s    Panduan :", new String(new char[60]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-10.10s|| %-10.10s|| %-10.10s|| %-10.10s||    n) Untuk Next Page\n", "ID", "ID ORDERS", "ID PRODUK", "KUANTITAS", "TOTAL");
            System.out.printf("%s    p) Untuk Prev Page", new String(new char[60]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-10d|| %-10d|| %-10d|| %-10d||\n", rs.getInt("ID"), rs.getInt("ID_ORDERS"), rs.getInt("ID_PRODUK"), rs.getInt("KUANTITAS_PRODUK"), rs.getInt("TOTAL"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[60]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s    PAGE %d/%d", new String(new char[60]).replace("\0", "="), currentPage, maxPage);
            System.out.println();
            System.out.println();  
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
        return maxPage;
    }
    
    // MENU PELAYAN FUNCTION
    
    public void pelayanMenu()
    {
        System.out.println("MENU EDIT DATA PELAYAN");
        System.out.println("======================");
        System.out.println("1) Tambah Data Pelayan");
        System.out.println("2) Cari/Ubah Data Pelayan");
        System.out.println("3) Hapus Data Pelayan");
        System.out.println("0) Kembali");
        System.out.println("======================");
        System.out.print("Pilih > ");
    }
    
    public void tambahPelayan()
    {
        try
        {
            System.out.println("TAMBAH DATA PELAYAN");
            System.out.println("=====================");
            System.out.print("Masukkan Nama     : ");
            String nama = scn.readLine();

            System.out.print("Masukkan No. Telp : ");
            String telp = scn.readLine();

            System.out.print("Masukkan Alamat   : ");
            String alamat = scn.readLine();
        
        // Add data to the PELANGGAN table
            db.addRowPelayan(nama, telp, alamat);
            System.out.println("Data berhasil ditambahkan!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void cariPelayan() throws InterruptedException
    {
        try
        {
            System.out.println("CARI DATA PELAYAN");
            System.out.println("=====================");
            System.out.println("1) Berdasarkan ID");
            System.out.println("2) Berdasarkan NAMA");
            System.out.println("3) Berdasarkan NO. TELPON");
            System.out.println("4) Berdsarkan ALAMAT");
            System.out.println("=====================");
            System.out.print("Pilih > ");

            int opt = Integer.parseInt(scn.readLine());
            String identity = "";

            System.out.println();
            System.out.print("Masukkan valuenya : ");
            String value = scn.readLine();

            if(opt == 1)
                identity += "ID=" + Integer.parseInt(value);
            else if(opt == 2)
                identity += "NAMA='" + value + "'";
            else if(opt == 3)
                identity += "NO_TELP='" + value + "'";
            else if(opt == 4)
                identity += "ALAMAT='" + value + "'";

            clearScreen();
            showLogo();

            System.out.println("HASIL PENCARIAN PELAYAN");
            System.out.println("=========================");
        

            rs = db.srcRowPelayan(identity);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Daya yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[77]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            // Give option to the user to modify the data if only single result
            if(rowCount == 1)
            {
                while(true)
                {
                    System.out.print("Ubah data yang bersangkutan? (Y/N) : ");
                    String ch = scn.readLine();

                    if(ch.equals("Y") || ch.equals("y"))
                    {
                        System.out.println("(Ketik 000 bila tidak ingin perubahan)");
                        System.out.print("Nama     : ");
                        String nama = scn.readLine();
                        System.out.print("No. telp : ");
                        String no_telp = scn.readLine();
                        System.out.print("Alamat   : ");
                        String alamat = scn.readLine();
                        
                        rs.first();
                        int id_pelayan = rs.getInt("ID");
                        
                        db.updtRowPelayan(id_pelayan, nama, no_telp, alamat);
                        System.out.println("Berhasil diubah!");
                        System.out.println();
                        
                        break;
                    }
                    else if(ch.equals("N") || ch.equals("n"))
                    {
                        System.out.println();
                        break;
                    }
                    else
                    {
                        System.out.println("Masukkan huruf Y/y untuk \"Iya\" atau N/n untuk \"Tidak\"!");
                    }

                }
            }
            else
            {
                System.out.println("Apabila ingin mengubah data pastikan");
                System.out.println("Hasil data pencarian hanya satu data");
                System.out.println("---");
            }

            System.out.println("Tekan apa saja untuk kembali...");
            System.in.read();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusPelayan()
    {
        try
        {
            System.out.println("HAPUS DATA PELAYAN");
            System.out.println("=====================");
            System.out.print("Masukkan ID Pelayan : ");
            int id_pelayan = Integer.parseInt(scn.readLine());
        

            // Check first the data if it's exist
            String sql = "ID=" + String.valueOf(id_pelayan);
            rs = db.srcRowPelayan(sql);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount == 0)
            {
                System.out.println("Data yang dimaksud tidak ada!");
                return;
            }
            // Else, then delete it
            else
            {
                db.remRowPelayan(id_pelayan);
                System.out.println("Berhasil dihapus!");
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public int tampilPelayan(int counter)
    {
        int rowCount = 0;
        int currentPage = counter;
        int maxPage = 1;
        
        System.out.println("TABLE DATA PELAYAN");
        System.out.println("====================");
        
        // Fetch table PELANGGAN
        try
        { 
            // Count the rows from table
            rowCount = db.countRows(TableName.PELAYAN);
            
            // Count the max page for every 5 rows
            maxPage = rowCount / 5;
            
            if(rowCount % 5 > 0)
                maxPage++;
            
            // Get the result of the query depends on the current page
            // If the currentPage is 1 then it mean will show the query from the 1st row to the next 5th row
            rs = db.fetchTable(TableName.PELAYAN, (currentPage - 1) * 5);
            

            
            // Print the table layout
            System.out.printf("%s    Panduan :", new String(new char[77]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s||    n) Untuk Next Page\n", "ID", "NAMA", "NO. TELEPON", "ALAMAT");
            System.out.printf("%s    p) Untuk Prev Page", new String(new char[77]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20.20s||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("NO_TELP"), rs.getString("ALAMAT"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[77]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s    PAGE %d/%d", new String(new char[77]).replace("\0", "="), currentPage, maxPage);
            System.out.println();
            System.out.println();  
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
        return maxPage;
    }
    
    // MENU PRODUCT FUNCTION
    
    public void produkMenu()
    {
        System.out.println("MENU EDIT DATA PRODUK");
        System.out.println("=====================");
        System.out.println("1) Tambah Data Produk");
        System.out.println("2) Cari/Ubah Data Produk");
        System.out.println("3) Hapus Data Produk");
        System.out.println("0) Kembali");
        System.out.println("=====================");
        System.out.print("Pilih > ");
    }
    
    public void tambahProduk()
    {
        try
        {
            System.out.println("TAMBAH DATA PRODUK");
            System.out.println("=====================");
            System.out.print("Masukkan Nama  : ");
            String nama = scn.readLine();

            System.out.print("Masukkan Jenis : ");
            String jenis = scn.readLine();

            System.out.print("Masukkan Stok  : ");
            int stok = Integer.parseInt(scn.readLine());
            
            System.out.print("Masukkan Harga : ");
            int harga = Integer.parseInt(scn.readLine());
            
            // Add data to the PELANGGAN table
            db.addRowProduk(nama, jenis, stok, harga);
            System.out.println("Data berhasil ditambahkan!");
        }
        catch(SQLException e)
        { 
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void cariProduk() throws InterruptedException
    {
        try
        {
            System.out.println("CARI DATA PRODUK");
            System.out.println("=====================");
            System.out.println("1) Berdasarkan ID");
            System.out.println("2) Berdasarkan NAMA");
            System.out.println("3) Berdasarkan JENIS");
            System.out.println("4) Berdsarkan STOK");
            System.out.println("5) Berdasarkan HARGA");
            System.out.println("=====================");
            System.out.print("Pilih > ");

            int opt = Integer.parseInt(scn.readLine());
            String identity = "";

            System.out.println();
            System.out.print("Masukkan valuenya : ");
            String value = scn.readLine();

            if(opt == 1)
                identity += "ID=" + Integer.parseInt(value);
            else if(opt == 2)
                identity += "NAMA='" + value + "'";
            else if(opt == 3)
                identity += "JENIS='" + value + "'";
            else if(opt == 4)
                identity += "STOK=" + Integer.parseInt(value);
            else if(opt == 5)
                identity += "HARGA=" + Integer.parseInt(value);

            clearScreen();
            showLogo();

            System.out.println("HASIL PENCARIAN PRODUK");
            System.out.println("=========================");
        

            rs = db.srcRowProduk(identity);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount <= 0)
            {
                System.out.println("Daya yang dicari tidak ada!");
                return;
            }
            
            System.out.printf("%s", new String(new char[100]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s|| %-20.20s||\n", "ID", "NAMA", "JENIS", "STOK", "HARGA");
            System.out.printf("%s", new String(new char[100]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20d|| %-20d||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("JENIS"), rs.getInt("STOK"), rs.getInt("HARGA"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[100]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s", new String(new char[100]).replace("\0", "="));
            System.out.println();
            System.out.println();
            
            // Give option to the user to modify the data if only single result
            if(rowCount == 1)
            {
                while(true)
                {
                    System.out.print("Ubah data yang bersangkutan? (Y/N) : ");
                    String ch = scn.readLine();

                    if(ch.equals("Y") || ch.equals("y"))
                    {
                        System.out.println("(Ketik 000 bila tidak ingin perubahan)");
                        System.out.print("Nama  : ");
                        String nama = scn.readLine();
                        System.out.print("Jenis : ");
                        String jenis = scn.readLine();
                        System.out.print("Stok  : ");
                        int stok = Integer.parseInt(scn.readLine());
                        System.out.print("Harga :");
                        int harga = Integer.parseInt(scn.readLine());
                        
                        rs.first();
                        int id_produk = rs.getInt("ID");
                        
                        db.updtRowProduk(id_produk, nama, jenis, stok, harga);
                        System.out.println("Berhasil diubah!");
                        System.out.println();
                        
                        break;
                    }
                    else if(ch.equals("N") || ch.equals("n"))
                    {
                        System.out.println();
                        break;
                    }
                    else
                    {
                        System.out.println("Masukkan huruf Y/y untuk \"Iya\" atau N/n untuk \"Tidak\"!");
                    }

                }
            }
            else
            {
                System.out.println("Apabila ingin mengubah data pastikan");
                System.out.println("Hasil data pencarian hanya satu data");
                System.out.println("---");
            }

            System.out.println("Tekan apa saja untuk kembali...");
            System.in.read();
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public void hapusProduk()
    {
        try
        {
            System.out.println("HAPUS DATA PRDOUK");
            System.out.println("=====================");
            System.out.print("Masukkan ID PRODUK : ");
            int id_pelayan = Integer.parseInt(scn.readLine());
        

            // Check first the data if it's exist
            String sql = "ID=" + String.valueOf(id_pelayan);
            rs = db.srcRowProduk(sql);
            int rowCount = 0;
            
            if(rs.last())
            {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            
            if(rowCount == 0)
            {
                System.out.println("Data yang dimaksud tidak ada!");
                return;
            }
            // Else, then delete it
            else
            {
                db.remRowProduk(id_pelayan);
                System.out.println("Berhasil dihapus!");
            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(IOException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
    }
    
    public int tampilProduk(int counter)
    {
        int rowCount = 0;
        int currentPage = counter;
        int maxPage = 1;
        
        System.out.println("TABLE DATA PRODUK");
        System.out.println("====================");
        
        // Fetch table PELANGGAN
        try
        { 
            // Count the rows from table
            rowCount = db.countRows(TableName.PRODUK);
            
            // Count the max page for every 5 rows
            maxPage = rowCount / 5;
            
            if(rowCount % 5 > 0)
                maxPage++;
            
            // Get the result of the query depends on the current page
            // If the currentPage is 1 then it mean will show the query from the 1st row to the next 5th row
            rs = db.fetchTable(TableName.PRODUK, (currentPage - 1) * 5);
            
            // Print the table layout
            System.out.printf("%s  Panduan :", new String(new char[100]).replace("\0", "-"));
            System.out.println();
            System.out.printf("|| %-3s|| %-20.20s|| %-20.20s|| %-20.20s|| %-20.20s||  n) Untuk Next Page\n", "ID", "NAMA", "JENIS", "STOK", "HARGA");
            System.out.printf("%s  p) Untuk Prev Page", new String(new char[100]).replace("\0", "+"));
            System.out.println();
            
            while(rs.next())
            {
                System.out.printf("|| %-3d|| %-20.20s|| %-20.20s|| %-20d|| %-20d||\n", rs.getInt("ID"), rs.getString("NAMA"), rs.getString("JENIS"), rs.getInt("STOK"), rs.getInt("HARGA"));
                
                if(rs.next())
                {
                    System.out.printf("%s", new String(new char[100]).replace("\0", "-"));
                    System.out.println();
                    rs.previous();
                }
            }
            
            System.out.printf("%s  PAGE %d/%d", new String(new char[100]).replace("\0", "="), currentPage, maxPage);
            System.out.println();
            System.out.println();  
        }
        catch(SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
        }
        
        return maxPage;
    }
    
    public void clearScreen() throws IOException, InterruptedException
    {
        if(System.console() == null)
        {
            System.out.println("Jangan RUN Aplikasi dari console NetBeans!");
            System.out.println("Build terlebih dahulu lalu RUN lewat CMD!");
            System.exit(0);
        }
        else
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}