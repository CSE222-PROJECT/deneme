package Panels;

import BasketWallet.Basket;
import Interface.LoginPanel;
import java.io.IOException;
import java.util.Scanner;

import Products.Product;
import Products.QueueList;
import Users.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SuperAdminPanel {
    
    public static  QueueList<String > confirms = new QueueList<>();
    public static  QueueList<Basket> orderQueue = new QueueList<>();
    public static ArrayList<Basket> orderList= new ArrayList<>();
    private String USERSFILE = "users.csv";

    
    private int choice;

    public SuperAdminPanel () {
        SuperAdminMenu();
    }

    public void SuperAdminMenu () {

        System.out.println("1) Bekleyen Siparişleri Göster");
        System.out.println("2) Bekleyen Siparişleri Onayla");
        System.out.println("3) Önceki Sayfa");
        Scanner in = new Scanner(System.in);
        choice = in.nextInt();
        SuperAdminPage(choice);

    }
    
    public static void readConfirm() throws IOException {

        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(
                    new FileReader("confirmBasket.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        {
                String line2 = null;
                for (int i = 0; (line2 = reader2.readLine()) != null; ++i)
                    confirms.add(line2);
            }

        }


    public static void showBaskets (){

        try {
            readConfirm();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ConfirmsSize : " + confirms.size());
        for (int i = 0; i < confirms.size(); ++i){
            System.out.println(confirms.get(i));
        }
    }

    public static void readOrder () {

        try (BufferedReader reader = new BufferedReader(
                new FileReader("user_basket.csv"))){

            String line = null;
            for(int i=0; (line = reader.readLine()) != null; i++) {
                String[] fields = line.split(";");

                Basket temp2=new Basket(fields[0], Integer.parseInt(fields[1]), new Product(fields[2], Double.parseDouble(fields[3]), fields[4], fields[5], fields[6],fields[7], Integer.parseInt(fields[8])));
                orderList.add(temp2);
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Confirm the basket
     */
    public static boolean confirmBasket() {

        readOrder();

        try {

            readConfirm();

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < orderList.size(); ++i) {
            orderQueue.add(orderList.get(i));
           // System.out.println(orderQueue.get(i));

        }
        System.out.println("ConfirmsSize : " + confirms.size());
        for (int i=0 ; i < orderQueue.size() ; ++i) {
            for ( int j = 0; j < confirms.size(); ++j) {
                if (orderQueue.get(i).getUserName().equals(confirms.get(j))) {
                    System.out.println("sayı : "+orderQueue.get(i).getUserProduct().numOfProduct);
                    orderQueue.get(i).getUserProduct().numOfProduct--;
                }
            }
        }
        writeAgainCsv();
        System.out.println("orderQueueSize : "+ orderQueue.size());
        System.out.println("silinen: "+orderQueue.remove());
        System.out.println("orderQueueSize** : "+ orderQueue.size());

        ArrayList<Basket> tempBasket = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Basket> newBasket = new ArrayList<>();
        System.out.println("Silme******");

        while (confirms.size() != 0){
            String basketID = confirms.poll();
            System.out.println(basketID + " kodlu sepet onaylandı ...");

            for (int i = 0; i < orderQueue.size(); ++i) {
                if (orderQueue.get(i).getUserName().equals(basketID)) {
                    tempBasket.add(orderQueue.get(i));
                    index.add(i);
                }
            }

            for (int j = 0; j < index.size(); ++j) {
                orderQueue.poll();
            }

            writeOrderQueue();
        }

        return true;
    }

    public static void writeOrderQueue (){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("user_basket.csv", false));
            BufferedWriter writer2 = new BufferedWriter(new FileWriter("confirmBasket.csv", false));

            for (int j = 0; j < orderQueue.size(); j++) {
                writer.write(orderQueue.get(j).getDataFields());
            }

            for (int j = 0; j < confirms.size(); j++) {
                writer2.write(confirms.get(j));
            }

            writer.close();
            writer2.close();

        }catch (IOException e){

            System.out.println(e);
        }
    }

    public static void writeAgainCsv () {
        PrintWriter pWrite = null;
        try {
            pWrite = new PrintWriter(new File("Products.csv"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder  sBuilder = new StringBuilder();
        for (int i=0; i<orderQueue.size() ; i++ ) {
            if (orderQueue.get(i).getUserProduct().getSize() == null && orderQueue.get(i).getUserProduct().getGender()!= null){
                sBuilder.append(orderQueue.get(i).getUserProduct().getName() + ";" + orderQueue.get(i).getUserProduct().getPrice() +  ";"
                        + orderQueue.get(i).getUserProduct().getGender() + ";" + orderQueue.get(i).getUserProduct().getTag() + ";"
                        + orderQueue.get(i).getUserProduct().getContent() + ";" + orderQueue.get(i).getUserProduct().getNumberOfProduct() + "\n");
            }
            else if(orderQueue.get(i).getUserProduct().getGender() == null && orderQueue.get(i).getUserProduct().getSize() == null){
                sBuilder.append( orderQueue.get(i).getUserProduct().getName() + ";" + orderQueue.get(i).getUserProduct().getPrice() +  ";"
                        + orderQueue.get(i).getUserProduct().getTag() + ";"+
                        orderQueue.get(i).getUserProduct().getContent() + ";" + orderQueue.get(i).getUserProduct().getNumberOfProduct() + "\n");
            }
            else {
                sBuilder.append( orderQueue.get(i).getUserProduct().getName() + ";" + orderQueue.get(i).getUserProduct().getPrice() + ";"
                        + orderQueue.get(i).getUserProduct().getSize() + ";"
                        + (orderQueue.get(i)).getUserProduct().getGender() + ";" + orderQueue.get(i).getUserProduct().getTag() + ";"
                        + orderQueue.get(i).getUserProduct().getContent() + ";" + orderQueue.get(i).getUserProduct().getNumberOfProduct() + "\n");
            }

        }
        pWrite.write(sBuilder.toString());
        pWrite.flush();
        pWrite.close();


    }

    /**
     * Get infos and add c company to database
     * @return success
     */
    public boolean addCompany(){
        Scanner sc = new Scanner(System.in);
        int userType = 2;

        System.out.println("Name: ");
        String name = sc.next();

        System.out.println("Surname: ");
        String surname = sc.next();

        System.out.println("Username: ");
        String temp = sc.next();

        ArrayList<User> AllUsers = LoginPanel.readUsers();

        for (int i = 0; i < AllUsers.size(); i++) {
            if (temp.equals(AllUsers.get(i).getUsername())) {
                System.out.println("Username already exist!");
                return false;
            }
        }
        String username = temp;

        System.out.println("Password ");
        String password = sc.next();

        System.out.println("Email: ");
        String email = sc.next();

        String str = userType + "," + username + "," + password + "," + name + "," + surname + email + "\n";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERSFILE, true));
            writer.append(str);

            writer.close();

            System.out.println("Users.Company added successfully!");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        return true;
    }

    public void SuperAdminPage (int choice) {

        switch (choice) {
            case 1 : showBaskets();
                break;
            case 2 : confirmBasket();
                break;
            case 3 :
                break;

            default:
                System.out.println("Hatalı Seçim");
                break;
        }

    }



}
