package yez.chicken.management;
import java.util.Scanner;

public class YEZChickenManagement
{
    public static void main(String[] args)
    {
        MenuOption menu = new MenuOption();
        DBManager db = new DBManager();
        Scanner scn = new Scanner(System.in);
        int opt = 0;
        
        db.ConnectDB();
        
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
