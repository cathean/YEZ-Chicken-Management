package yez.chicken.management;
import java.sql.*;
import java.io.File;

public class DBManager
{
    enum fetchTable
    {
        PELANGGAN,
        PELAYAN,
        PRODUK,
        ORDERS,
        DETAIL_ORDER
    }
    
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    
    private boolean isDBExist = false;
    private String dbName = "YEZ.db";
    
    public void ConnectDB()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:YEZ.db");

            initTables();
        }catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database susccesfully!");
    }
    
    // Database table initialization
    private void initTables() throws SQLException
    {
        stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PELANGGAN " +
                     "(ID_PELANGGAN  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                     " NAMA          TEXT                              NOT NULL," +
                     " NO_TELP       CHAR(12)                                  ," +
                     " ALAMAT        CHAR(50)                                  )";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS PELAYAN " +
              "(ID_PELAYAN    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
              " NAMA          TEXT                              NOT NULL," +
              " NO_TELP       CHAR(12)                                  ," +
              " ALAMAT        CHAR(50)                                  )";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS PRODUK " +
              "(ID_PRODUK     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
              " NAMA_PRODUK   TEXT                              NOT NULL," +
              " JENIS         CHAR(12)                          NOT NULL," +
              " STOK          INTEGER                           NOT NULL," +
              " HARGA         INTEGER                           NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS ORDERS " +
              "(ID_ORDERS     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
              " TGL_PEMBELIAN DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL," +
              " ID_PELANGGAN  INTEGER                           NOT NULL," +
              " ID_PELAYAN  INTEGER                           NOT NULL," +
              " FOREIGN KEY(ID_PELANGGAN)   REFERENCES PELANGGAN(ID_PELANGGAN)," +
              " FOREIGN KEY(ID_PELAYAN)     REFERENCES PELAYAN(ID_PELAYAN))";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS DETAIL_ORDERS " +
              "(ID_DETAIL_ORDERS     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
              " KUANTITAS_PRODUK     INTEGER                           NOT NULL," +
              " TOTAL                INTEGER                           NOT NULL," +
              " ID_ORDERS            INTEGER                           NOT NULL," +
              " ID_PRODUK            INTEGER                           NOT NULL," +
              " FOREIGN KEY(ID_ORDERS)   REFERENCES ORDERS(ID_ORDERS)," +
              " FOREIGN KEY(ID_PRODUK)   REFERENCES PRODUK(ID_PRODUK))";
        stmt.executeUpdate(sql);
        
        // ADD PRODUK COLUMNS ----
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (1, 'Nasi Putih', 'Makanan', 25, 2000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (2, 'Ayam Bakar Paha', 'Makanan', 12, 8000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (3, 'Ayam Goreng Paha', 'Makanan', 4, 8000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (4, 'Ayam Bakar Dada', 'Makanan', 16, 10000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (5, 'Ayam Goren Dada', 'Makanan', 13, 10000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (6, 'Teh Manis', 'Minuman', 7, 3000)";
        stmt.executeUpdate(sql);
        
        sql = "INSERT OR REPLACE INTO PRODUK" +
              "(ID_PRODUK, NAMA_PRODUK, JENIS, STOK, HARGA)" +
              "VALUES (7, 'Nutrisasri', 'Minuman', 12, 3000)";
        stmt.executeUpdate(sql);
        
        // ADD PELANGGAN COLUMNS ---
        
        sql = "INSERT OR REPLACE INTO PELAYAN " +
              "(NAMA, NO_TELP, ALAMAT) " +
              "VALUES ('Thoriq', '08123456789', 'Tubagus Ismail Dalam' )";
        stmt.executeUpdate(sql);
    }
    
    public void addPelanggan(String nama, String telp, String alamat) throws SQLException
    {
        stmt = conn.createStatement();
        
        String sql = "INSERT INTO PELANGGAN " +
                     "(NAMA, NO_TELP, ALAMAT) " +
                     "VALUES ('" + nama + "', '" + telp + "', '" + alamat + "')";
        stmt.executeUpdate(sql);
    }
    
    private void addPelayan(String nama, String telp, String alamat) throws SQLException
    {
        stmt = conn.createStatement();
        
        String sql = "INSERT INTO PELAYAN " +
                     "(NAMA, NO_TELP, ALAMAT) " +
                     "VALUES ('" + nama + "', '" + telp + "', '" + alamat + "')";
        stmt.executeUpdate(sql);
    }
    
    private void addOrders(int id_pelanggan, int id_pelayan) throws SQLException
    {
        stmt = conn.createStatement();
        
        String sql = "INSERT INTO ORDERS " +
                     " (ID_PELANGGAN, ID_PELAYAN) " +
                     " VALUES ('" + id_pelanggan + "', '" + id_pelayan + "')";
        stmt.executeUpdate(sql);
    }
    
    private void addOrdersDetail(int Qty, int total, int id_orders, int id_produk) throws SQLException
    {
        stmt = conn.createStatement();
        
        String sql = "INSERT INTO DETAIL_ORDERS " +
                    " (KUANTITAS_PROUDK, TOTAL, ID_ORDERS, ID_PRODUK) " +
                    " VALUES ('" + Qty + "', '" + total + "', '" + id_orders + "', '" + id_produk + "')";
        stmt.executeUpdate(sql);
    }
    
    public ResultSet fetchTable(fetchTable ft) throws SQLException
    {
        stmt = conn.createStatement();
        String sql = "";
        
        if(ft == fetchTable.PELANGGAN)
            sql = "SELECT * FROM PELANGGAN";
        else if(ft == fetchTable.PELAYAN)
            sql = "SELECT * FROM PELAYAN";
        else if(ft == fetchTable.PRODUK)
            sql = "SELECT * FROM PRODUK";
        
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
}