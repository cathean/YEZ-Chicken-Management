package yez.chicken.management;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class YEZChickenManagement
{
    public static void main(String[] args) throws SQLException, IOException, InterruptedException
    {
        MenuOption menu = new MenuOption();
        Scanner scn = new Scanner(System.in);
        int opt = 0;
        
        do
        {
            menu.clearScreen();
            menu.showLogo();
            menu.mainMenu();
            opt = scn.nextInt();
            
            if(opt == TableName.PELANGGAN.getValue())
            {
                do
                {
                    menu.clearScreen();
                    menu.showLogo();
                    menu.pelangganMenu();
                    opt = scn.nextInt();

                    if(opt == 1)
                    {
                        menu.clearScreen();
                        menu.showLogo();
                        menu.tambahPelanggan();
                    }
                    else if(opt == 2)
                    {
                        menu.clearScreen();
                        menu.showLogo();
                        menu.tampilPelanggan();
                    }
                    else if(opt == 3)
                    {
                        menu.clearScreen();
                        menu.showLogo();
                        menu.hapusPelanggan();
                    }
                    else if(opt == 0)
                        opt = -1;
                }while(opt != -1);
            }
            else if(opt == TableName.ORDERS.getValue())
            {
                menu.clearScreen();
                menu.showLogo();
                menu.orderMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.DETAIL_ORDERS.getValue())
            {
                menu.clearScreen();
                menu.showLogo();
                menu.detailOrdersMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.PELAYAN.getValue())
            {
                menu.clearScreen();
                menu.showLogo();
                menu.pelayanMenu();
                opt = scn.nextInt();
            }
            else if(opt == TableName.PRODUK.getValue())
            {
                menu.clearScreen();
                menu.showLogo();
                menu.produkMenu();
                opt = scn.nextInt();
            }
        }while(opt != 0);
        
    }   
}
