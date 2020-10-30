/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class menuTransaksi extends javax.swing.JFrame {
    Connection sambungan;

    public menuTransaksi() {
        initComponents();
        sambung();
        Tanggal();
        bersih();
        cari_dataO();
        cari_dataK();
        tampilkan();
        
        
        //membuat tanggal sekarang tidak bisa diubah/edit. ~No pointer focus~
        TanggalSekarang.setEnabled(false);
        //untuk menghiddenkan variabel pembantu
        txtKDL.hide();
        txtNamaL.hide();
        txtHarga.hide();
        txtIDK.hide();
        txtKDO.hide();
    }
    
    private void Tanggal(){
        //menampilkan tanggan hari ini.
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
    
    private void sambung() {
        //konek ke database
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
    
    private void bersih(){
        //membersihkan komponen
        cbKDO.setSelectedIndex(0);
        cbIDK.setSelectedIndex(0);
        txtIDP.setText("");
        txtTO.setText("");
        txtKDT.setText("");
        txtTotal.setText("0");
        txtBayar.setText("0");
        txtKembali.setText("0");
        //variabel pembantu yang di-hidden-kan ikut dibersihkan
        txtKDL.setText("");
        txtNamaL.setText("");
        txtHarga.setText("");
        txtIDK.setText("");
        
    }
    
    private void cari_dataO(){
        //menampilkan data ke comboBox
        try{
            String sql ="select *from orderan";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                this.cbKDO.addItem(res.getString("kode_order"));
            }
            res.last();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void tampil_data(){
        //menampilkan data lain berdasarkan data yang dipilih pada comboBox
        try{
            String sql = "select kode_order, id_pelanggan, total_harga, tgl_order "
                    + "from orderan where kode_order='"+cbKDO.getSelectedItem()+"'";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                this.txtKDO.setText(res.getString("kode_order"));
                this.txtIDP.setText(res.getString("id_pelanggan"));
                this.txtTotal.setText(res.getString("total_harga"));
                this.txtTO.setText(res.getString("tgl_order"));
            }
            res.last();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void cari_dataK(){
        //menampilkan data ke comboBox
        try{
            String sql ="select *from karyawan";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                this.cbIDK.addItem(res.getString("id_karyawan"));
            }
            res.last();
            res.first();
            
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void tampil_dataK(){
        //menampilkan data lain berdasarkan data yang dipilih pada comboBox
        try{
            String sql = "select id_karyawan from karyawan where id_karyawan='"+cbKDO.getSelectedItem()+"'";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                this.txtIDK.setText(res.getString("id_karyawan"));
            }
            res.last();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void transaksi(){
        int  total, bayar, kembali;
        String kembalian;
        total = Integer.valueOf(txtTotal.getText());
        bayar =  Integer.valueOf(txtBayar.getText());
        
        kembali = bayar-total;
        
        kembalian = Integer.toString(kembali);
        txtKembali.setText(kembalian);
    }
    
    private void simpan(){
        String idP, kodeT, tglT, kodeO, idK, total, bayar, kembali;
        kodeT = txtKDT.getText();
        kodeO = txtKDO.getText();
        idK = (String) cbIDK.getSelectedItem();
//        idK = txtIDK.getText();
        idP = txtIDP.getText();
        tglT = TanggalSekarang.getText();
        total = txtTotal.getText();
        bayar = txtKDT.getText();
        kembali = txtTotal.getText();

        if (idP.equals("") 
                | (kodeO.equals("")) 
                | (idP.equals("")) 
                | (tglT.equals("")) 
                | (total.equals("")) 
                | (kembali.equals("")) 
                | (bayar.equals(""))){
            JOptionPane.showMessageDialog(null, "Data Harga Belum Muncul");
            txtTotal.requestFocus();
        }else {
            try {
                String sql = "Insert Into transaksi1 values(?,?,?,?,?,?,?,?)";
                PreparedStatement stat = sambungan.prepareStatement(sql);
                try {
                    stat.setString(1, kodeT);
                    stat.setString(2, kodeO);
                    stat.setString(3, idP);
                    stat.setString(4, idK);
                    stat.setString(5, tglT);
                    stat.setString(6, total);
                    stat.setString(7, bayar);
                    stat.setString(8, kembali);
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan..");
                    bersih();
                }catch(SQLException se){
                    System.out.println("Gagal Menyimpan.."+se);
                    JOptionPane.showMessageDialog(null, "Data baru gagal disimpan.. coba lagi.."
                        + "\n Pesan Error : \n"+se);
                    txtIDP.requestFocus();
                }
            }catch(Exception se){
                //System.out.println(se);
            }
        }
    }

    private void tampilkan(){
        String a = txtKDO.getText();

        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Kode_order");
        tbl.addColumn("Kode_layanan");
        tbl.addColumn("Nama_layanan");
        tbl.addColumn("Harga");
        tbl.addColumn("Tgl_Order");

        if(cbKDO.equals(a)){
            tbl.addRow(new Object[]{(""),(""),(""),(""),});
        }else{
            try {
                String sql1 = "Select *from order1 where kode_order like '%"+cbKDO.getSelectedItem()+"%'";
//                    String sql1 = "Select *from order1";
                Statement sttr = sambungan.createStatement();
                ResultSet res = sttr.executeQuery(sql1);
                while(res.next()){
                    tbl.addRow(new Object[]{
                        res.getString("kode_order"),
                        res.getString("kode_layanan"),
                        res.getString("nama_layanan"),
                        res.getString("harga"),
                        res.getString("tgl_pesan"),
                    });
                    
                tblTransaksi.setModel(tbl);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "DATA TABLE GAGAL.."+e,"\nERROR",JOptionPane.ERROR_MESSAGE);
            }
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
        m_Transaksi = new javax.swing.JLabel();
        keluar = new javax.swing.JLabel();
        m_pelanggan = new javax.swing.JLabel();
        m_Karyawan = new javax.swing.JLabel();
        TanggalSekarang = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIDP = new javax.swing.JTextField();
        cbKDO = new javax.swing.JComboBox();
        txtTO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        txtKDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        submit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbIDK = new javax.swing.JComboBox();
        txtHarga = new javax.swing.JTextField();
        txtIDK = new javax.swing.JTextField();
        txtNamaL = new javax.swing.JTextField();
        txtKDL = new javax.swing.JTextField();
        txtKDO = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        m_Laporan = new javax.swing.JLabel();
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

        m_Transaksi.setBackground(new java.awt.Color(153, 153, 153));
        m_Transaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Transaksi.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\Menu\\write-letter.png")); // NOI18N
        m_Transaksi.setToolTipText("Transaksi");
        m_Transaksi.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Transaksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_TransaksiMouseClicked(evt);
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
            .addComponent(m_Transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(m_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 600));
        jPanel1.add(TanggalSekarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 150, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID PELANGGAN");

        cbKDO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH --" }));
        cbKDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKDOActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TANGGAL ORDER");

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Order", "Kode_Layanan", "Nama_Layanan", "Harga", "Tgl_Order"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("KODE TRANSAKSI");

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TOTAL");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("BAYAR");

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("KEMBALI");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("KODE ORDER");

        submit.setText("SUBMIT");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID KARYAWAN");

        cbIDK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH --" }));
        cbIDK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIDKActionPerformed(evt);
            }
        });

        jButton1.setText("TAMPILKAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(469, 469, 469)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtKDT))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBayar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(72, 72, 72)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIDP)
                                    .addComponent(cbKDO, 0, 120, Short.MAX_VALUE)
                                    .addComponent(txtTO))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbIDK, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtKDO, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKDL, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaL, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbKDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbIDK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKDL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 850, 420));

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, java.awt.Color.darkGray));
        jPanel4.setToolTipText("");
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        m_Laporan.setBackground(new java.awt.Color(153, 153, 153));
        m_Laporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_Laporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/barber/laporan.png"))); // NOI18N
        m_Laporan.setToolTipText("Laporan");
        m_Laporan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_Laporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        m_Laporan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m_LaporanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(m_Laporan, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(m_Laporan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 150, 80, 100));

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\barber\\background cut3.jpg")); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, -120, 1070, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        new menuLayanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_LayananMouseClicked

    private void m_TransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_TransaksiMouseClicked
        // TODO add your handling code here:
        new menuTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_TransaksiMouseClicked

    private void m_UtamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_UtamaMouseClicked
        // TODO add your handling code here:
        new menuTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_UtamaMouseClicked

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        // TODO add your handling code here:
        transaksi();
    }//GEN-LAST:event_txtBayarActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_submitActionPerformed

    private void cbKDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKDOActionPerformed
        // TODO add your handling code here:
        tampil_data();
    }//GEN-LAST:event_cbKDOActionPerformed

    private void cbIDKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIDKActionPerformed
        // TODO add your handling code here:
        tampil_dataK();
    }//GEN-LAST:event_cbIDKActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tampilkan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void m_LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_LaporanMouseClicked
        // TODO add your handling code here:
        new laporanTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_LaporanMouseClicked

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
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TanggalSekarang;
    private javax.swing.JComboBox cbIDK;
    private javax.swing.JComboBox cbKDO;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel keluar;
    private javax.swing.JLabel m_Karyawan;
    private javax.swing.JLabel m_Laporan;
    private javax.swing.JLabel m_Layanan;
    private javax.swing.JLabel m_Transaksi;
    private javax.swing.JLabel m_Utama;
    private javax.swing.JLabel m_pelanggan;
    private javax.swing.JButton submit;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtIDK;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtKDL;
    private javax.swing.JTextField txtKDO;
    private javax.swing.JTextField txtKDT;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtNamaL;
    private javax.swing.JTextField txtTO;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
