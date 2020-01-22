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
        }catch(SQLException e)
        {
            System.out.println("SQLException Error");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException Error");
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
              " `TGL_PEMBELIAN` DATE                           ," +
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
              " FOREIGN KEY(ID_ORDERS)     REFERENCES ORDERS(ID) ON DELETE CASCADE," +
              " FOREIGN KEY(ID_PRODUK)     REFERENCES PRODUK(ID) ON DELETE CASCADE," +
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
    
    public ResultSet srcRowPelanggan(String identity) throws SQLException
    {
        String sql = " SELECT * FROM PELANGGAN " +
                     " WHERE " + identity + " AND STATUS=1";
      
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void updtRowPelanggan(int id_pelanggan, String nama, String no_telp, String alamat) throws SQLException
    {
        if(nama.equals("000"))
            nama = "NAMA";
        else if(no_telp.equals("000"))
            no_telp = "NO_TELP";
        else if(alamat.equals("000"))
            alamat = "ALAMAT";
        
        String sql = " UPDATE PELANGGAN                " +
                     " SET NAMA=?, NO_TELP=?, ALAMAT=? " +
                     " WHERE ID=? AND STATUS=1         ";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, no_telp);
        pstmt.setString(3, alamat);
        pstmt.setInt(4, id_pelanggan);
        
        pstmt.executeUpdate();
    }
    
    public void remRowPelanggan(int id_pelanggan) throws SQLException
    {
        String sql = " UPDATE PELANGGAN " +
                     " SET STATUS=0     " +
                     " WHERE ID=?       ";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_pelanggan);
        
        pstmt.executeUpdate();
    }
    
    // PELAYAN TABLE OPERATION
    
    public void addRowPelayan(String nama, String telp, String alamat) throws SQLException
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
    
    public ResultSet srcRowPelayan(String identity) throws SQLException
    {
        String sql = " SELECT * FROM PELAYAN " +
                     " WHERE " + identity + " AND STATUS=1";
      
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void updtRowPelayan(int id_pelayan, String nama, String no_telp, String alamat) throws SQLException
    {
        if(nama.equals("000"))
            nama = "NAMA";
        else if(no_telp.equals("000"))
            no_telp = "NO_TELP";
        else if(alamat.equals("000"))
            alamat = "ALAMAT";
        
        String sql = " UPDATE PELAYAN                " +
                     " SET NAMA=?, NO_TELP=?, ALAMAT=? " +
                     " WHERE ID=? AND STATUS=1         ";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, no_telp);
        pstmt.setString(3, alamat);
        pstmt.setInt(4, id_pelayan);
        
        pstmt.executeUpdate();
    }
    
    public void remRowPelayan(int id_pelayan) throws SQLException
    {
        String sql = " UPDATE PELAYAN " +
                     " SET STATUS=0     " +
                     " WHERE ID=?       ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_pelayan);
        
        pstmt.executeUpdate();
    }
    
    // ORDERS TABLE OPERATION
    
    public void addRowOrders(int id_pelanggan, int id_pelayan) throws SQLException
    {
        String sql = " INSERT INTO ORDERS " +
                     " (TGL_PEMBELIAN, ID_PELANGGAN, ID_PELAYAN, STATUS) " +
                     " VALUES (CURDATE(), " + id_pelanggan + ", " + id_pelayan + ", 1)";
        
        pstmt = conn.prepareStatement(sql);
        
        stmt.executeUpdate(sql);
    }
    
    public ResultSet srcRowOrders(String identity) throws SQLException
    {
        String sql = " SELECT * FROM ORDERS " +
                     " WHERE " + identity + " AND STATUS=1";
      
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void updtRowOrders(int id_orders, java.sql.Date date, int id_pelanggan, int id_pelayan) throws SQLException
    { 
        String sql = " UPDATE ORDERS                                     " +
                     " SET TGL_PEMBELIAN=?, ID_PELANGGAN=?, ID_PELAYAN=? " +
                     " WHERE ID=? AND STATUS=1                           ";
        
        pstmt = conn.prepareStatement(sql);
        
        if(date.equals("000"))
            pstmt.setString(1, "TGL_PEMBELIAN");
        else
            pstmt.setDate(1, date);
        
        if(id_pelanggan == 0)
            pstmt.setString(2, "ID_PELANGGAN");
        else
            pstmt.setInt(2, id_pelanggan);
        
        if(id_pelayan == 0)
            pstmt.setString(3, "ID_PELAYAN");
        else
            pstmt.setInt(3, id_pelayan);
        
        pstmt.setInt(4, id_orders);
        
        pstmt.executeUpdate();
    }
    
    public void remRowOrders(int id_orders) throws SQLException
    {
        String sql = " UPDATE ORDERS    " +
                     " SET STATUS=0     " +
                     " WHERE ID=?       ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_orders);
        
        pstmt.executeUpdate();
    }
    
    // ORDERS DETAIL TABLE OPERATION
    
    public void addRowOrdersDetail(int Qty, int total, int id_orders, int id_produk) throws SQLException
    {
        String sql = "INSERT INTO DETAIL_ORDERS " +
                    " (KUANTITAS_PRODUK, TOTAL, ID_ORDERS, ID_PRODUK, STATUS) " +
                    " VALUES (" + Qty + "," + total + ", " + id_orders + ", " + id_produk + ", 1)";
        
        stmt.executeUpdate(sql);
    }
    
     public ResultSet srcRowOrdersDetail(String identity) throws SQLException
    {
        String sql = " SELECT * FROM DETAIL_ORDERS " +
                     " WHERE " + identity + " AND STATUS=1";
      
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void updtRowOrdersDetail(int id_orders_detail, int id_orders, int id_produk, int kuantitas) throws SQLException
    { 
        int price = getPrice(id_produk);
        
        String sql = " UPDATE DETAIL_ORDERS                                              " +
                     " SET ID_ORDERS=?, ID_PRODUK=?, KUANTITAS_PRODUK=?, TOTAL=?  " +
                     " WHERE ID=? AND STATUS=1                                    ";
        
        pstmt = conn.prepareStatement(sql);
        
        if(id_orders == 0)
            pstmt.setString(1, "ID_ORDERS");
        else
            pstmt.setInt(1, id_orders);
        
        if(id_produk == 0)
            pstmt.setString(2, "ID_PRODUK");
        else
            pstmt.setInt(2, id_produk);
        
        if(kuantitas == 0)
            pstmt.setString(3, "KUANTITAS_PRODUK");
        else
            pstmt.setInt(3, kuantitas);
        
        pstmt.setInt(4, price * kuantitas);
        pstmt.setInt(5, id_orders_detail);
        
        pstmt.executeUpdate();
    }
    
    public void remRowOrdersDetail(int id_detail_orders) throws SQLException
    {
        String sql = " UPDATE DETAIL_ORDERS " +
                     " SET STATUS=0         " +
                     " WHERE ID=?           ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_detail_orders);
        
        pstmt.executeUpdate();
    }
    
    // PRODUK TABLE OPERATION
    
    public void addRowProduk(String nama, String jenis, int stok, int harga) throws SQLException
    {
        String sql = " INSERT INTO PRODUK " +
                     " (NAMA, JENIS, STOK, HARGA, STATUS) " +
                     " VALUES (?, ?, ?, ?, 1)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nama);
        pstmt.setString(2, jenis);
        pstmt.setInt(3, stok);
        pstmt.setInt(4, harga);
        
        pstmt.executeUpdate();
    }
    
    public ResultSet srcRowProduk(String identity) throws SQLException
    {
        String sql = " SELECT * FROM PRODUK " +
                     " WHERE " + identity + " AND STATUS=1";
      
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void updtRowProduk(int id_produk, String nama, String jenis, int stok, int harga) throws SQLException
    {
        String sql = " UPDATE PRODUK                                     " +
                     " SET NAMA=?, JENIS=?, STOK=?, HARGA=? " +
                     " WHERE ID=? AND STATUS=1                           ";
        
        pstmt = conn.prepareStatement(sql);
        
        if(nama.equals("000"))
            pstmt.setString(1, "NAMA");
        else
            pstmt.setString(1, nama);
        
        if(jenis.equals("000"))
            pstmt.setString(2, "JENIS");
        else
            pstmt.setString(2, jenis);
        
        if(stok == 0)
            pstmt.setString(3, "STOK");
        else
            pstmt.setInt(3, stok);
        
        if(harga == 0)
            pstmt.setString(4, "HARGA");
        else
            pstmt.setInt(4, harga);
        
        pstmt.setInt(5, id_produk);
        
        pstmt.executeUpdate();
    }
    
    public void remRowProduk(int id_produk) throws SQLException
    {
        String sql = " UPDATE PRODUK    " +
                     " SET STATUS=0     " +
                     " WHERE ID=?       ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_produk);
        
        pstmt.executeUpdate();
    }
    
    // Fetch table result
    public ResultSet fetchTable(TableName ft, int n) throws SQLException
    {
        String sql = "";
        int m = n + 5;
        
        if(ft == TableName.PELANGGAN)
            sql = "SELECT * FROM PELANGGAN WHERE STATUS=1 LIMIT " + n + "," + m;
        else if(ft == TableName.ORDERS)
            sql = "SELECT * FROM ORDERS WHERE STATUS=1 LIMIT " + n + "," + m;
        else if(ft == TableName.DETAIL_ORDERS)
            sql = "SELECT * FROM DETAIL_ORDERS WHERE STATUS=1 LIMIT " + n + "," + m;
        else if(ft == TableName.PELAYAN)
            sql = "SELECT * FROM PELAYAN WHERE STATUS=1 LIMIT " + n + "," + m;
        else if(ft == TableName.PRODUK)
            sql = "SELECT * FROM PRODUK WHERE STATUS=1 LIMIT " + n + "," + m;
        
        rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
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
    
    public int fetchLastRowID(TableName ft) throws SQLException
    {
        String what = "";
        
        if(ft == TableName.PELANGGAN)
            what = "PELANGGAN";
        else if(ft == TableName.ORDERS)
            what = "ORDERS";
        else if(ft == TableName.DETAIL_ORDERS)
            what = "DETAIL_ORDERS";
        else if(ft == TableName.PELAYAN)
            what = "PELAYAN";
        else if(ft == TableName.PRODUK)
            what = "PRODUK";
        
        String sql = "SELECT * FROM " + what + " ORDER BY ID DESC LIMIT 1";
        rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("ID");
    }
    
    public ResultSet fetchProdukFromOrders(int id_orders) throws SQLException
    {
        String sql = "SELECT * FROM PRODUK, DETAIL_ORDERS WHERE PRODUK.ID=DETAIL_ORDERS.ID_PRODUK AND DETAIL_ORDERS.ID_ORDERS=" + id_orders;
        rs = stmt.executeQuery(sql);
        return rs;
    }
    
    public int getPrice(int id_produk) throws SQLException
    {
        String sql = "SELECT * FROM PRODUK WHERE ID=" + id_produk;
        rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("HARGA");
    }
    
    public int countRows(TableName ft) throws SQLException
    {
        String sql = "";
        int rowCount = 0;
        
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
        
        if(rs.last())
        {
            rowCount = rs.getRow(); 
            rs.beforeFirst();
        }
        
        return rowCount;
    }
    
    public void remLinked(int id_pelanggan) throws SQLException
    {
        String sql = "UPDATE PELANGGAN SET STATUS=0 WHERE ID=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_pelanggan);
        pstmt.executeUpdate();
        
        String identity = "ID_PELANGGAN=" + id_pelanggan;
        rs = srcRowOrders(identity);
        rs.next();
        int id_orders = rs.getInt("ID");
        
        sql = "UPDATE ORDERS SET STATUS=0 WHERE ID=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_orders);
        pstmt.executeUpdate();
        
        sql = "UPDATE DETAIL_ORDERS SET STATUS=0 WHERE ID_ORDERS=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id_orders);
        pstmt.executeUpdate();
    }
}