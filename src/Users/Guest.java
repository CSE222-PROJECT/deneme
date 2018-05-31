package Users;



import Interface.LoginPanel;
import java.util.Objects;
import java.util.Scanner;

import static Interface.LoginPanel.allUsers;

public class Guest extends User {

    private String address;
    //private BasketWallet.Basket userBasket= new BasketWallet.Basket();
    //private BasketWallet.Wallet userWallet= new BasketWallet.Wallet();
    /**
     */
    public Guest(String name, String surname, String username, String password, String email, int userType) {
        super(name, surname, username, password, email, userType);
    }
    /**
     */
    public Guest() {
    }
    /**
     */
    public boolean upDateFilter(String str)
    {
        return true;
    }
    /**
     */
    public void updateInfo()
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Yeni Bilgileri Giriniz ");
        System.out.println("Ad : ");
        String name = scan.next();

        System.out.println("Soyad : ");
        String surname = scan.next();

        System.out.println("Sifre : ");
        String password = scan.next();

        int index = -1;
        for (int i = 0; i < allUsers.size(); ++i){
            if (allUsers.get(i).getUsername().equals(this.getUsername())){
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
     */
    public void updateBasket()
    {

    }
    /**
     */
    @Override
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
