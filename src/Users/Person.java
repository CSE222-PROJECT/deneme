package Users;

import java.util.ArrayList;

public interface Person {
    /**
     *
     * @return name
     */
    public String getName();

    /**
     *
     * @return surname
     */
    public String getSurname();

    /**
     *
     * @return username
     */
    public String getUsername();

    /**
     *
     * @return password
     */
    public String getPassword();

    /**
     *
     * @return email
     */
    public String getEmail();

    /**
     *
     * @return filter
     */
    public ArrayList<String> getFilter();

    /**
     * update user info
     */
    public void updateInfo();

    /**
     *
     * @param name of user
     */
    public void setName(String name);

    /**
     *
     * @param surname of user
     */
    public void setSurname(String surname);

    /**
     *
     * @param username of user
     */
    public void setUsername(String username);

    /**
     *
     * @param password of user
     */
    public void setPassword(String password);

    /**
     *
     * @param email of user
     */
    public void setEmail(String email) ;


}
