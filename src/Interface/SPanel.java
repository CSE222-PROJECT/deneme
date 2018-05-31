/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import BasketWallet.Basket;
import static Panels.SuperAdminPanel.confirms;
import static Panels.SuperAdminPanel.orderList;
import static Panels.SuperAdminPanel.orderQueue;
import static Panels.SuperAdminPanel.readConfirm;
import static Panels.SuperAdminPanel.readOrder;
import static Panels.SuperAdminPanel.writeAgainCsv;
import static Panels.SuperAdminPanel.writeOrderQueue;
import Products.Product;
import Products.QueueList;
import Users.SuperAdmin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author ahmet
 */
public class SPanel extends javax.swing.JFrame {
    
    public static  QueueList<String > confirms = new QueueList<>();
    public static  QueueList<Basket> orderQueue = new QueueList<>();
    public static ArrayList<Basket> orderList= new ArrayList<>();
    private String USERSFILE = "users.csv";
    
    
    /**
     * Creates new form SPanel
     */
    public SPanel() {
        try {
            readConfirm();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        
        DefaultListModel temp=new DefaultListModel();
        for (int i = 0; i < confirms.size(); ++i){
            temp.addElement(confirms.get(i));
        }
        jList1.setModel(temp);
    }
    
    public static void readConfirm() throws IOException {

        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(
                    new FileReader("confirmBasket.csv"));
        
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        String line2 = null;
        for (int i = 0; (line2 = reader2.readLine()) != null; ++i)
            confirms.add(line2);     
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
    
    public void updateList(){
        
        
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        bannerText = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        exitButon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        confimButon = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(1152, 648));
        jPanel1.setMinimumSize(new java.awt.Dimension(1152, 648));
        jPanel1.setName(""); // NOI18N

        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/organic-icon.png"))); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        iconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        bannerText.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        bannerText.setForeground(new java.awt.Color(0, 153, 0));
        bannerText.setText("ORGANICS");
        bannerText.setToolTipText("");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Admin Paneli ");

        exitButon.setText("Çıkış");
        exitButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButonActionPerformed(evt);
            }
        });

        jLabel3.setText("Onaylanmayı Bekleyen Siparişler");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(37, 37, 37))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        confimButon.setText("İlk Siparişi Onayla");
        confimButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confimButonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Hoşgeldiniz");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(exitButon)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(confimButon)
                        .addGap(119, 119, 119))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confimButon)
                .addGap(26, 26, 26)
                .addComponent(exitButon)
                .addGap(32, 32, 32))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("YÖNETİCİ PANELİ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bannerText, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(jLabel2)))
                .addContainerGap(361, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bannerText, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
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

    private void exitButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButonActionPerformed
        Home h = new Home();
        h.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_exitButonActionPerformed

    private void confimButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confimButonActionPerformed
        
    }//GEN-LAST:event_confimButonActionPerformed

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
            java.util.logging.Logger.getLogger(SPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bannerText;
    private javax.swing.JButton confimButon;
    private javax.swing.JButton exitButon;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
