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
            
            switch(opt)
            {
                case 1:
                    menu.orderMenu();
                    opt = scn.nextInt();
                    
                    switch(opt)
                    {
                        case 2:
                            menu.addOrderMenu();
                            break;
                        case 0:
                            opt = -1;
                            break;
                    }
                    break;
                case 2:
                    menu.productMenu();
                    opt = scn.nextInt();
                    
                    switch(opt)
                    {
                        case 0:
                            opt = -1;
                            break;
                    }
                    break;
            }
            
        }while(opt != 0);
        
    }   
}
