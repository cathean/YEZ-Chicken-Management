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
    public PreparedStatement pstmt = null;
    public ResultSet rs = null;
    
    private String db = "YEZ";
    private String host = "localhost";
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost/";
    
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

            // Initialization to the datables
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
        String sql = "CREATE TABLE IF NOT EXISTS `PELANGGAN` " +
                     "(`ID`  INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                     " `NAMA`          VARCHAR(20)          NOT NULL," +
                     " `NO_TELP`       VARCHAR(12)          NOT NULL," +
                     " `ALAMAT`        vARCHAR(50)          NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `PELAYAN` " +
              "(`ID`    INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `NAMA`          VARCHAR(20)            NOT NULL," +
              " `NO_TELP`       VARCHAR(12)                    ," +
              " `ALAMAT`        VARCHAR(50)                    )";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `PRODUK` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `NAMA`   VARCHAR(20)                    NOT NULL," +
              " `JENIS`         VARCHAR(12)             NOT NULL," +
              " `STOK`          INTEGER                 NOT NULL," +
              " `HARGA`         INTEGER                 NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `ORDERS` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `TGL_PEMBELIAN` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL," +
              " `ID_PELANGGAN`  INT                         NOT NULL," +
              " `ID_PELAYAN`  INT                           NOT NULL," +
              " FOREIGN KEY(ID_PELANGGAN)   REFERENCES PELANGGAN(ID)," +
              " FOREIGN KEY(ID_PELAYAN)     REFERENCES PELAYAN(ID))";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS `DETAIL_ORDERS` " +
              "(`ID`     INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
              " `KUANTITAS_PRODUK`     INT              NOT NULL," +
              " `TOTAL`                INT              NOT NULL," +
              " `ID_ORDERS`            INT              NOT NULL," +
              " `ID_PRODUK`            INT              NOT NULL," +
              " FOREIGN KEY(ID_ORDERS)     REFERENCES ORDERS(ID)," +
              " FOREIGN KEY(ID_PRODUK)     REFERENCES PRODUK(ID))";
        stmt.executeUpdate(sql);
        
        // ADD PRODUK COLUMNS ----
        
        sql = "REPLACE INTO `PRODUK`" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (1, 'Nasi Putih', 'Makanan', 25, 2000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (2, 'Ayam Bakar Paha', 'Makanan', 12, 8000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (3, 'Ayam Goreng Paha', 'Makanan', 4, 8000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (4, 'Ayam Bakar Dada', 'Makanan', 16, 10000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (5, 'Ayam Goren Dada', 'Makanan', 13, 10000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (6, 'Teh Manis', 'Minuman', 7, 3000)";
        stmt.executeUpdate(sql);
        
        sql = "REPLACE INTO PRODUK" +
              "(ID, NAMA, JENIS, STOK, HARGA)" +
              "VALUES (7, 'Nutrisasri', 'Minuman', 12, 3000)";
        stmt.executeUpdate(sql);
        
        // ADD PELANGGAN COLUMNS ---
        
        sql = "REPLACE INTO PELAYAN " +
              "(NAMA, NO_TELP, ALAMAT) " +
              "VALUES ('Thoriq', '08123456789', 'Tubagus Ismail Dalam' )";
        stmt.executeUpdate(sql);
    }
    
    public void addPelanggan(String nama, String telp, String alamat) throws SQLException
    {
        String sql = "INSERT INTO PELANGGAN " +
                     "(NAMA, NO_TELP, ALAMAT) " +
                     "VALUES (?, ?, ?)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, telp);
        pstmt.setString(1, alamat);
        
        pstmt.executeUpdate();
    }
    
    private void addPelayan(String nama, String telp, String alamat) throws SQLException
    {
        String sql = "INSERT INTO PELAYAN " +
                     "(NAMA, NO_TELP, ALAMAT) " +
                     "VALUES ('" + nama + "', '" + telp + "', '" + alamat + "')";
        stmt.executeUpdate(sql);
    }
    
    private void addOrders(int id_pelanggan, int id_pelayan) throws SQLException
    {
        String sql = "INSERT INTO ORDERS " +
                     " (ID_PELANGGAN, ID_PELAYAN) " +
                     " VALUES ('" + id_pelanggan + "', '" + id_pelayan + "')";
        stmt.executeUpdate(sql);
    }
    
    private void addOrdersDetail(int Qty, int total, int id_orders, int id_produk) throws SQLException
    {
        String sql = "INSERT INTO DETAIL_ORDERS " +
                    " (KUANTITAS_PROUDK, TOTAL, ID_ORDERS, ID_PRODUK) " +
                    " VALUES ('" + Qty + "', '" + total + "', '" + id_orders + "', '" + id_produk + "')";
        stmt.executeUpdate(sql);
    }
    
    public ResultSet fetchTable(fetchTable ft) throws SQLException
    {
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