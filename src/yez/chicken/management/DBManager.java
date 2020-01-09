package yez.chicken.management;
import java.sql.*;
import java.io.File;

public class DBManager
{
    public Connection conn = null;
    public Statement stmt = null;
    public PreparedStatement pstmt = null;
    public ResultSet rs = null;
    
    final private String db = "YEZ";
    final private String host = "localhost";
    final private String user = "root";
    final private String pass = "";
    final private String url = "jdbc:mysql://localhost/";
    
    public void ConnectDB()
    {
        try
        {
            // Connecting to database
            System.out.println("Connecting to database...");
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
            
            // Create the database if not exist
            System.out.println("Creating the database...");
            
            String sql = "CREATE DATABASE IF NOT EXISTS " + db;
            stmt.executeUpdate(sql);
            sql = "USE " + db;
            stmt.executeUpdate(sql);
            
            System.out.println("Database (already) created!");

            // Initialization data to the database
            initTablesData();
        }catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database susccesfully!");
    }
    
    // Database table initialization
    private void initTablesData() throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `PELANGGAN` " +
                     "(`ID`  INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                     " `NAMA`          VARCHAR(20)          NOT NULL," +
                     " `NO_TELP`       VARCHAR(12)          NOT NULL," +
                     " `ALAMAT`        VARCHAR(50)          NOT NULL," +
                     " `STATUS`        INT                  NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `PELAYAN` " +
              "(`ID`    INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `NAMA`          VARCHAR(20)            NOT NULL," +
              " `NO_TELP`       VARCHAR(12)                    ," +
              " `ALAMAT`        VARCHAR(50)                    ," +
              " `STATUS`        INT                    NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `PRODUK` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `NAMA`   VARCHAR(20)                    NOT NULL," +
              " `JENIS`         VARCHAR(12)             NOT NULL," +
              " `STOK`          INTEGER                 NOT NULL," +
              " `HARGA`         INTEGER                 NOT NULL," +
              " `STATUS`        INT                     NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `ORDERS` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `TGL_PEMBELIAN` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL," +
              " `ID_PELANGGAN`  INT                         NOT NULL," +
              " `ID_PELAYAN`    INT                         NOT NULL," +
              " FOREIGN KEY(ID_PELANGGAN)   REFERENCES PELANGGAN(ID)," +
              " FOREIGN KEY(ID_PELAYAN)     REFERENCES PELAYAN(ID)," +
              " `STATUS`        INT                       NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `DETAIL_ORDERS` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `KUANTITAS_PRODUK`     INT              NOT NULL," +
              " `TOTAL`                INT              NOT NULL," +
              " `ID_ORDERS`            INT              NOT NULL," +
              " `ID_PRODUK`            INT              NOT NULL," +
              " FOREIGN KEY(ID_ORDERS)     REFERENCES ORDERS(ID)," +
              " FOREIGN KEY(ID_PRODUK)     REFERENCES PRODUK(ID)," +
              " `STATUS`        INT                    NOT NULL)";
        stmt.executeUpdate(sql);
        
        // ADD PRODUK COLUMNS ----
        
        sql = "REPLACE INTO `PRODUK`" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (1, 'Nasi Putih', 'Makanan', 25, 2000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (2, 'Ayam Bakar Paha', 'Makanan', 12, 8000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (3, 'Ayam Goreng Paha', 'Makanan', 4, 8000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (4, 'Ayam Bakar Dada', 'Makanan', 16, 10000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (5, 'Ayam Goren Dada', 'Makanan', 13, 10000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (6, 'Teh Manis', 'Minuman', 7, 3000, 1)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA, STATUS)" +
              "VALUES (7, 'Nutrisasri', 'Minuman', 12, 3000, 1)";
        stmt.executeUpdate(sql);
        
        // ADD PELANGGAN COLUMNS ---
        
        sql = "REPLACE INTO PELAYAN " +
              "(NAMA, NO_TELP, ALAMAT, STATUS) " +
              "VALUES ('Thoriq', '08123456789', 'Tubagus Ismail Dalam', 1)";
        stmt.executeUpdate(sql);
    }
    
    // PELANGAN TABLE OPERATION
    
    public void addRowPelanggan(String nama, String telp, String alamat) throws SQLException
    {
        String sql = " INSERT INTO PELANGGAN " +
                     " (NAMA, NO_TELP, ALAMAT, STATUS) " +
                     " VALUES (?, ?, ?, 1)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, telp);
        pstmt.setString(3, alamat);
        
        pstmt.executeUpdate();
    }
    
    private void addRowPelayan(String nama, String telp, String alamat) throws SQLException
    {
        String sql = " INSERT INTO PELAYAN " +
                     " (NAMA, NO_TELP, ALAMAT, STATUS) " +
                     " VALUES (?, ?, ?, 1)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, telp);
        pstmt.setString(3, alamat);
        
        pstmt.executeUpdate();
    }
    
    private void addRowOrders(int id_pelanggan, int id_pelayan) throws SQLException
    {
        String sql = " INSERT INTO ORDERS " +
                     " (ID_PELANGGAN, ID_PELAYAN, STATUS) " +
                     " VALUES (?, ?, 1)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_pelanggan);
        pstmt.setInt(2, id_pelayan);
        
        stmt.executeUpdate(sql);
    }
    
    private void addRowOrdersDetail(int Qty, int total, int id_orders, int id_produk) throws SQLException
    {
        String sql = "INSERT INTO DETAIL_ORDERS " +
                    " (KUANTITAS_PROUDK, TOTAL, ID_ORDERS, ID_PRODUK, STATUS) " +
                    " VALUES (?, ?, ?, ?, 1)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Qty);
        pstmt.setInt(2, total);
        pstmt.setInt(3, id_orders);
        pstmt.setInt(4, id_produk);
        
        stmt.executeUpdate(sql);
    }
    
    
    // Fetch table
    public ResultSet fetchTable(TableName ft) throws SQLException
    {
        String sql = "";
        
        if(ft == TableName.PELANGGAN)
            sql = "SELECT * FROM PELANGGAN WHERE STATUS=1";
        else if(ft == TableName.ORDERS)
            sql = "SELECT * FROM ORDERS WHERE STATUS=1";
        else if(ft == TableName.DETAIL_ORDERS)
            sql = "SELECT * FROM DETAIL_ORDERS WHERE STATUS=1";
        else if(ft == TableName.PELAYAN)
            sql = "SELECT * FROM PELAYAN WHERE STATUS=1";
        else if(ft == TableName.PRODUK)
            sql = "SELECT * FROM PRODUK WHERE STATUS=1";
        
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
}