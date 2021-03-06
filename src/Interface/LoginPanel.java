/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Panels.OrganicsMapClass;
import Users.Company;
import Users.Guest;
import Users.SuperAdmin;
import Users.User;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static temp.lg.readUsers;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmet
 */
public class LoginPanel extends javax.swing.JFrame {

    
    private User currentUser;
    private boolean loginStatus = false;
    public static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<String> filters = new ArrayList<>();
    private static OrganicsMapClass<String,User> userMap = new OrganicsMapClass<>();
    
   
        
       


    /**
     * default constructor
     */
    public LoginPanel() {
        initComponents();
        readFilter();
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
    public static void writeUsers(){
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
   public boolean login(String username, String password){
        readUsers();
        readUserFilters();

        String name = "", surname = "", email = "";
        int userType = 0;

        
        boolean found = false;
      
        System.out.println(username+password);

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

        if(found)
        {
            if (userType == 1){
                currentUser = new SuperAdmin(name,surname,username,password,email,userType);
                SPanel s = new SPanel();
                s.setVisible(true);
                this.dispose();
            }else if(userType == 2){
                currentUser = new Company(name,surname,username,password,email,userType);
                CPanel c = new CPanel();
                c.setVisible(true);
                this.dispose();
            }else if(userType == 3){
                currentUser = new Guest(name,surname,username,password,email,userType);
                AllProducts a = new AllProducts();
                a.setVisible(true);
                this.dispose();
            
            }else
                return false;
        }
        

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
    public boolean register(String name,String surname,String username,String password,String email) {
        readUsers();
        readUserFilters();

        int userType = 3;
        ArrayList<String > chosenFilter ;

        System.out.println("Isim: ");

        System.out.println("Soyisim: ");

        readUsers();

        System.out.println("Kullanici Adi: ");

        for (int i = 0; i < allUsers.size(); i++) {
            if (username.equals(allUsers.get(i).getUsername())) {
                System.out.println("Username already exist!");
                return false;
            }
        }

        System.out.println("Sifre: ");

        System.out.println("Email: ");

        currentUser = new Guest(name,surname,username,password,email,userType); 
        System.out.println("Kayit Islemi Basarili!");
        loginStatus = true;
        writeUsers();
        
        /*-------*/
        chosenFilter = getFilterFromUser();
        currentUser.setFilter(chosenFilter);

        allUsers.add(currentUser); // kullanıcı listesine eklendi

        
        writeUserFilters();

       
        return true;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu1 = new java.awt.PopupMenu();
        jPanel1 = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        loginPassword = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        login_button = new javax.swing.JButton();
        header2 = new javax.swing.JLabel();
        loginUsername = new javax.swing.JTextField();
        registerPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        register_button = new javax.swing.JButton();
        header1 = new javax.swing.JLabel();
        r_username_field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        name_fiield = new javax.swing.JTextField();
        surname_field = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        email_field = new javax.swing.JTextField();
        r_password = new javax.swing.JTextField();
        bannerText = new javax.swing.JLabel();
        iconLabel = new javax.swing.JLabel();

        popupMenu1.setLabel("popupMenu1");
        popupMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupMenu1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1152, 648));
        jPanel1.setMinimumSize(new java.awt.Dimension(1152, 648));

        loginPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        loginPanel.setMaximumSize(new java.awt.Dimension(320, 430));
        loginPanel.setMinimumSize(new java.awt.Dimension(320, 430));
        loginPanel.setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Username");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Password");

        login_button.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        login_button.setText("Login");
        login_button.setToolTipText("");
        login_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_buttonActionPerformed(evt);
            }
        });

        header2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        header2.setText("Login");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(header2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(login_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(loginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header2)
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(loginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(13, 13, 13)
                .addComponent(loginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(login_button)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        registerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        registerPanel.setMaximumSize(new java.awt.Dimension(320, 430));
        registerPanel.setMinimumSize(new java.awt.Dimension(320, 430));
        registerPanel.setRequestFocusEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Password");

        register_button.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        register_button.setText("Register");
        register_button.setToolTipText("");
        register_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register_buttonActionPerformed(evt);
            }
        });

        header1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        header1.setText("Register");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Name");

        name_fiield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_fiieldActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Surname");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("E-mail");

        javax.swing.GroupLayout registerPanelLayout = new javax.swing.GroupLayout(registerPanel);
        registerPanel.setLayout(registerPanelLayout);
        registerPanelLayout.setHorizontalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerPanelLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(header1))
                    .addGroup(registerPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7)
                                .addComponent(jLabel4)
                                .addComponent(r_username_field)
                                .addComponent(jLabel3)
                                .addComponent(jLabel8)
                                .addComponent(name_fiield)
                                .addComponent(surname_field, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r_password))
                            .addComponent(jLabel9)
                            .addComponent(email_field))))
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(register_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        registerPanelLayout.setVerticalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1)
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name_fiield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(surname_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r_username_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(email_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(register_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bannerText.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        bannerText.setForeground(new java.awt.Color(0, 153, 0));
        bannerText.setText("ORGANICS");
        bannerText.setToolTipText("");

        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/organic-icon.png"))); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        iconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(registerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bannerText, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(349, 349, 349))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bannerText, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1152, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void login_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_buttonActionPerformed
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if(username.length() == 0 || password.length() == 0){
            JOptionPane.showMessageDialog(null, "HATALI GİRİŞ ", "UYARI", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            if( !login(username,password) ){
                JOptionPane.showMessageDialog(null, "UYARI ", "HATALI GİRİŞ", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_login_buttonActionPerformed

    private void register_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_register_buttonActionPerformed
        String name,surname, username, password, email;
        
        name = name_fiield.getText();
        surname=surname_field.getText();
        username=r_username_field.getText();
        password=r_password.getText();
        email= email_field.getText();
        
        if(name==null || surname==null || username==null || password==null || email==null){
            JOptionPane.showMessageDialog(null, "HATALI GİRİŞ ", "UYARI", JOptionPane.INFORMATION_MESSAGE);
        }else if(!register(name, surname, username, password, email)){
            JOptionPane.showMessageDialog(null, username+" Bu kullanıcı adı kayıtlıdır", "UYARI", JOptionPane.INFORMATION_MESSAGE);
        }else{
            AllProducts a = new AllProducts();
            // Kullanıcı burdan yollanacak 
            a.setVisible(true);
            this.dispose();
        }       
    }//GEN-LAST:event_register_buttonActionPerformed

    private void name_fiieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_fiieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_fiieldActionPerformed

    private void popupMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popupMenu1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bannerText;
    private javax.swing.JTextField email_field;
    private javax.swing.JLabel header1;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JTextField loginPassword;
    private javax.swing.JTextField loginUsername;
    private javax.swing.JButton login_button;
    private javax.swing.JTextField name_fiield;
    private java.awt.PopupMenu popupMenu1;
    private javax.swing.JTextField r_password;
    private javax.swing.JTextField r_username_field;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JButton register_button;
    private javax.swing.JTextField surname_field;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JPanel FilterPanel = registerPanel;

}
