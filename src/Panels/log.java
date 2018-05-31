package Panels;

import Users.Company;
import Users.Guest;
import Users.SuperAdmin;
import Users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class log {
    private User currentUser;
    private boolean loginStatus = false;
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<String> filters = new ArrayList<>();
    private static OrganicsMapClass<String,User> userMap = new OrganicsMapClass<>();

    /**
     * default constructor
     */
    public log() {
        readFilter();
        welcomePanel();
    }

    /**
     * getting the current user
     * @return user who login or sign to system
     */
    public User getCurrentUser(){
        if(loginStatus)
            return currentUser;
        else
            return null;
    }

    /**
     * the main page of our system
     * give options to user like login signup...
     */
    private void welcomePanel() {
        System.out.println("##############################");
        System.out.println("#        GIRIS PANELI        #");
        System.out.println("##############################");
        System.out.println("# 1-Uye Girisi               #");
        System.out.println("# 2-Uye Ol                   #");
        System.out.println("# 3-Anasayfa                 #");
        System.out.println("##############################");

        Scanner sc = new Scanner(System.in);

        int error, login = 0, userChoice = 3; //1-login 2-register 3-exit

        do {
            error = 0;
            try {
                userChoice = sc.nextInt();
                if (userChoice < 1 || userChoice > 3){
                    System.out.println("Please enter a valid number!");
                    error = 1;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                sc.next(); //clear wrong input
                error = 1;
            }
        } while (error == 1);


        if (userChoice == 1) { //LOGIN
            System.out.println("UYE GIRISI");
            login = 1;

            loginStatus = login();
            if(loginStatus){
                //user.menu();
            }
            else
                welcomePanel();
        }

        if (userChoice == 2 && login == 0) { //REGISTER
            System.out.println("UYE OL");
            register();
        }
    }

    /**
     * read all filters from file
     * @return filter which read from file
     */
    public ArrayList<String> readFilter(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("filters.csv"));
            String line;
            while ( (line = in.readLine() ) != null) {
                filters.add(line);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return filters;
    }

    /**
     * read users to AllUsers arraylist from file
     * @return all users that read from file
     */
    public static ArrayList<User> readUsers(){
        User temp = null;
        allUsers.clear();
        try {
            BufferedReader in = new BufferedReader(new FileReader("users.csv"));
            String line;
            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");

                if(tokens[0].equals("1"))
                    temp = new SuperAdmin(tokens[3],tokens[4],tokens[1],tokens[2],tokens[5],1);
                if(tokens[0].equals("2"))
                    temp = new Company(tokens[3],tokens[4],tokens[1],tokens[2],tokens[5],2);
                if(tokens[0].equals("3"))
                    temp = new Guest(tokens[3],tokens[4],tokens[1],tokens[2],tokens[5],3);

                userMap.put(tokens[4],temp);
                allUsers.add(temp);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return allUsers;
    }

    /**
     * write all filters to file
     */
    public void writeUserFilters(){
        try {

            ArrayList<String> userFilter = new ArrayList<>();
            BufferedReader in = new BufferedReader(new FileReader("users_filters.csv"));
            String line;
            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                String username = tokens[0];
                if(!username.equals(currentUser.getUsername()))
                    userFilter.add(line);
            }

            String currentUserFilter="";
            for (int i=0; i<currentUser.getFilter().size(); i++)
                currentUserFilter += ","+currentUser.getFilter().get(i);

            userFilter.add(currentUser.getUsername()+currentUserFilter);
            in.close();
            // -----------------------------------------------------------
            BufferedWriter writer = new BufferedWriter(new FileWriter("users_filters.csv", false));
            for (int i = 0; i < userFilter.size(); i++) {
                writer.write(userFilter.get(i)+"\n");
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    /**
     *  read user filters from file to arraylist of strings
     */
    public void readUserFilters(){
        ArrayList<String> tempFilters = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("users_filters.csv"));
            String line;
            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                String username = tokens[0];

                for (int i = 0; i < allUsers.size(); i++) {
                    if(allUsers.get(i).getUsername().equals(username)){
                        for (int j = 1; j < tokens.length; j++) {
                            tempFilters.add(tokens[j]);
                        }
                        allUsers.get(i).setFilter(tempFilters);
                        tempFilters.clear();
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * write all users in the end to file
     */
    public void writeUsers(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", false));
            for (int i = 0; i < allUsers.size(); i++) {
                String str =
                        allUsers.get(i).getUserType() + "," +
                                allUsers.get(i).getUsername()+ "," +
                                allUsers.get(i).getPassword() + "," +
                                allUsers.get(i).getName() + "," +
                                allUsers.get(i).getSurname() + "," +
                                allUsers.get(i).getEmail() +"\n";
                writer.write(str);
            }

            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    /**
     * get and check  username and pass to login
     * if username in the list, return true
     * otherwise return false
     * @return boolean true or false
     */
    public boolean login(){
        readUsers();
        readUserFilters();

        String name = "", surname = "", email = "";
        int userType = 0;

        Scanner sc = new Scanner(System.in);
        boolean found = false;

        System.out.println("Kullanıcı Adı: ");
        String username = sc.next();

        System.out.println("Sifre: ");
        String password = sc.next();

        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(password))
            {
                found = true;
                name = allUsers.get(i).getName();
                surname = allUsers.get(i).getSurname();
                email = allUsers.get(i).getEmail();
                userType = allUsers.get(i).getUserType();
                System.out.println("Giris Basarili!");
                break;
            }
        }

        if (!found) {
            System.out.println("Kullanici adi veya sifre hatali. Tekrar deneyin!");
            welcomePanel();
        }

        if (userType == 1)
            currentUser = new SuperAdmin(name,surname,username,password,email,userType);
        else if(userType == 2)
            currentUser = new Company(name,surname,username,password,email,userType);
        else if(userType == 3)
            currentUser = new Guest(name,surname,username,password,email,userType);
        else
            return false;

        return found;
    }

    /**
     * get filters from deafult filters and
     * @return choosen filters
     */
    private ArrayList<String> getFilterFromUser(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> chosenFilter = new ArrayList<>();
        int chosen = 0;

        System.out.println("Filtrelerden Seciniz");
        System.out.println("0-Cikis");
        for (int i = 0; i < filters.size(); i++) {
            System.out.println((i+1)+"-"+filters.get(i));
        }

        do {

            try {
                chosen = sc.nextInt();

                if(chosen<0 || chosen>=filters.size()) {
                    System.out.println("Yanlis Giris Yaptiniz!");
                    throw new ArrayIndexOutOfBoundsException();
                }
                if(chosen==0)
                    break;
                if(!chosenFilter.contains(filters.get(chosen-1)))
                    chosenFilter.add(filters.get(chosen-1));
                else
                    throw new ArrayIndexOutOfBoundsException();

            }
            catch (ArrayIndexOutOfBoundsException m) {
                System.out.println("Onceden eklenmis veya gecersiz filtre - Tekrar giriniz!");

            } catch (Exception e) {
                System.out.println("Onceden eklenmis veya gecersiz filtre numarası - Tekrar giriniz!");
                sc.next(); //clear wrong input
            }

        } while (true);
        return chosenFilter;
    }

    /**
     * register a new guest
     */
    public void register() {
        readUsers();
        readUserFilters();

        String name = "", surname = "", username = "", password = "", email = "";
        int userType = 3;
        ArrayList<String > chosenFilter ;
        Scanner sc = new Scanner(System.in);

        System.out.println("Isim: ");
        name = sc.next();

        System.out.println("Soyisim: ");
        surname = sc.next();

        readUsers();

        System.out.println("Kullanici Adi: ");
        username = sc.next();

        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                System.out.println("Username already exist!");
                register();
                return;
            }
        }

        System.out.println("Sifre: ");
        password = sc.next();

        System.out.println("Email: ");
        email = sc.next();

        currentUser = new Guest(name,surname,username,password,email,userType);
        chosenFilter = getFilterFromUser();
        currentUser.setFilter(chosenFilter);

        allUsers.add(currentUser); // kullanıcı listesine eklendi

        writeUsers();
        writeUserFilters();

        System.out.println("Kayit Islemi Basarili!");
        loginStatus = true;
    }

}


