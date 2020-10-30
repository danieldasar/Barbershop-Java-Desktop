/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import javax.swing.table.DefaultTableModel;
import KONEKSI.koneksi;


/**
 *
 * @author hp
 */
public class laporanOrder extends javax.swing.JFrame {
    Connection sambungan;
    public Statement sttr;

    public laporanOrder() {
        initComponents();
        sambung();
        dataTable();
        Tanggal();
        
    }
    
    private void sambung() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Sukses Driver JDBC ditemukan..");
            try {
                String url = "jdbc:mysql://localhost/barbershop?user=root&password=daniel";
                sambungan = DriverManager.getConnection(url);
                System.out.println("Sukses koneksi ");
            }catch (SQLException se){
                System.out.println("GAGAL koneksi = "+ se);
                System.exit(0);
            }
        }catch (ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, "Class Tidak Ditemukan.. Error : "+cnfe);
            System.exit(0);
        }
    }
    
    private void Tanggal(){
        this.setLocationRelativeTo(null);
        new Thread(){
            public void run(){
            while (true) {
                Calendar kal = new GregorianCalendar();
                int tahun = kal.get(Calendar.YEAR);
                int bulan = kal.get(Calendar.MONTH)+1;
                int hari = kal.get(Calendar.DAY_OF_MONTH);
                String tanggal = tahun+"-"+bulan+"-"+hari;
                TanggalSekarang.setText(tanggal);
            }
        }
        }.start();
    }

    private void dataTable(){
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Kode_order");
        tbl.addColumn("Tgl_Order");
        tbl.addColumn("ID_Pelanggan");
        tbl.addColumn("Kode_layanan");
        tbl.addColumn("Nama_layanan");
        tbl.addColumn("Harga");
        try {
            String sql1 = "Select *from order1";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql1);
            while(res.next()){
                tbl.addRow(new Object[]{
                        res.getString("kode_order"),
                        res.getString("tgl_pesan"),
                        res.getString("id_pelanggan"),
                        res.getString("kode_layanan"),
                        res.getString("nama_layanan"),
                        res.getString("harga"),
                });
                tblOrder.setModel(tbl);
            }
        }catch(Exception e){
            //System.out.println("ERROR");
            JOptionPane.showMessageDialog(null, "ERROR.. "+e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        m_Utama = new javax.swing.JLabel();
        m_Layanan = new javax.swing.JLabel();
        m_Laporan = new javax.swing.JLabel();
        keluar = new javax.swing.JLabel();
        m_pelanggan = new javax.swing.JLabel();
        m_Karyawan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        view = new javax.swing.JButton();
        print = new javax.swing.JButton();
        cari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        TanggalSekarang = new javax.swing.JTextField();
        BackTransaksi = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Stencil", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TRANSAKSI");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 490, 90));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        m_Utama.setBackground(new java.awt.Color(153, 153, 153));
        m_Utama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Utama.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\salon.png")); // NOI18N
        m_Utama.setToolTipText("MENU");
        m_Utama.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Utama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Utama.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Utama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_UtamaMouseClicked(evt);
            }
        });

        m_Layanan.setBackground(new java.awt.Color(153, 153, 153));
        m_Layanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Layanan.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\customer.png")); // NOI18N
        m_Layanan.setToolTipText("Daftar Layanan");
        m_Layanan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Layanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Layanan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Layanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_LayananMouseClicked(evt);
            }
        });

        m_Laporan.setBackground(new java.awt.Color(153, 153, 153));
        m_Laporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Laporan.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\write-letter.png")); // NOI18N
        m_Laporan.setToolTipText("Laporan");
        m_Laporan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Laporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Laporan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_LaporanMouseClicked(evt);
            }
        });

        keluar.setBackground(new java.awt.Color(153, 153, 153));
        keluar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        keluar.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\power.png")); // NOI18N
        keluar.setToolTipText("Keluar");
        keluar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        keluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keluar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarMouseClicked(evt);
            }
        });

        m_pelanggan.setBackground(new java.awt.Color(153, 153, 153));
        m_pelanggan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_pelanggan.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\barber.png")); // NOI18N
        m_pelanggan.setToolTipText("Let's Work !..");
        m_pelanggan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_pelanggan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_pelanggan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_pelangganMouseClicked(evt);
            }
        });

        m_Karyawan.setBackground(new java.awt.Color(153, 153, 153));
        m_Karyawan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Karyawan.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\workers.png")); // NOI18N
        m_Karyawan.setToolTipText("Karyawan");
        m_Karyawan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Karyawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Karyawan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_KaryawanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(m_Utama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(m_Layanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(m_Laporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_Karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(m_Utama, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_pelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_Karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_Layanan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 600));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        view.setText("VIEW");
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });

        print.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        print.setText("PRINT");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        cari.setText("CARI");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode_order", "Tgl_Order", "ID_Pelanggan", "Kode_layanan", "Nama_layanan", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrder);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCari)
                    .addComponent(cari, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(view, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 890, 380));
        jPanel1.add(TanggalSekarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 100, 150, -1));

        BackTransaksi.setBackground(new java.awt.Color(153, 153, 153));
        BackTransaksi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray, java.awt.Color.gray));
        BackTransaksi.setToolTipText("Kembali ke Menu Transaksi");
        BackTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BackTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackTransaksiMouseClicked(evt);
            }
        });
        BackTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("BACK");
        BackTransaksi.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(BackTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 90, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\barber\\background cut3.jpg")); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, -120, 1070, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_keluarMouseClicked

    private void m_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_pelangganMouseClicked
        // TODO add your handling code here:
        new menuPelanggan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_pelangganMouseClicked

    private void m_KaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_KaryawanMouseClicked
        // TODO add your handling code here:
        new menuKaryawan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_KaryawanMouseClicked

    private void m_LayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_LayananMouseClicked
        // TODO add your handling code here:
        new laporanOrder().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_LayananMouseClicked

    private void m_LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_LaporanMouseClicked
        // TODO add your handling code here:
        new menuTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_LaporanMouseClicked

    private void m_UtamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_UtamaMouseClicked
        // TODO add your handling code here:
        new laporanOrder().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_UtamaMouseClicked

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        // untuk menampilkan data tabel.
        dataTable();
    }//GEN-LAST:event_viewActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        // TODO add your handling code here:
        int tanya = JOptionPane.showConfirmDialog(null, "Ingin mencetak laporan ? ", 
                "tanya",JOptionPane.YES_NO_CANCEL_OPTION);
        if(tanya==0){
            new cetak_Order().setVisible(true);
            this.dispose();
        }else if(tanya==1){
            new menuOrder().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_printActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        //untuk mencari data berdasarkan nilai tertentu seperti kode layanan, nama layanan maupun harga.
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Kode_order");
        tbl.addColumn("Tgl_Order");
        tbl.addColumn("ID_Pelanggan");
        tbl.addColumn("Kode_layanan");
        tbl.addColumn("Nama_layanan");
        tbl.addColumn("Harga");
            try {
                String sql1 = "Select *from order1 where kode_order like '%"+txtCari.getText()+"%'";
                Statement sttr = sambungan.createStatement();
                ResultSet res = sttr.executeQuery(sql1);
                while(res.next()){
                    tbl.addRow(new Object[]{
                        res.getString("kode_order"),
                        res.getString("tgl_pesan"),
                        res.getString("id_pelanggan"),
                        res.getString("kode_layanan"),
                        res.getString("nama_layanan"),
                        res.getString("harga"),
                    });
                tblOrder.setModel(tbl);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(rootPane, "DATA TABLE GAGAL..");
            }
    }//GEN-LAST:event_cariActionPerformed

    private void BackTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackTransaksiMouseClicked
        // TODO add your handling code here:
        new menuTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackTransaksiMouseClicked

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
        // TODO add your handling code here:
//        tabel();
    }//GEN-LAST:event_tblOrderMouseClicked

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
            java.util.logging.Logger.getLogger(laporanOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporanOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporanOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporanOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporanOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackTransaksi;
    private javax.swing.JTextField TanggalSekarang;
    private javax.swing.JButton cari;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel keluar;
    private javax.swing.JLabel m_Karyawan;
    private javax.swing.JLabel m_Laporan;
    private javax.swing.JLabel m_Layanan;
    private javax.swing.JLabel m_Utama;
    private javax.swing.JLabel m_pelanggan;
    private javax.swing.JButton print;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtCari;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}