package yez.chicken.management;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class YEZChickenManagement
{
    public static void main(String[] args) throws SQLException
    {
        MenuOption menu = new MenuOption();
        Scanner scn = new Scanner(System.in);
        int opt = 0;
        
        do
        {
            menu.mainMenu();
            opt = scn.nextInt();
            
            if(opt == TableName.PELANGGAN.getValue())
            {
                menu.pelangganMenu();
                opt = scn.nextInt();
                
                if(opt == 1)
                {
                    menu.tambahPelanggan();
                }
                else if(opt == 2)
                {
                    menu.tampilPelanggan();
                }
            }
            else if(opt == TableName.ORDERS.getValue())
            {
                menu.orderMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.DETAIL_ORDERS.getValue())
            {
                menu.detailOrdersMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.PELAYAN.getValue())
            {
                menu.pelayanMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.PRODUK.getValue())
            {
                menu.produkMenu();
                opt = scn.nextInt();
            }
        }while(opt != 0);
        
    }   
}
