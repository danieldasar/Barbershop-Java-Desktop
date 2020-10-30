/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.*;
import com.mysql.jdbc.Driver;
import java.awt.event.ActionEvent;

/**
 *
 * @author hp
 */
public class Login extends javax.swing.JFrame {

    Connection sambungan;
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        sambung();
    }

    public void sambung() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Sukses Driver JDBC ditemukan..");
            try {
                String url = "jdbc:mysql://localhost/barbershop?user=root&password=daniel";
                sambungan = DriverManager.getConnection(url);
                System.out.println("Sukses koneksi = ");
            }catch (SQLException se){
                System.out.println("GAGAL koneksi = "+ se);
                System.exit(0);
            }
        }catch (ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, "Class Tidak Ditemukan.. Error : "+cnfe);
            System.exit(0);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        tutup = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setLocation(new java.awt.Point(400, 230));
        setUndecorated(true);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("PASSWORD");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 50));

        jLabel3.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("ID");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 30, 50));

        jLabel6.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("DaSaR BarberShop");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, 50));

        jPasswordField1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jPanel1.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 190, 50));

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 190, 50));

        tutup.setBackground(new java.awt.Color(102, 102, 102));
        tutup.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Button\\close.png")); // NOI18N
        tutup.setToolTipText("CLOSE");
        tutup.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tutup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tutup.setOpaque(true);
        tutup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tutupMouseClicked(evt);
            }
        });
        jPanel1.add(tutup, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 70));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Button\\refresh.png")); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, -1, 70));

        jLabel4.setBackground(new java.awt.Color(102, 102, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Button\\login.png")); // NOI18N
        jLabel4.setToolTipText("LOGIN");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.setOpaque(true);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jLabel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel4KeyPressed(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, -1, 70));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 193, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\barber\\dark.jpg")); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 280));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 450, 280);

        setSize(new java.awt.Dimension(447, 277));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tutupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tutupMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_tutupMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
//        new menuUtama().setVisible(true);
//        this.dispose();
        
        if ((jTextField1.getText().equals("admin")&&
            //
            String.valueOf(jPasswordField1.getPassword()).equals("admin"))){
        new menuUtama().setVisible(true);
        dispose();
        }
        else if ((jTextField1.getText().equals("admin")&&
            //
            String.valueOf(jPasswordField1.getPassword()).equals("admin"))){
        new menuUtama().setVisible(true);
        dispose();
        }
        else{
            JOptionPane.showMessageDialog(
                null,
                jTextField1.getText()+
                "password anda salah",
                "Pesan kesalahan",
                JOptionPane.ERROR_MESSAGE);
            jTextField1.setText("");
            jPasswordField1.setText("");
            jTextField1.requestFocus();
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        jTextField1.setText("");
        jPasswordField1.setText("");
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel4KeyPressed
        // TODO add your handling code here:
//        if(evt.getKeyCode()==evt.VK_ENTER){
//            jLabel4ActionPeformed (new ActionEvent(evt.getSource(),evt.getID(),""));
//        }
    }//GEN-LAST:event_jLabel4KeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel tutup;
    // End of variables declaration//GEN-END:variables
}