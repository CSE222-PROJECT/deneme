package Users;

import java.util.ArrayList;
import java.util.Objects;

public class Company extends User {
    //private BasketWallet.Wallet companyWallet=new BasketWallet.Wallet();

    public Company(String name, String surname, String username, String password, String email, int userType) {
        super(name, surname, username, password, email, userType);
    }

    public Company(){super();}
    /*public BasketWallet.Wallet getCompanyWallet() {
        return companyWallet;
    }

    public void setCompanyWallet(BasketWallet.Wallet companyWallet) {
        this.companyWallet = companyWallet;
    }

    public boolean addProduct(Products.ProductInterface pro){
        return true;
    }
    public boolean deleteProduct(Products.ProductInterface pro){
        return true;
    }
    public boolean stockProduct(Products.ProductInterface pro){
        return true;
    }*/
    public ArrayList<String> getFilter(){return  null;}

    @Override
    public void updateInfo() {

    }

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
