package Users;

import Interface.LoginPanel;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import static Interface.LoginPanel.allUsers;

public class SuperAdmin extends User {
    
    /**
     * constructor
     * @param name of the admin
     * @param surname of the admin
     * @param username of the admin
     * @param password of the admin
     * @param email of the admin
     * @param userType of the admin
     */
    public SuperAdmin(String name, String surname, String username, String password, String email, int userType) {
        super(name, surname, username, password, email, userType);
    }


    /**
     *
     * @return filter
     */
    @Override
    public ArrayList<String> getFilter() {
        return null;
    }

    @Override
    public void updateInfo() {
        System.out.println("Güncelleme yapmak istediginiz adı giriniz : ");
        Scanner scan = new Scanner(System.in);
        String usrName = scan.next();
        System.out.println("Yeni Bilgileri Giriniz ");
        System.out.println("Ad : ");
        String name = scan.next();

        System.out.println("Soyad : ");
        String surname = scan.next();

        System.out.println("Sifre : ");
        String password = scan.next();

        System.out.println(usrName);

        int index = -1;
        for (int i = 0; i < allUsers.size(); ++i){
            if (allUsers.get(i).getUsername().equals(usrName)){
                index = i;
                allUsers.get(i).setName(name);
                allUsers.get(i).setSurname(surname);
                allUsers.get(i).setPassword(password);
            }
        }
        System.out.println("Bilgiler Güncellendi");
        System.out.print("Ad : ");
        System.out.println(allUsers.get(index).getName());
        System.out.print("Soyad : ");
        System.out.println(allUsers.get(index).getSurname());
        System.out.print("Sifre : ");
        System.out.println(allUsers.get(index).getPassword());
        LoginPanel.writeUsers();

    }

    

    /**
     * equals method
     * @param o object
     * @return boolean
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail());
    }
}
