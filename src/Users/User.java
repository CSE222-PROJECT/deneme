package Users;

import java.util.ArrayList;

public abstract class User implements Person {

    /**
     * filter of person
     */
    private ArrayList<String> filter = new ArrayList<>();
    /**
     * name of person
     */
    private String name;
    /**
     * surname of person
     */
    private String surname;
    /**
     * username of person
     */
    private String username;
    /**
     * password  of person
     */
    private String password;
    /**
     * email  of person
     */
    private String email;
    /**
     * user type  of person
     */
    private int userType; // 1-super admin 2-company 3-costumer

    /**
     * no parameter constructor
     */
    public User(){}

    /**
     * Constructor with infos
     * @param name of user
     * @param surname of user
     * @param username of user
     * @param password of user
     * @param email of user
     * @param userType of user
     */
    public User(String name, String surname, String username, String password, String email,int userType) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    /**
     * setter
     * @param filter filter
     */
    public void setFilter(ArrayList<String> filter) {
        this.filter.addAll(filter);
    }

    /**
     * getter
     * @return filter
     */
    public ArrayList<String> getFilter() {
        return this.filter ;
    }
    /**
     * getter
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    /**
     * getter
     * @return surname
     */
    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * getter
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * getter
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * getter
     * @return email
     */
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * getter
     * @return user type
     */
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public abstract boolean equals(Object o);

    /**
     * to string method
     * @return string
     */
    @Override
    public String toString() {
        return "Users.User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
