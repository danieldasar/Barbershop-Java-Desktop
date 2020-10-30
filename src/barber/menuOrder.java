/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class menuOrder extends javax.swing.JFrame {
    Connection sambungan;

    public menuOrder() {
        initComponents();
        sambung();
        cari_dataP();
        cari_dataL();
        tanggal();
//        dataTable();
        Tampilkan();
        
        //variabel pembantu untuk mengambil nilai id pelanggan dan kodeLayanan tidak ditampilkan dilayar.
        txtIDP.hide();
        txtKodeL.hide();
    }
    
    private void tanggal(){
        //menampilkan tanggal hari ini
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
        TanggalSekarang.setEnabled(false);
        
//        String tanggal = TanggalSekarang.getText();
//        txtTO.setText(tanggal);
    }
    
    public void sambung() {
        //method untuk mengkonek kan ke database sql
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
    
    public void bersih(){
        //untuk membersihkan komponen yang ada dilayar.
        cbPelanggan.setSelectedIndex(0);
        cbLayanan.setSelectedIndex(0);
        txtNamaP.setText("");
        txtKDO.setText("");
        txtKodeL.setText("");
        txtIDP.setText("");
        txtNamaL.setText("");
        txtKodeL.setText("");
        txtHarga.setText("");
    }
    
    private void cari_dataP(){
        //menampilkan data ke comboBox
        try{
            String sql ="select * from pelanggan";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                
                this.cbPelanggan.addItem(res.getString("id_pelanggan"));
            }
            res.last();
//            int jumlahdata = res.getRow();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }    
    }
    
    private void tampil_dataP(){
        //menampilkan data lain berdasarkan data yang dipilih pada comboBox
        try{
            String sql = "select id_pelanggan, nama_pelanggan from pelanggan "
                    + "where id_pelanggan='"+cbPelanggan.getSelectedItem()+"'";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                
                this.txtIDP.setText(res.getString("id_pelanggan"));
                this.txtNamaP.setText(res.getString("nama_pelanggan"));
                
            }
            res.last();
//            int jumlahdata = res.getRow();
            res.first();
         
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void cari_dataL(){
        try{
            String sql ="select * from layanan";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
                this.cbLayanan.addItem(res.getString("kodeLayanan"));
            }
            res.last();
//            int jumlahdata = res.getRow();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void tampil_dataL(){
        try{
            String sql = "select kodeLayanan, nama_layanan, harga from layanan "
                    + "where kodeLayanan='"+cbLayanan.getSelectedItem()+"'";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql);
            while (res.next()){
//                this.cbLayanan.addItem(res.getString("nama_layanan"));
                this.txtKodeL.setText(res.getString("kodeLayanan"));
                this.txtNamaL.setText(res.getString("nama_layanan"));
                this.txtHarga.setText(res.getString("harga"));
            }
            res.last();
//            int jumlahdata = res.getRow();
            res.first();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }
    
    private void cari_total(){
        //menjumlahkan isi colom ke 4 dalam tabel
        int total = 0;
        
//        if(tblOrder.getValueAt(i, 5).equils("")){
//            txtTotal.setText("");
//        }else{
            
        for (int i =0; i< tblOrder.getRowCount(); i++){
            int amount = Integer.parseInt((String)tblOrder.getValueAt(i, 5));
            total += amount;
        }
        txtTotal.setText(""+total);
//        }
    }
    
    private void Tampilkan(){
        //datatable hanya akan ditampilan berdasarkan kode Order yang dipilih
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Kode_order");
        tbl.addColumn("Tgl_Order");
        tbl.addColumn("ID_Pelanggan");
        tbl.addColumn("Kode_layanan");
        tbl.addColumn("Nama_layanan");
        tbl.addColumn("Harga");
        
        if(txtKDO.equals("")){
            tbl.addRow(new Object[]{(""),(""),(""),(""),(""),(""),});
        }else{
            try {
                String sql1 = "Select *from order1 where kode_order like '%"+txtKDO.getText()+"%'";
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
        }
        cari_total();
    }
    
//    private void dataTable(){
//        DefaultTableModel tbl = new DefaultTableModel();
//        tbl.addColumn("Kode_order");
//        tbl.addColumn("Tgl_Order");
//        tbl.addColumn("ID_Pelanggan");
//        tbl.addColumn("Kode_layanan");
//        tbl.addColumn("Nama_layanan");
//        tbl.addColumn("Harga");
//        //jTable1.setModel(tbl);
//        try {
//            String sql1 = "Select *from order1";
//            Statement sttr = sambungan.createStatement();
//            ResultSet res = sttr.executeQuery(sql1);
//            while (res.next()){
//                tbl.addRow(new Object []{
//                    res.getString("kode_order"),
//                    res.getString("tgl_pesan"),
//                    res.getString("id_pelanggan"),
//                    res.getString("kode_layanan"),
//                    res.getString("nama_layanan"),
//                    res.getString("harga"),
//                });
//                tblOrder.setModel(tbl);
//            }
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(rootPane, "DATA TABLE GAGAL..");
//        }
//    }
    
    private void save(){
        String idP, namaP, kodeL, namaL, harga, tglO, kodeO;
        idP = txtIDP.getText();
        namaP = txtNamaP.getText();
        kodeL = txtKodeL.getText();
        namaL = txtNamaL.getText();
        harga = txtHarga.getText();
        kodeO = txtKDO.getText();
        tglO = TanggalSekarang.getText();
        
        if (idP.equals("") | (kodeL.equals("")) 
                | (kodeO.equals("")) 
                | (namaL.equals("")) 
                | (harga.equals("")) 
                | (tglO.equals(""))){
            JOptionPane.showMessageDialog(null, "Pengisisan Tak Boleh Kosong..");
            txtNamaP.requestFocus();
        }else {
            try {
                String sql = "Insert Into order1 values(?,?,?,?,?,?)";
                PreparedStatement stat = sambungan.prepareStatement(sql);
                try {
                    stat.setString(1, kodeO);
                    stat.setString(2, idP);
                    stat.setString(3, kodeL);
                    stat.setString(4, namaL);
                    stat.setString(5, harga);
                    stat.setString(6, tglO);
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan..");
                    cbLayanan.setSelectedIndex(0);
                    txtNamaL.setText("");
                    txtKodeL.setText("");
                    txtHarga.setText("");
                }catch(SQLException se){
                    System.out.println("Gagal Menyimpan.."+se);
                    JOptionPane.showMessageDialog(null, "Data yang anda masukkan ttelah ada.."
                        + "\nData baru gagal disimpan.. coba lagi.."
                        + "\n Pesan Error : \n"+se);
                    txtNamaP.requestFocus();
                }
            }catch(Exception se){
                //System.out.println(se);
            }
        }
    }
    
    private void delete(){
        String kode;
        kode = txtKDO.getText();
        int tanya = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus ? ", 
                "Tanya", JOptionPane.YES_NO_CANCEL_OPTION);
        if (tanya==0){
            try{
                String sql1 = "delete FROM order1 WHERE kode_order ='"+kode+"'";
                Statement sttr = sambungan.createStatement();
                sttr.execute(sql1);
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus..");
                txtKDO.setText("");
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Proses Menghapus GAGAL","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void tabel(){
        int tbl = tblOrder.getSelectedRow();
        //DefaultTableModel model = tabel.getModel();
                
        String a = tblOrder.getValueAt(tbl, 0).toString();
//        String b = tblOrder.getValueAt(tbl, 1).toString();
//        String c = tblOrder.getValueAt(tbl, 2).toString();
//        String d = tblOrder.getValueAt(tbl, 3).toString();
//        String e = tblOrder.getValueAt(tbl, 4).toString();
//        String f = tblOrder.getValueAt(tbl, 5).toString();
        
//        txtIDP.setText(c);
//        txtNamaP.setText(d);
        txtKDO.setText(a);
//        txtKodeL.setText(d);
//        txtNamaL.setText(e);
        
//        txtIDP.setEnabled(false);
        txtKDO.requestFocus();
    }
    
    private void simpan(){
        String idP, harga, tglO, kodeO;
        kodeO = txtKDO.getText();
        idP = txtIDP.getText();
        tglO = TanggalSekarang.getText();
        harga = txtTotal.getText();

        if (idP.equals("") | (kodeO.equals("")) | (harga.equals("")) | (tglO.equals(""))){
            JOptionPane.showMessageDialog(null, "Data Harga Belum Muncul");
            txtTotal.requestFocus();
        }else {
            try {
                String sql = "Insert Into orderan values(?,?,?,?)";
                PreparedStatement stat = sambungan.prepareStatement(sql);
                try {
                    stat.setString(1, kodeO);
                    stat.setString(2, tglO);
                    stat.setString(3, idP);
                    stat.setString(4, harga);
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan..");
                }catch(SQLException se){
                    System.out.println("Gagal Menyimpan.."+se);
                    JOptionPane.showMessageDialog(null, "Data baru gagal disimpan.. coba lagi.."
                        + "\n Pesan Error : \n"+se);
                    txtKDO.requestFocus();
                }
            }catch(Exception se){
                //System.out.println(se);
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
        jPanel5 = new javax.swing.JPanel();
        m_Laporan = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbPelanggan = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cbLayanan = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        add = new javax.swing.JButton();
        clean = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtKDO = new javax.swing.JTextField();
        txtNamaL = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        tampil = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtNamaP = new javax.swing.JTextField();
        simpan = new javax.swing.JButton();
        TanggalSekarang = new javax.swing.JTextField();
        txtIDP = new javax.swing.JTextField();
        txtKodeL = new javax.swing.JTextField();
        BackTransaksi1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, java.awt.Color.darkGray));
        jPanel5.setToolTipText("");
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(m_Laporan, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(m_Laporan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, 80, 100));

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Stencil", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Order");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 300, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID PELANGGAN");

        cbPelanggan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH --" }));
        cbPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbPelangganMouseClicked(evt);
            }
        });
        cbPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPelangganActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NAMA PELANGGAN");

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

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("HARGA");

        cbLayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- PILIH --" }));
        cbLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLayananActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("KODE LAYANAN");

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        clean.setText("CLEAN");
        clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("KODE ORDER");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("NAMA LAYANAN");

        txtTotal.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        tampil.setText("TAMPILKAN");
        tampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TOTAL");

        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNamaL, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKDO, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNamaP))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(149, 149, 149)
                                    .addComponent(jLabel9))
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(simpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clean, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clean, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tampil, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 770, 380));
        jPanel1.add(TanggalSekarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 140, -1));
        jPanel1.add(txtIDP, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 490, 135, -1));
        jPanel1.add(txtKodeL, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, 135, -1));

        BackTransaksi1.setBackground(new java.awt.Color(153, 153, 153));
        BackTransaksi1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray, java.awt.Color.gray));
        BackTransaksi1.setToolTipText("Kembali ke Menu Transaksi");
        BackTransaksi1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BackTransaksi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackTransaksi1MouseClicked(evt);
            }
        });
        BackTransaksi1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setText("BACK");
        BackTransaksi1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(BackTransaksi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 90, 50));

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\barber\\background cut3.jpg")); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -120, 1090, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        delete();
//        dataTable();
        Tampilkan();
    }//GEN-LAST:event_deleteActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // TODO add your handling code here:
        save();
//        dataTable();
        Tampilkan();
    }//GEN-LAST:event_addActionPerformed

    private void cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanActionPerformed
        // TODO add your handling code here:
        bersih();
//        dataTable();
        Tampilkan();
    }//GEN-LAST:event_cleanActionPerformed

    private void cbPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPelangganActionPerformed
        // TODO add your handling code here:
        tampil_dataP();
    }//GEN-LAST:event_cbPelangganActionPerformed

    private void cbPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbPelangganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPelangganMouseClicked

    private void cbLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLayananActionPerformed
        // TODO add your handling code here:
        tampil_dataL();
    }//GEN-LAST:event_cbLayananActionPerformed

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
        // TODO add your handling code here:
        tabel();
    }//GEN-LAST:event_tblOrderMouseClicked

    private void tampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilActionPerformed
        // TODO add your handling code here:
        Tampilkan();
    }//GEN-LAST:event_tampilActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        // TODO add your handling code here:
        simpan();
    }//GEN-LAST:event_simpanActionPerformed

    private void m_LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_LaporanMouseClicked
        // TODO add your handling code here:
        new laporanOrder().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_LaporanMouseClicked

    private void BackTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackTransaksiMouseClicked
        // TODO add your handling code here:
        new menuTransaksi().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackTransaksiMouseClicked

    private void BackTransaksi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackTransaksi1MouseClicked
        // TODO add your handling code here:
        new menuPelanggan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackTransaksi1MouseClicked

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
            java.util.logging.Logger.getLogger(menuOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackTransaksi;
    private javax.swing.JPanel BackTransaksi1;
    private javax.swing.JTextField TanggalSekarang;
    private javax.swing.JButton add;
    private javax.swing.JComboBox cbLayanan;
    private javax.swing.JComboBox cbPelanggan;
    private javax.swing.JButton clean;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel m_Laporan;
    private javax.swing.JButton simpan;
    private javax.swing.JButton tampil;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtKDO;
    private javax.swing.JTextField txtKodeL;
    private javax.swing.JTextField txtNamaL;
    private javax.swing.JTextField txtNamaP;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
