package yez.chicken.management;
import java.sql.*;
import java.io.File;

public class DBManager
{
    boolean isDBExist = false;
    String dbName = "YEZ.db";
    
    public void ConnectDB()
    {
        Connection conn = null;
        Statement stmt = null;
        File file = new File(dbName);
        
        if(file.exists())
        {
            System.out.println("Database already exist!");
            isDBExist = true;
        }
        else
            isDBExist = false;

        
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:YEZ.db");

            makeTables(conn, stmt);
        }catch(Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database susccesfully!");
    }
    
    private void makeTables(Connection conn, Statement stmt) throws Exception
    {
        stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS PELANGGAN " +
                        "(ID_PELANGGAN  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAMA          TEXT                              NOT NULL," +
                        " NO_TELP       CHAR(12)                                  ," +
                        " ADDRESS       CHAR(50)                                  )";
        stmt.executeUpdate(sql);
        
        sql =   "CREATE TABLE IF NOT EXISTS PELAYAN " +
                "(ID_PELAYAN    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAMA          TEXT                              NOT NULL," +
                " NO_TELP       CHAR(12)                                  ," +
                " ADDRESS       CHAR(50)                                  )";
        stmt.executeUpdate(sql);
        
        sql =   "CREATE TABLE IF NOT EXISTS PRODUK " +
                "(ID_PRODUK     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAMA_PRODUK   TEXT                              NOT NULL," +
                " JENIS         CHAR(12)                          NOT NULL," +
                " STOK          INTEGER                           NOT NULL," +
                " HARGA         INTEGER                           NOT NULL)";
        stmt.executeUpdate(sql);
        
        sql =   "CREATE TABLE IF NOT EXISTS ORDERS " +
                "(ID_ORDERS     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAMA_PAKET    TEXT                              NOT NULL," +
                " DESKRIPSI     TEXT                              NOT NULL," +
                " HARGA         INTEGER                           NOT NULL," +
                " ID_PELANGGAN  INTEGER                           NOT NULL," +
                " ID_PELAYAN  INTEGER                           NOT NULL," +
                " FOREIGN KEY(ID_PELANGGAN)   REFERENCES PELANGGAN(ID_PELANGGAN)," +
                " FOREIGN KEY(ID_PELAYAN)     REFERENCES PELAYAN(ID_PELAYAN))";
        stmt.executeUpdate(sql);
        
        sql =   "CREATE TABLE IF NOT EXISTS DETAIL_ORDER " +
                "(ID_DETAIL_ORDERS     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " KUANTITAS_PRODUK     INTEGER                           NOT NULL," +
                " ID_ORDERS            INTEGER                           NOT NULL," +
                " ID_PRODUK            INTEGER                           NOT NULL," +
                " FOREIGN KEY(ID_ORDERS)   REFERENCES ORDERS(ID_ORDERS)," +
                " FOREIGN KEY(ID_PRODUK)   REFERENCES PRODUK(ID_PRODUK))";
        stmt.executeUpdate(sql);
    }
}