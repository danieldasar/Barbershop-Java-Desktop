/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barber;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class menuPelanggan extends javax.swing.JFrame {

    Connection sambungan;
    String idP, namaP, noHP, jenkel, alamat;

    public menuPelanggan() {
        initComponents();
        sambung();
        dataTable();
        bersih();
        
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

    private void dataTable(){
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID pelanggan");
        tbl.addColumn("Nama");
        tbl.addColumn("Jenis Kelamin");
        tbl.addColumn("No. HP");
        tbl.addColumn("Alamat");
        //jTable1.setModel(tbl);
        try {
            String sql1 = "Select *from pelanggan";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql1);
            while (res.next()){
                tbl.addRow(new Object []{
                    res.getString("id_pelanggan"),
                    res.getString("nama_pelanggan"),
                    res.getString("gender"),
                    res.getString("phone"),
                    res.getString("alamat"),
                });
                tabel.setModel(tbl);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, "DATA TABLE GAGAL..");
        }
    }
    
    private void bersih (){
        txtIDP.setText("");
        txtNama.setText("");
        txtHP.setText("");
        txtAlamat.setText("");
        gender1.setSelected(true);
        tabel.clearSelection();
        txtIDP.setEnabled(true);
    }
    
    private void save(){
        idP = txtIDP.getText();
        namaP = txtNama.getText();
        if (gender1.isSelected()){
            jenkel = "L";
        }else{
            jenkel = "P";
        }
        noHP = txtHP.getText();
        alamat = txtAlamat.getText();

        if (idP.equals("") | (namaP.equals("")) | (noHP.equals("")) | (alamat.equals(""))){
            JOptionPane.showMessageDialog(null, "Pengisisan Tak Boleh Kosong..");
            txtIDP.requestFocus();
        }else {
            try {
                String sql = "Insert Into pelanggan values(?,?,?,?,?)";
                PreparedStatement stat = sambungan.prepareStatement(sql);
                try {
                    stat.setString(1, idP);
                    stat.setString(2, namaP);
                    stat.setString(3, jenkel);
                    stat.setString(4, noHP);
                    stat.setString(5, alamat);
                    stat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan..");
                    bersih();
                }catch(SQLException se){
                    System.out.println("Gagal Menyimpan.."+se);
                    JOptionPane.showMessageDialog(null, "Data yang anda masukkan ttelah ada.."
                        + "\nData baru gagal disimpan.. coba lagi.."
                        + "\n Pesan Error : \n"+se);
                    txtIDP.requestFocus();
                }
            }catch(Exception se){
                //System.out.println(se);
            }
        }
    }
    
    private void edit(){
        idP = txtIDP.getText();
        namaP = txtNama.getText();
        if (gender1.isSelected()){
            jenkel = "L";
        }else{
            jenkel = "P";
        }
        noHP = txtHP.getText();
        alamat = txtAlamat.getText();
        int tanya = JOptionPane.showConfirmDialog(null, "Yakin Ingin Mengedit Data ? ",
                "Tanya", JOptionPane.YES_NO_CANCEL_OPTION);
        if (tanya==0){
            try{
                //sambung();
                String sql1 = "Update pelanggan set nama_pelanggan='"+namaP+"',"
                +"gender='"+jenkel+"',"+"phone='"+noHP+"',"+"alamat='"+alamat+"' "+"WHERE id_pelanggan='"+idP+"'";
                Statement sttr = sambungan.createStatement();
                //ResultSet res = sttr.executeQuery(sql1);
                sttr.execute(sql1);
                //sambungan.close();
                //dataTable();
                bersih();
                JOptionPane.showMessageDialog(this, "Data Berhasil Diubah..", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                //save.setEnabled(false);
            }catch(Exception se){
                JOptionPane.showMessageDialog(this, "Proses Menyimpan GAGAL","ERROR",JOptionPane.ERROR_MESSAGE);
                //System.out.println(e.getMessage());
            }
        }
    }
    
    private void delete(){
        String kode;
        kode = txtIDP.getText();
        int tanya = JOptionPane.showConfirmDialog(null, "Yakin Ingin Menghapus ? ", "Tanya", JOptionPane.YES_NO_CANCEL_OPTION);
        if (tanya==0){
            try{
                String sql1 = "delete FROM pelanggan WHERE id_pelanggan ='"+kode+"'";
                Statement sttr = sambungan.createStatement();
                sttr.execute(sql1);
                bersih();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Proses Menghapus GAGAL","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cari(){
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID Pelanggan");
        tbl.addColumn("Nama");
        tbl.addColumn("Jenis Kelamin");
        tbl.addColumn("No. HP");
        tbl.addColumn("Alamat");
        try {
            
            String gender;
            if(gender1.isSelected()){
                gender = "L";
            }else{
                gender = "P";
            }
                    
            String sql1 = "Select *from pelanggan where id_pelanggan like '%"+txtCari.getText()
            +"%' or nama_pelanggan like '%"+txtCari.getText()
            +"%' or gender like '%"+ txtCari.getText()
            +"%' or phone like '%"+ txtCari.getText()
            +"%' or alamat like '%"+txtCari.getText()+"%'";
            Statement sttr = sambungan.createStatement();
            ResultSet res = sttr.executeQuery(sql1);
            while(res.next()){
                tbl.addRow(new Object[]{
                    res.getString("id_pelanggan"),
                    res.getString("nama_pelanggan"),
                    res.getString("gender"),
                    res.getString("phone"),
                    res.getString("alamat")});
            tabel.setModel(tbl);
            
        }
        }catch(Exception e){
            //System.out.println("ERROR");
            JOptionPane.showMessageDialog(null, "ERROR.. "+e);
        }
    }
    
    public void orderan(){
        int tbl = tabel.getSelectedRow();
        if (tabel.isRowSelected(tbl)){
            new menuOrder().setVisible(true);
        }
    }
    
    private void tabel(){
        int tbl = tabel.getSelectedRow();
                
        String a = tabel.getValueAt(tbl, 0).toString();
        String b = tabel.getValueAt(tbl, 1).toString();
        String c = tabel.getValueAt(tbl, 2).toString();
        String d = tabel.getValueAt(tbl, 3).toString();
        String e = tabel.getValueAt(tbl, 4).toString();
        
        txtIDP.setText(a);
        txtNama.setText(b);
        if (c.equals("L")){
            gender1.setSelected(true);
        }else {
            gender2.setSelected(true);
        }
        txtHP.setText(d);
        txtAlamat.setText(e);
        
        txtIDP.setEnabled(false);
        txtNama.requestFocus();
    }
    
    private void cetak(){
        try {
            boolean complete = tabel.print();
            if (complete){
                JOptionPane.showConfirmDialog(null, "SUKSES..");
            }else{
                JOptionPane.showConfirmDialog(null, "ERROR..");
            }
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        txtNama = new javax.swing.JTextField();
        gender1 = new javax.swing.JRadioButton();
        gender2 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtHP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        txtIDP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        cari = new javax.swing.JButton();
        save = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        view = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        print = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        clean = new javax.swing.JButton();
        order = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1080, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Semangat Kerjanya Gaeiz...");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 560, 50));

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
        jPanel1.add(TanggalSekarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 80, 150, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NAMA");

        buttonGroup1.add(gender1);
        gender1.setForeground(new java.awt.Color(255, 255, 255));
        gender1.setText("LAKI-LAKI");

        buttonGroup1.add(gender2);
        gender2.setForeground(new java.awt.Color(255, 255, 255));
        gender2.setText("PEREMPUAN");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("JENIS KELAMIN");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NOMOR HP");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ALAMAT");

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Pelanggan", "Nama", "Jenis Kelamin", "No. HP", "Alamat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID PELANGGAN");

        cari.setText("CARI");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        save.setText("SAVE");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        edit.setText("EDIT");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        view.setText("VIEW");
        view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewActionPerformed(evt);
            }
        });

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        print.setText("PRINT");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        clean.setText("CLEAN");
        clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanActionPerformed(evt);
            }
        });

        order.setText("ORDER");
        order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(gender1)
                                        .addGap(10, 10, 10)
                                        .addComponent(gender2))
                                    .addComponent(txtIDP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clean, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(print, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(order, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(72, 72, 72)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(print, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(gender1)
                                .addComponent(gender2)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDP, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clean, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(order, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 890, 450));

        jLabel10.setIcon(new javax.swing.ImageIcon("D:\\Daniel\\ICON\\barber\\background cut3.jpg")); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -130, 1100, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        new menuUtama().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_m_UtamaMouseClicked

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        cari();
    }//GEN-LAST:event_cariActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:    
        save();
    }//GEN-LAST:event_saveActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        
        edit();
    }//GEN-LAST:event_editActionPerformed

    private void viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewActionPerformed
        dataTable();
    }//GEN-LAST:event_viewActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        delete();
    }//GEN-LAST:event_deleteActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        cetak();
    }//GEN-LAST:event_printActionPerformed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        tabel();
    }//GEN-LAST:event_tabelMouseClicked

    private void cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanActionPerformed
        // TODO add your handling code here:
        bersih();
    }//GEN-LAST:event_cleanActionPerformed

    private void orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderActionPerformed
        // TODO add your handling code here:
//        new menuOrder().setVisible(true);
        orderan();
    }//GEN-LAST:event_orderActionPerformed

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
            java.util.logging.Logger.getLogger(menuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menuPelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TanggalSekarang;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cari;
    private javax.swing.JButton clean;
    private javax.swing.JButton delete;
    private javax.swing.JButton edit;
    private javax.swing.JRadioButton gender1;
    private javax.swing.JRadioButton gender2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel keluar;
    private javax.swing.JLabel m_Karyawan;
    private javax.swing.JLabel m_Layanan;
    private javax.swing.JLabel m_Transaksi;
    private javax.swing.JLabel m_Utama;
    private javax.swing.JLabel m_pelanggan;
    private javax.swing.JButton order;
    private javax.swing.JButton print;
    private javax.swing.JButton save;
    private javax.swing.JTable tabel;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHP;
    private javax.swing.JTextField txtIDP;
    private javax.swing.JTextField txtNama;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}