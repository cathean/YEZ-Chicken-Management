package yez.chicken.management;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class YEZChickenManagement
{
    public static void main(String[] args) throws SQLException, InterruptedException, ParseException
    {
        MenuOption menu = new MenuOption();
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));
        Scanner brk = new Scanner(System.in);
        int opt;
        boolean incorrectOpt;

        do
        {
            try
            {
                int counter = 1;
                int max;
                menu.clearScreen();
                menu.showLogo();
                menu.mainMenu();
                opt = Integer.parseInt(scn.readLine());
                
                if(opt == 11)
                {
                    menu.clearScreen();
                    menu.showLogo();
                    menu.tambahTransaksi();
                    brk.nextLine();
                }
                else if(opt == 22)
                {
                    menu.clearScreen();
                    menu.showLogo();
                    menu.tampilTransaksi();
                    brk.nextLine();
                }
                else if(opt == 33)
                {
                    menu.clearScreen();
                    menu.showLogo();
                    menu.hapusTransaksi();
                    brk.nextLine();
                }
                if(opt == TableName.PELANGGAN.getValue())
                {
                    do
                    {
                        do
                        {
                            menu.clearScreen();
                            menu.showLogo();
                            max = menu.tampilPelanggan(counter);
                            menu.pelangganMenu();

                            // Option validation
                            String ch = scn.readLine();

                            if(ch.equals("n") && counter < max)
                            {
                                counter++;
                                incorrectOpt = true;
                            }
                            else if(ch.equals("p") && counter > 1)
                            {
                                counter--;
                                incorrectOpt = true;
                            }
                            else if((ch.equals("n") || ch.equals("p")) && (counter >= max || counter <= 1))
                                incorrectOpt = true;
                            else if(Integer.parseInt(ch) >= 0 && Integer.parseInt(ch) <= 3)
                            {
                                opt = Integer.parseInt(ch);
                                incorrectOpt = false;
                            }
                            else
                                incorrectOpt = true;
                        }
                        while(incorrectOpt);

                        menu.clearScreen();
                        menu.showLogo();

                        if(opt == 1)
                        {
                            menu.tambahPelanggan();
                            brk.nextLine();
                        }
                        else if(opt == 2)
                        {
                            menu.cariPelanggan();
                            brk.nextLine();
                        }
                        else if(opt == 3)
                        {
                            menu.hapusPelanggan();
                            brk.nextLine();
                        }
                        else if(opt == 0)
                            opt = -1;
                    }while(opt != -1);
                }
                else if(opt == TableName.ORDERS.getValue())
                {
                    do
                    {
                        do
                        {
                            menu.clearScreen();
                            menu.showLogo();
                            max = menu.tampilOrders(counter);
                            menu.orderMenu();

                            // Option validation
                            String ch = scn.readLine();

                            if(ch.equals("n") && counter < max)
                            {
                                counter++;
                                incorrectOpt = true;
                            }
                            else if(ch.equals("p") && counter > 1)
                            {
                                counter--;
                                incorrectOpt = true;
                            }
                            else if((ch.equals("n") || ch.equals("p")) && (counter >= max || counter <= 1))
                                incorrectOpt = true;
                            else if(Integer.parseInt(ch) >= 0 && Integer.parseInt(ch) <= 3)
                            {
                                opt = Integer.parseInt(ch);
                                incorrectOpt = false;
                            }
                            else
                                incorrectOpt = true;
                        }
                        while(incorrectOpt);

                        menu.clearScreen();
                        menu.showLogo();

                        if(opt == 1)
                        {
                            menu.tambahOrders();
                            brk.nextLine();
                        }
                        else if(opt == 2)
                        {
                            menu.cariOrders();
                            brk.nextLine();
                        }
                        else if(opt == 3)
                        {
                            menu.hapusOrders();
                            brk.nextLine();
                        }
                        else if(opt == 0)
                            opt = -1;
                    }while(opt != -1);
                }
                else if(opt == TableName.DETAIL_ORDERS.getValue())
                {
                    do
                    {
                        do
                        {
                            menu.clearScreen();
                            menu.showLogo();
                            max = menu.tampilDetailOrders(counter);
                            menu.orderMenu();

                            // Option validation
                            String ch = scn.readLine();

                            if(ch.equals("n") && counter < max)
                            {
                                counter++;
                                incorrectOpt = true;
                            }
                            else if(ch.equals("p") && counter > 1)
                            {
                                counter--;
                                incorrectOpt = true;
                            }
                            else if((ch.equals("n") || ch.equals("p")) && (counter >= max || counter <= 1))
                                incorrectOpt = true;
                            else if(Integer.parseInt(ch) >= 0 && Integer.parseInt(ch) <= 3)
                            {
                                opt = Integer.parseInt(ch);
                                incorrectOpt = false;
                            }
                            else
                                incorrectOpt = true;
                        }
                        while(incorrectOpt);

                        menu.clearScreen();
                        menu.showLogo();

                        if(opt == 1)
                        {
                            menu.tambahDetailOrders();
                            brk.nextLine();
                        }
                        else if(opt == 2)
                        {
                            menu.cariDetailOrders();
                            brk.nextLine();
                        }
                        else if(opt == 3)
                        {
                            menu.hapusDetailOrders();
                            brk.nextLine();
                        }
                        else if(opt == 0)
                            opt = -1;
                    }while(opt != -1);
                }
                else if(opt == TableName.PELAYAN.getValue())
                {
                    do
                    {
                        do
                        {
                            menu.clearScreen();
                            menu.showLogo();
                            max = menu.tampilPelayan(counter);
                            menu.pelayanMenu();

                            // Option validation
                            String ch = scn.readLine();

                            if(ch.equals("n") && counter < max)
                            {
                                counter++;
                                incorrectOpt = true;
                            }
                            else if(ch.equals("p") && counter > 1)
                            {
                                counter--;
                                incorrectOpt = true;
                            }
                            else if((ch.equals("n") || ch.equals("p")) && (counter >= max || counter <= 1))
                                incorrectOpt = true;
                            else if(Integer.parseInt(ch) >= 0 && Integer.parseInt(ch) <= 3)
                            {
                                opt = Integer.parseInt(ch);
                                incorrectOpt = false;
                            }
                            else
                                incorrectOpt = true;
                        }
                        while(incorrectOpt);

                        menu.clearScreen();
                        menu.showLogo();

                        if(opt == 1)
                        {
                            menu.tambahPelayan();
                            brk.nextLine();
                        }
                        else if(opt == 2)
                        {
                            menu.cariPelayan();
                            brk.nextLine();
                        }
                        else if(opt == 3)
                        {
                            menu.hapusPelayan();
                            brk.nextLine();
                        }
                        else if(opt == 0)
                            opt = -1;
                    }while(opt != -1);
                }
                else if(opt == TableName.PRODUK.getValue())
                {
                    do
                    {
                        do
                        {
                            menu.clearScreen();
                            menu.showLogo();
                            max = menu.tampilProduk(counter);
                            menu.produkMenu();

                            // Option validation
                            String ch = scn.readLine();

                            if(ch.equals("n") && counter < max)
                            {
                                counter++;
                                incorrectOpt = true;
                            }
                            else if(ch.equals("p") && counter > 1)
                            {
                                counter--;
                                incorrectOpt = true;
                            }
                            else if((ch.equals("n") || ch.equals("p")) && (counter >= max || counter <= 1))
                                incorrectOpt = true;
                            else if(Integer.parseInt(ch) >= 0 && Integer.parseInt(ch) <= 3)
                            {
                                opt = Integer.parseInt(ch);
                                incorrectOpt = false;
                            }
                            else
                                incorrectOpt = true;
                        }
                        while(incorrectOpt);

                        menu.clearScreen();
                        menu.showLogo();

                        if(opt == 1)
                        {
                            menu.tambahProduk();
                            brk.nextLine();
                        }
                        else if(opt == 2)
                        {
                            menu.cariProduk();
                            brk.nextLine();
                        }
                        else if(opt == 3)
                        {
                            menu.hapusProduk();
                            brk.nextLine();
                        }
                        else if(opt == 0)
                            opt = -1;
                    }while(opt != -1);
                }
            }
            catch(IOException e)
            {
                System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
                opt = -2;
                brk.nextLine();
            }
            catch(NumberFormatException e)
            {
                System.err.println(e.getClass().getName() + ": " + e.getMessage()); 
                opt = -2;
                brk.nextLine();
            }
        }while(opt != 0);

    }   
}
