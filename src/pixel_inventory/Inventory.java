package pixel_inventory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

public class Inventory extends javax.swing.JFrame {

    ResultSet result;
    Connection connec;
    Statement state;
    static Double stockProduct;
    static String roleUser="";
    static String userLogin="";
    
    public Inventory() {
        initComponents();
        //loadCombo();
        loadCombo1();
        //loadCombo2();
        loaddeshboard();
        loadsellerCombo();
        loadBrandCombo();
        
        jLabel178.setText(userLogin);
        AutoCompleteDecorator.decorate(jComboBox2);
        AutoCompleteDecorator.decorate(jComboBox1);
        AutoCompleteDecorator.decorate(invProductCombo);
        
    }
  
    public void connection()throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user, password, url;
//        url = "jdbc:mysql://103.169.160.66/organic1_pixel_inventory";
//        user = "organic1_mihir";
//        password = "v5@Gy7SHXWFcft2"; 
        url = "jdbc:mysql://127.0.0.1/organic1_pixel_inventory";
        user = "root";
        password = "";

//        url = "jdbc:mysql://192.168.0.108:3306/organic1_pixel_inventory";
//        user = "global";
//        password = "12345";
        connec = (Connection) DriverManager.getConnection(url, user, password);
        state = connec.createStatement();
    }
    
    
    private void loaddeshboard(){
        try {
            connection();
            //Total Customer
            String query="SELECT COUNT(*) AS recordCount FROM customer";
            ResultSet rs = state.executeQuery(query);
            
            rs .next();
            int count = rs .getInt("recordCount");
            rs .close();
            jLabel64.setText(String.valueOf(count));
            
            //Total Product
            String query1="SELECT COUNT(*) AS recordCount FROM add_stock";
            ResultSet rs1 = state.executeQuery(query1);
            rs1 .next();
            int count1 = rs1 .getInt("recordCount");
            rs1 .close();
            jLabel65.setText(String.valueOf(count1));
            
            //TOtal Order
            String query2="SELECT COUNT(invoice_id) AS InvoiceId FROM new_invoice";
            ResultSet rs2 = state.executeQuery(query2);
            rs2 .next();
            int count2 = rs2 .getInt("InvoiceId");
            rs2 .close();
            jLabel117.setText(String.valueOf(count2));
            
            //Total Stock
            
            String query3="SELECT SUM(`stock`) AS totalStock FROM `add_stock`";
            ResultSet rs3 = state.executeQuery(query3);
            rs3 .next();
            int count3 = rs3 .getInt("totalStock");
            rs3 .close();
            jLabel120.setText(String.valueOf(count3));
            
            
            //Limited Stock
            String query4="SELECT COUNT(*) AS limitedStock FROM `add_stock` WHERE `stock`<=100";
            ResultSet rs4 = state.executeQuery(query4);
            rs4 .next();
            int count4 = rs4 .getInt("limitedStock");
            rs4 .close();
            jLabel121.setText(String.valueOf(count4));
            
            //Count Supplyer
            String query5="SELECT COUNT(*) AS supllyer FROM `add_seller`";
            ResultSet rs5 = state.executeQuery(query5);
            rs5 .next();
            int count5 = rs5 .getInt("supllyer");
            rs5 .close();
            jLabel39.setText(String.valueOf(count5));
            
            //Total Sales
            String query6="SELECT sum(`total_price`) AS totalSaes FROM `new_invoice`";
            ResultSet rs6 = state.executeQuery(query6);
            rs6 .next();
            int count6 = rs6 .getInt("totalSaes");
            rs6 .close();
            jLabel41.setText(String.valueOf(count6));
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadCombo(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("");
        try {
            connection();
            Statement state=connec.createStatement();
            String query="select category_name from add_category";
            ResultSet result = state.executeQuery(query);
            
            while (result.next()) {
                jComboBox2.addItem(result.getString("category_name"));
            }
        } catch (ClassNotFoundException|SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }  
    }
    private void loadsellerCombo(){
        SallNCombo.removeAllItems();
        SallNCombo.addItem("");
        try {
            connection();
            Statement state=connec.createStatement();
            String sellerq="SELECT `saller_name` FROM `add_seller`";
            ResultSet resul = state.executeQuery(sellerq);
           
            while (resul.next()) {
                SallNCombo.addItem(resul.getString("saller_name"));
            }
             
        } catch (ClassNotFoundException|SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
       
    }
    private void loadBrandCombo(){
        PBrand.removeAllItems();
        PBrand.addItem("");
        try {
            connection();
            Statement state=connec.createStatement();
            String sellerq="SELECT brand_name FROM add_brand";
            ResultSet resul = state.executeQuery(sellerq);
           
            while (resul.next()) {
                PBrand.addItem(resul.getString("brand_name"));
            } 
        } catch (ClassNotFoundException|SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
       
    }
    
    private void loadCombo1(){
        invProductCombo.removeAllItems();
        invProductCombo.addItem("");
        try {
            connection();
            Statement state=connec.createStatement();
            String query1="select product_id from add_stock";
            ResultSet result1 = state.executeQuery(query1);
            while (result1.next()) {
                invProductCombo.addItem(result1.getString("product_id"));
            }
             
        } catch (ClassNotFoundException|SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
    private void loadCombo2(){
        jComboBox1.removeAllItems();
        jComboBox6.removeAllItems();
        jComboBox1.addItem("");
        jComboBox6.addItem("");
        try {
            connection();
            Statement state=connec.createStatement();
            String query1="select name from customer";
            ResultSet result1 = state.executeQuery(query1);
            while (result1.next()) {
                jComboBox1.addItem(result1.getString("name"));
                jComboBox6.addItem(result1.getString("name"));
            }    
        } catch (ClassNotFoundException|SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
    
     public void setBackgroundEfact(JPanel panel ) {
//        
//        if(panel.){
//        panel.setBackground(new Color(70, 73, 73));
//        panel.setForeground(Color.WHITE);  
//        }else{
//            
//        }
    }
    
    void showStock(){
        try {
            connection();
            String clgshowQuery="select SL,product_id, product_name, stock, price,Date from add_stock";
            ResultSet rs= state.executeQuery(clgshowQuery);
            Vector<String> header = new Vector<String>();
            header.add("SL");
            header.add("Product ID");
            header.add("Product Name");
            header.add("Stock");;
            header.add("Price");
            header.add("Date");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("SL"));
                row.add(rs.getString("product_id"));
                row.add(rs.getString("product_name"));
                row.add(rs.getString("stock"));
                row.add(rs.getString("price"));
                row.add(rs.getString("Date"));
                data.add(row);
            }
            DefaultTableModel model = (DefaultTableModel) showProducts.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Dashboard = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        addStockPan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField10 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        SellerCountry = new javax.swing.JComboBox<>();
        jLabel101 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        showProducts = new javax.swing.JTable();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel137 = new javax.swing.JLabel();
        PBrand = new javax.swing.JComboBox<>();
        jPanel33 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        SallNCombo = new javax.swing.JComboBox<>();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        Sellerinfo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel102 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        SellName = new javax.swing.JTextField();
        sellMobile = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        Sellfactory = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        sellcounCombo = new javax.swing.JComboBox<>();
        jPanel34 = new javax.swing.JPanel();
        jLabel140 = new javax.swing.JLabel();
        AddCategory = new javax.swing.JPanel();
        AddCategory1 = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        jButton35 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton36 = new javax.swing.JButton();
        jLabel142 = new javax.swing.JLabel();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        brand_name = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        barndDescription = new javax.swing.JTextArea();
        jButton11 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField19 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton40 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        Invoicepan = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jpinvoice = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        bill = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        address = new javax.swing.JLabel();
        shopname = new javax.swing.JLabel();
        Mobile = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        net = new javax.swing.JLabel();
        lbldate = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        per = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        namelbl = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jLabel176 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        invno1 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        invproname = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        InvStock = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        invquantity = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        InvPrice = new javax.swing.JTextField();
        invTotal = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        invProductCombo = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jLabel122 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        deposit = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        deposittext = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jButton39 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel113 = new javax.swing.JLabel();
        custypedepo = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        cusIdDepo = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        cusadddepo = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        duelbl = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel131 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jLabel132 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        deposit_table1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        deposit_table = new javax.swing.JTable();
        Customerpan = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        cliname = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        custshopName = new javax.swing.JTextField();
        custmobileNo = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        cstAddress = new javax.swing.JTextArea();
        jButton8 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        customer = new javax.swing.JTable();
        jLabel66 = new javax.swing.JLabel();
        cusDesignation = new javax.swing.JComboBox<>();
        jPanel35 = new javax.swing.JPanel();
        jLabel147 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        custCode = new javax.swing.JTextField();
        Reportpan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chartimport = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        topSalingProduct = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        panelPiChart = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Accountpan = new javax.swing.JPanel();
        createAccount = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        AccName = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        AccEmail = new javax.swing.JTextField();
        AccMobile = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        accPassword = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        CreateRole = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        accTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        Billpan = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jDateChooser6 = new com.toedter.calendar.JDateChooser();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jpinvoice1 = new javax.swing.JPanel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        bill1 = new javax.swing.JTable();
        jLabel160 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        address1 = new javax.swing.JLabel();
        shopname1 = new javax.swing.JLabel();
        Mobile1 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        net1 = new javax.swing.JLabel();
        lbldate1 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        per1 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        searchProduct = new javax.swing.JPanel();
        jLabel179 = new javax.swing.JLabel();
        newaddStrock = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        DashBoard = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        AddStock = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        showStock = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        addCategory = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        Billpage = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        CatCutomer = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        bill_Deposite = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        Account = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        Logout = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        Reports = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        invoice = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pixel Inventory Management System");
        setIconImage(Toolkit.getDefaultToolkit().getImage(Inventory.class.getResource("Image/inventory.png"))

        );
        setLocation(new java.awt.Point(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(247, 146, 30));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("INVENTORY MANAGEMENT SYSTEM");
        jPanel8.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 790, 70));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/logo.png"))); // NOI18N
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 190, 70));

        jLabel177.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel177.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/user (3).png"))); // NOI18N
        jPanel8.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 0, 100, 70));

        jLabel178.setFont(new java.awt.Font("Raleway", 1, 16)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(230, 236, 255));
        jLabel178.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 56, 100, 30));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -2, 1390, 90));

        Dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/packaging.png"))); // NOI18N
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 80, 60));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(247, 146, 30));
        jLabel26.setText("Total Products");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 130, 30));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 153, 0));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 76, 100, 30));

        Dashboard.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, 210, 150));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setForeground(new java.awt.Color(247, 146, 30));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/empty-box.png"))); // NOI18N
        jLabel30.setText("jLabel30");
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 120, 70));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(247, 146, 30));
        jLabel31.setText("Limited Stock");
        jPanel10.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(247, 146, 30));
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel121.setText(" ");
        jPanel10.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 75, 110, 40));

        Dashboard.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 370, 210, 150));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/storage.png"))); // NOI18N
        jPanel11.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 100, 50));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(247, 146, 30));
        jLabel29.setText("Total Stock");
        jPanel11.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, 30));

        jLabel120.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(247, 146, 30));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel11.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 80, 30));

        Dashboard.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 210, 150));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/supplier.png"))); // NOI18N
        jPanel12.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 130, 70));

        jLabel33.setBackground(new java.awt.Color(252, 135, 32));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(247, 146, 30));
        jLabel33.setText("Supplier");
        jPanel12.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 102, 0));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel12.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 40, 30));

        Dashboard.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 540, 210, 150));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/medal.png"))); // NOI18N
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 110, 60));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(247, 146, 30));
        jLabel35.setText("Top Saling Producs");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 102, 0));
        jLabel40.setText("4");
        jPanel13.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 50, 30));

        Dashboard.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 540, 210, 150));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/sales.png"))); // NOI18N
        jPanel14.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 100, 60));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(247, 146, 30));
        jLabel37.setText("Total Sales (Monthly)");
        jPanel14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 102, 0));
        jPanel14.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 100, 30));

        Dashboard.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 540, 210, 150));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/chat_2_150x150.png"))); // NOI18N
        jPanel15.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 300, 140));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(252, 135, 32));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Massage");
        jPanel15.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 120, -1));

        Dashboard.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 300, 230));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/delivery-truck.png"))); // NOI18N
        jPanel16.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 100, 60));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(252, 135, 32));
        jLabel23.setText("Total Delivary");
        jPanel16.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 105, -1, 30));

        jLabel119.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(252, 135, 32));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setText("10");
        jPanel16.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 120, -1));

        Dashboard.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 210, 140));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/customer.png"))); // NOI18N
        jPanel17.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 110, 50));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(247, 146, 30));
        jLabel25.setText("Total Custommer");
        jPanel17.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 102, 0));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel17.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 76, 80, 30));

        Dashboard.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, 210, 140));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(247, 146, 30));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Dashboard");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        Dashboard.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 990, 60));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/received.png"))); // NOI18N
        jPanel22.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 90, 60));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(252, 135, 32));
        jLabel45.setText("Total Order");
        jPanel22.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 110, 110, -1));

        jLabel117.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(252, 135, 32));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel22.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 90, 30));

        Dashboard.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 210, 140));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/notification_1_150x150.png"))); // NOI18N
        jPanel30.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 220, 170));

        jLabel148.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(252, 135, 32));
        jLabel148.setText("Notification");
        jPanel30.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel36.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 90, 60));

        jLabel150.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(252, 135, 32));
        jLabel150.setText("Total Order");
        jPanel36.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        jPanel30.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 300, 230));

        Dashboard.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 300, 230));

        jTabbedPane1.addTab("tab6", Dashboard);

        addStockPan.setBackground(new java.awt.Color(214, 217, 223));
        addStockPan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(252, 135, 32));
        jLabel1.setText("Product ID");
        addStockPan.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 109, 22));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(252, 135, 32));
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        addStockPan.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 260, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(252, 135, 32));
        jLabel2.setText("Product Name");
        addStockPan.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 109, 30));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(252, 135, 32));
        jTextField3.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        addStockPan.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 260, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(252, 135, 32));
        jLabel3.setText("Product Stock");
        addStockPan.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 109, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(252, 135, 32));
        jLabel4.setText("Category");
        addStockPan.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 109, 22));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(252, 135, 32));
        jLabel5.setText("Unit Price");
        addStockPan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 110, 30));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(252, 135, 32));
        jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        addStockPan.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 260, -1));

        jButton7.setBackground(new java.awt.Color(255, 102, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Insert");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 670, 90, 30));

        jButton9.setBackground(new java.awt.Color(255, 102, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("New");
        jButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 670, 80, 30));

        jComboBox2.setEditable(true);
        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(252, 135, 32));
        jComboBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        addStockPan.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 260, -1));

        jTextField10.setBackground(new java.awt.Color(255, 255, 255));
        jTextField10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 102, 0));
        jTextField10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addStockPan.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 260, 30));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setBackground(new java.awt.Color(255, 51, 0));
        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 102, 0));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("SHOW PRODUCTS");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        addStockPan.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 660, 50));

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(252, 135, 32));
        jLabel99.setText("Brand");
        addStockPan.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 109, 30));

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(252, 135, 32));
        jLabel100.setText("Saler Name");
        addStockPan.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 109, 22));
        addStockPan.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 610, 260, 30));

        SellerCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bangladesh", "China", "India", "Other" }));
        addStockPan.add(SellerCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 260, 30));

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(252, 135, 32));
        jLabel101.setText("Date");
        addStockPan.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 109, 30));

        showProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SL", "Product ID", "Product Name", "Product Stock", "Unit Price", "Date"
            }
        ));
        showProducts.setOpaque(false);
        showProducts.setRowHeight(25);
        showProducts.setShowGrid(true);
        showProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showProductsMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(showProducts);
        if (showProducts.getColumnModel().getColumnCount() > 0) {
            showProducts.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        addStockPan.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, 660, 470));

        jButton28.setBackground(new java.awt.Color(255, 102, 0));
        jButton28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton28.setForeground(new java.awt.Color(255, 255, 255));
        jButton28.setText("Delete Stock");
        jButton28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 670, 120, 30));

        jButton29.setBackground(new java.awt.Color(255, 102, 0));
        jButton29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton29.setForeground(new java.awt.Color(255, 255, 255));
        jButton29.setText("Edit Stock");
        jButton29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton29, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 670, 100, 30));

        jLabel137.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(252, 135, 32));
        jLabel137.setText("Saler Country");
        addStockPan.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 109, 30));

        addStockPan.add(PBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 562, 260, 30));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        jLabel139.setBackground(new java.awt.Color(255, 51, 0));
        jLabel139.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 102, 0));
        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel139.setText("ADD PRODUCTS");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        addStockPan.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 390, 50));

        SallNCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SallNComboItemStateChanged(evt);
            }
        });
        addStockPan.add(SallNCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 462, 260, 30));

        jButton42.setBackground(new java.awt.Color(255, 102, 0));
        jButton42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton42.setForeground(new java.awt.Color(255, 255, 255));
        jButton42.setText("Search Products");
        jButton42.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton42, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 670, 130, 30));

        jButton43.setBackground(new java.awt.Color(255, 102, 0));
        jButton43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton43.setForeground(new java.awt.Color(255, 255, 255));
        jButton43.setText("Show Products");
        jButton43.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        addStockPan.add(jButton43, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 670, 130, 30));

        jTabbedPane1.addTab("tab1", addStockPan);

        Sellerinfo.setForeground(new java.awt.Color(255, 51, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seller ID", "Name", "Mobile", "Factory Name", "Country"
            }
        ));
        jTable1.setRowHeight(25);
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setHeaderValue("Name");
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Mobile");
            jTable1.getColumnModel().getColumn(3).setHeaderValue("Factory Name");
            jTable1.getColumnModel().getColumn(4).setHeaderValue("Country");
        }

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 102, 0));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Seller Information");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton30.setBackground(new java.awt.Color(255, 102, 0));
        jButton30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton30.setForeground(new java.awt.Color(255, 255, 255));
        jButton30.setText("Save");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(255, 102, 0));
        jButton31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton31.setForeground(new java.awt.Color(255, 255, 255));
        jButton31.setText("Edit");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setBackground(new java.awt.Color(255, 102, 0));
        jButton32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton32.setForeground(new java.awt.Color(255, 255, 255));
        jButton32.setText("Delete");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 102, 0));
        jLabel102.setText("Seller ID");

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 102, 0));

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(255, 102, 0));
        jLabel107.setText("Name");

        SellName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SellName.setForeground(new java.awt.Color(255, 102, 0));

        sellMobile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sellMobile.setForeground(new java.awt.Color(255, 102, 0));

        jLabel108.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 102, 0));
        jLabel108.setText("Mobile");

        Sellfactory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Sellfactory.setForeground(new java.awt.Color(255, 102, 0));

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 102, 0));
        jLabel109.setText("Factory Name");

        jLabel110.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 102, 0));
        jLabel110.setText("Country");

        sellcounCombo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sellcounCombo.setForeground(new java.awt.Color(255, 102, 0));
        sellcounCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bangladesh", "India", "China", "Other" }));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        jLabel140.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 102, 0));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setText("Add Seller");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel140)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SellerinfoLayout = new javax.swing.GroupLayout(Sellerinfo);
        Sellerinfo.setLayout(SellerinfoLayout);
        SellerinfoLayout.setHorizontalGroup(
            SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SellerinfoLayout.createSequentialGroup()
                .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SellerinfoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(SellerinfoLayout.createSequentialGroup()
                                    .addComponent(jLabel109)
                                    .addGap(5, 5, 5)
                                    .addComponent(Sellfactory))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SellerinfoLayout.createSequentialGroup()
                                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField12))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SellerinfoLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(SellerinfoLayout.createSequentialGroup()
                                    .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sellMobile)
                                        .addComponent(SellName))))
                            .addGroup(SellerinfoLayout.createSequentialGroup()
                                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sellcounCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SellerinfoLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jButton31)
                        .addGap(70, 70, 70)
                        .addComponent(jButton32)
                        .addGap(1, 1, 1)))
                .addGap(30, 30, 30)
                .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );
        SellerinfoLayout.setVerticalGroup(
            SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellerinfoLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SellerinfoLayout.createSequentialGroup()
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SellName, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sellMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Sellfactory, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sellcounCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(SellerinfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", Sellerinfo);

        AddCategory.setForeground(new java.awt.Color(255, 255, 255));
        AddCategory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AddCategory1.setForeground(new java.awt.Color(255, 102, 0));
        AddCategory1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel141.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 102, 0));
        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel141.setText("Add Category");
        jLabel141.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        AddCategory1.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 100, 40));

        jButton35.setBackground(new java.awt.Color(252, 135, 32));
        jButton35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton35.setForeground(new java.awt.Color(255, 255, 255));
        jButton35.setText("Save");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton35, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 310, 80, 30));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category", "Description"
            }
        ));
        jTable5.setRowHeight(25);
        jTable5.setShowGrid(true);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(jTable5);

        AddCategory1.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 370, 500, 330));

        jButton36.setBackground(new java.awt.Color(252, 135, 32));
        jButton36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton36.setForeground(new java.awt.Color(255, 255, 255));
        jButton36.setText("Show");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton36, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 200, 80, 40));

        jLabel142.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 102, 0));
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel142.setText("Description");
        jLabel142.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        AddCategory1.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 100, 40));

        jButton37.setBackground(new java.awt.Color(252, 135, 32));
        jButton37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton37.setForeground(new java.awt.Color(255, 255, 255));
        jButton37.setText("Update");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton37, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 250, 80, 30));

        jButton38.setBackground(new java.awt.Color(252, 135, 32));
        jButton38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton38.setForeground(new java.awt.Color(255, 255, 255));
        jButton38.setText("Delete");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton38, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 280, 80, 30));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        jLabel143.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 102, 0));
        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel143.setText("Add Brand");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        AddCategory1.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, 500, 50));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        jLabel144.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 102, 0));
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText("Add Category");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        AddCategory1.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 500, 50));

        jLabel145.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 102, 0));
        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel145.setText("Add Brand");
        jLabel145.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        AddCategory1.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 90, 40));

        brand_name.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AddCategory1.add(brand_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 200, 290, 36));

        jLabel146.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 102, 0));
        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel146.setText("Description");
        jLabel146.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        AddCategory1.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 90, 40));

        barndDescription.setColumns(20);
        barndDescription.setRows(5);
        barndDescription.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane13.setViewportView(barndDescription);

        AddCategory1.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 290, 99));

        jButton11.setBackground(new java.awt.Color(252, 135, 32));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Save");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 80, 30));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brands", "Description"
            }
        ));
        jTable2.setRowHeight(25);
        jTable2.setShowGrid(true);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        AddCategory1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 500, 330));

        jButton19.setBackground(new java.awt.Color(252, 135, 32));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Delete");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 202, 80, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane4.setViewportView(jTextArea1);

        AddCategory1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 249, 290, 100));

        jTextField19.setEditable(false);
        AddCategory1.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 90, 40));

        jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AddCategory1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 290, 36));

        jButton40.setBackground(new java.awt.Color(252, 135, 32));
        jButton40.setForeground(new java.awt.Color(255, 255, 255));
        jButton40.setText("Update");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        AddCategory1.add(jButton40, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 250, 80, 30));

        AddCategory.add(AddCategory1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Category", "Description"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(jTable6);

        AddCategory.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 500, 250));

        jTabbedPane1.addTab("tab3", AddCategory);

        Invoicepan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mihir\\Downloads\\Pixel logo\\logo.png")); // NOI18N
        Invoicepan.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jpinvoice.setBackground(new java.awt.Color(255, 255, 255));
        jpinvoice.setForeground(new java.awt.Color(255, 255, 255));
        jpinvoice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel47.setText("Invoice:");
        jLabel47.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 91, 32));

        jLabel13.setText("Date               :");
        jLabel13.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 91, -1));

        jLabel48.setText("Client Name :");
        jLabel48.setMaximumSize(new java.awt.Dimension(70, 15));
        jLabel48.setMinimumSize(new java.awt.Dimension(72, 15));
        jLabel48.setName(""); // NOI18N
        jLabel48.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 80, -1));

        jLabel50.setText("Address         :");
        jLabel50.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 90, 40));

        jLabel49.setText("Mobile NO      :");
        jLabel49.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 155, 90, 20));

        jLabel63.setText("Shop Name  :");
        jpinvoice.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 87, 20));

        bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Quantity", "Total"
            }
        ));
        bill.setGridColor(new java.awt.Color(102, 102, 102));
        bill.setIntercellSpacing(new java.awt.Dimension(1, 1));
        bill.setSelectionBackground(new java.awt.Color(255, 255, 255));
        bill.setSelectionForeground(new java.awt.Color(51, 51, 51));
        bill.setShowHorizontalLines(true);
        bill.setShowVerticalLines(true);
        jScrollPane5.setViewportView(bill);
        if (bill.getColumnModel().getColumnCount() > 0) {
            bill.getColumnModel().getColumn(0).setPreferredWidth(100);
            bill.getColumnModel().getColumn(0).setMaxWidth(100);
            bill.getColumnModel().getColumn(1).setPreferredWidth(250);
            bill.getColumnModel().getColumn(1).setMaxWidth(250);
            bill.getColumnModel().getColumn(2).setHeaderValue("Price");
        }

        jpinvoice.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 187, 690, 390));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("Discount %");
        jpinvoice.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 590, -1, 30));

        jTextField15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(102, 102, 102));
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });
        jpinvoice.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 590, 30, 30));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Total Amount:");
        jpinvoice.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 100, 32));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Net Amount:");
        jpinvoice.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 590, -1, 30));
        jpinvoice.add(address, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 230, 30));
        jpinvoice.add(shopname, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 161, 22));
        jpinvoice.add(Mobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 161, 20));

        total.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        total.setForeground(new java.awt.Color(102, 102, 102));
        jpinvoice.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 590, 110, 32));

        net.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        net.setForeground(new java.awt.Color(51, 51, 51));
        jpinvoice.add(net, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 590, 120, 30));
        jpinvoice.add(lbldate, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 161, 20));

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/logo.png"))); // NOI18N
        jpinvoice.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 171, 51));

        jPanel23.setBackground(new java.awt.Color(204, 204, 204));
        jPanel23.setPreferredSize(new java.awt.Dimension(3, 165));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );

        jpinvoice.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        jLabel78.setText("Address : Corporate office Miazi Manjil, Tusardhara,");
        jpinvoice.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 287, 24));

        jLabel81.setText("Sector-3, Road-3, Kadamtali Thana, Dhaka -1362");
        jpinvoice.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 290, 24));

        jLabel82.setText("Mobile    :  01726688322");
        jpinvoice.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 150, -1));

        jLabel85.setText("Email     :  pixelelectricbd@gmail.com");
        jpinvoice.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 211, -1));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(660, 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jpinvoice.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 585, 680, 3));

        per.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        per.setForeground(new java.awt.Color(153, 153, 153));
        jpinvoice.add(per, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 590, 90, 30));

        jLabel133.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(153, 153, 153));
        jLabel133.setText("=");
        jpinvoice.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 590, 10, 30));

        jLabel134.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(51, 51, 51));
        jLabel134.setText("TK");
        jpinvoice.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 590, 40, 30));

        jLabel135.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(51, 51, 51));
        jLabel135.setText("TK");
        jpinvoice.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 50, 30));

        jLabel136.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(51, 51, 51));
        jLabel136.setText("TK");
        jpinvoice.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 590, 60, 30));

        jLabel174.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jpinvoice.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 140, 30));
        jpinvoice.add(namelbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 220, 20));

        jLabel175.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel175.setForeground(new java.awt.Color(153, 153, 153));
        jpinvoice.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 620, 70, 20));

        jLabel176.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel176.setForeground(new java.awt.Color(153, 153, 153));
        jLabel176.setText("Generated By:");
        jpinvoice.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(599, 620, 80, 20));

        Invoicepan.add(jpinvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 100, 736, 640));

        jLabel19.setText("Invoice  NO");
        Invoicepan.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 105, 32));

        invno1.setBackground(new java.awt.Color(204, 255, 255));
        invno1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                invno1KeyReleased(evt);
            }
        });
        Invoicepan.add(invno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, 185, 32));

        jLabel53.setText("Product Id");
        Invoicepan.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 105, 32));
        Invoicepan.add(invproname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 185, 32));

        jLabel54.setText("Stock");
        Invoicepan.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 105, 32));
        Invoicepan.add(InvStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 185, 32));

        jLabel56.setText("Product Name");
        Invoicepan.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 105, 32));

        invquantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                invquantityKeyReleased(evt);
            }
        });
        Invoicepan.add(invquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 185, 32));

        jLabel69.setText("Price");
        Invoicepan.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 105, 32));

        jLabel70.setText("Quantity");
        Invoicepan.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 105, 32));
        Invoicepan.add(InvPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 185, 32));

        invTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invTotalActionPerformed(evt);
            }
        });
        Invoicepan.add(invTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 590, 185, 32));

        jLabel73.setText("Total");
        Invoicepan.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 105, 32));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Create Invoice");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addContainerGap())
        );

        Invoicepan.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 100, 310, -1));

        jLabel86.setText("Customer Name");
        Invoicepan.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 105, 29));

        jDateChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyReleased(evt);
            }
        });
        Invoicepan.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 185, 29));

        invProductCombo.setBorder(null);
        invProductCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                invProductComboItemStateChanged(evt);
            }
        });
        Invoicepan.add(invProductCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 185, 32));

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        Invoicepan.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 690, 70, 40));

        remove.setText("Remove");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });
        Invoicepan.add(remove, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 640, 100, 40));

        jButton10.setText("Save Invoice");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        Invoicepan.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 640, -1, 40));

        jButton12.setText("Add Table");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        Invoicepan.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 100, 40));

        jButton27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 153, 0));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/printer (1).png"))); // NOI18N
        jButton27.setText("Print");
        jButton27.setIconTextGap(8);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        Invoicepan.add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 690, 110, 40));

        jButton41.setText("Save Bill");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        Invoicepan.add(jButton41, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 690, 90, 40));

        jLabel122.setText("Date");
        Invoicepan.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 105, 29));

        jComboBox1.setEditable(true);
        jComboBox1.setPreferredSize(new java.awt.Dimension(72, 25));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        Invoicepan.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 190, 180, 30));

        jTabbedPane1.addTab("tab5", Invoicepan);

        deposit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 102, 0));
        jLabel8.setText("Address");
        deposit.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 86, 36));

        jButton13.setBackground(new java.awt.Color(255, 102, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Print");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        deposit.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(935, 995, -1, -1));

        jButton14.setBackground(new java.awt.Color(255, 102, 0));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Update");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        deposit.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 790, -1, -1));

        jButton15.setBackground(new java.awt.Color(255, 102, 0));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Save");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        deposit.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 790, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 102, 0));
        jLabel12.setText("Deposit");
        deposit.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, 30));

        deposittext.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        deposittext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                deposittextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                deposittextFocusLost(evt);
            }
        });
        deposit.add(deposittext, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, 201, 36));

        jButton18.setBackground(new java.awt.Color(255, 102, 0));
        jButton18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Delete");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        deposit.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 790, -1, -1));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 102, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Deposit History");

        jButton39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton39.setForeground(new java.awt.Color(255, 102, 0));
        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/printer (1).png"))); // NOI18N
        jButton39.setText("Print");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton39)
                .addGap(67, 67, 67)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        deposit.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 700, -1));

        jButton22.setBackground(new java.awt.Color(255, 102, 0));
        jButton22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Show");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        deposit.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 790, -1, -1));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 102, 0));
        jLabel58.setText("Date");
        deposit.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 58, 40));
        deposit.add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 201, 40));

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 102, 0));
        jLabel113.setText("Customer Type");
        deposit.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 105, 35));

        custypedepo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)));
        deposit.add(custypedepo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 201, 40));

        jLabel115.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 102, 0));
        jLabel115.setText("Customer ID");
        deposit.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 92, 22));

        cusIdDepo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        deposit.add(cusIdDepo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 201, 32));

        jLabel116.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 102, 0));
        jLabel116.setText("Name");
        deposit.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 92, 31));

        jComboBox6.setEditable(true);
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        deposit.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 201, 38));

        cusadddepo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)));
        deposit.add(cusadddepo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, 201, 36));

        jLabel118.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(255, 102, 0));
        jLabel118.setText("Total Amount");
        deposit.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 100, 36));

        duelbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)));
        deposit.add(duelbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 396, 201, 40));

        jButton21.setBackground(new java.awt.Color(255, 255, 255));
        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 102, 0));
        jButton21.setText("Save Deposit");
        jButton21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        deposit.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(219, 500, 120, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 102, 0));
        jLabel9.setText("Deposit :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 102, 0));
        jLabel10.setText("Total     :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 102, 0));

        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 102, 0));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 102, 0));
        jLabel17.setText("Due       :");

        jLabel114.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        deposit.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 320, 140));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel128.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 102, 0));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setText("Deposit");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel128)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        deposit.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 101, 324, -1));

        jLabel129.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 102, 0));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/search (1).png"))); // NOI18N
        deposit.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 40, 40));

        jLabel130.setBackground(new java.awt.Color(255, 255, 255));
        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 102, 0));
        jLabel130.setText("Search By Date:");
        jLabel130.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        jLabel130.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel130MouseClicked(evt);
            }
        });
        deposit.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 140, 40));
        deposit.add(jDateChooser4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 180, 40));

        jLabel131.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setText("TO");
        jLabel131.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0)));
        deposit.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, 30, 40));
        deposit.add(jDateChooser5, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, 200, 40));

        jLabel132.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 102, 0));
        jLabel132.setText("ID:");
        deposit.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 160, 30, 40));

        jTextField4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 102, 0));
        deposit.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, 70, 40));

        jButton33.setBackground(new java.awt.Color(255, 255, 255));
        jButton33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton33.setForeground(new java.awt.Color(255, 102, 0));
        jButton33.setText("Clear");
        jButton33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        deposit.add(jButton33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 80, 30));

        jButton34.setBackground(new java.awt.Color(255, 255, 255));
        jButton34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton34.setForeground(new java.awt.Color(255, 102, 0));
        jButton34.setText("Update");
        jButton34.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        deposit.add(jButton34, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 500, 100, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel67.setBackground(new java.awt.Color(255, 102, 0));
        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 102, 0));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("All Order");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        deposit.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 700, 40));

        deposit_table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice", "Custommer Name", "Mobile", "Net Ammount", "Date"
            }
        ));
        deposit_table1.setRowHeight(25);
        deposit_table1.setShowGrid(true);
        jScrollPane16.setViewportView(deposit_table1);

        deposit.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 470, 700, 220));

        deposit_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Custommer Name", "Total Amount", "Deposit", "Date"
            }
        ));
        deposit_table.setRowHeight(25);
        deposit_table.setShowGrid(true);
        jScrollPane3.setViewportView(deposit_table);
        if (deposit_table.getColumnModel().getColumnCount() > 0) {
            deposit_table.getColumnModel().getColumn(3).setHeaderValue("Total");
            deposit_table.getColumnModel().getColumn(4).setHeaderValue("discount");
        }

        deposit.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 700, 210));

        jTabbedPane1.addTab("tab4", deposit);

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 153, 0));
        jLabel57.setText("Client Name");

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 153, 0));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("ALL CUSTOMER");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 153, 0));
        jLabel60.setText("Shop Name");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 153, 0));
        jLabel61.setText("Mobile NO");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 153, 0));
        jLabel62.setText("Address");

        cstAddress.setColumns(20);
        cstAddress.setRows(5);
        jScrollPane7.setViewportView(cstAddress);

        jButton8.setBackground(new java.awt.Color(255, 153, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Add");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(255, 153, 0));
        jButton23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Edit");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(255, 153, 0));
        jButton24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Delete");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setBackground(new java.awt.Color(255, 153, 0));
        jButton25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("Show");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        customer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Code", "Name", "Designation", "Shop Name", "Mobile", "Address"
            }
        ));
        customer.setRowHeight(25);
        customer.setShowGrid(true);
        customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(customer);

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 153, 0));
        jLabel66.setText("Customer Type");

        cusDesignation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Distributor", "Dealer", "Super Dealer", "Personal Code" }));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        jLabel147.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 153, 0));
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel147.setText("ADD CUSTOMER");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel147)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel123.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 153, 0));
        jLabel123.setText("Customer Code");

        javax.swing.GroupLayout CustomerpanLayout = new javax.swing.GroupLayout(Customerpan);
        Customerpan.setLayout(CustomerpanLayout);
        CustomerpanLayout.setHorizontalGroup(
            CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerpanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(CustomerpanLayout.createSequentialGroup()
                            .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(CustomerpanLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(jButton24)
                                    .addGap(39, 39, 39)
                                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CustomerpanLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(custmobileNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(CustomerpanLayout.createSequentialGroup()
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(44, 44, 44)
                            .addComponent(cliname))
                        .addGroup(CustomerpanLayout.createSequentialGroup()
                            .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cusDesignation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(custshopName, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(CustomerpanLayout.createSequentialGroup()
                            .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(custCode, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        CustomerpanLayout.setVerticalGroup(
            CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CustomerpanLayout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CustomerpanLayout.createSequentialGroup()
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(CustomerpanLayout.createSequentialGroup()
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(custCode, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel123, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cliname, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cusDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(custshopName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(custmobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CustomerpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(91, 91, 91))
        );

        jTabbedPane1.addTab("tab7", Customerpan);

        Reportpan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());
        Reportpan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 163, 500, 271));

        chartimport.setBackground(new java.awt.Color(255, 255, 255));
        chartimport.setLayout(new java.awt.BorderLayout());

        topSalingProduct.setBackground(new java.awt.Color(255, 255, 255));
        topSalingProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name"
            }
        ));
        topSalingProduct.setRowHeight(25);
        topSalingProduct.setShowGrid(true);
        topSalingProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topSalingProductMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(topSalingProduct);

        chartimport.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("Top Saling Products");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        chartimport.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        Reportpan.add(chartimport, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 163, 500, 270));

        panelPiChart.setBackground(new java.awt.Color(255, 255, 255));
        panelPiChart.setLayout(new java.awt.BorderLayout());
        Reportpan.add(panelPiChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 452, 500, 271));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.BorderLayout());
        Reportpan.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 452, 500, 270));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Reports");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Reportpan.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 103, 1012, -1));

        jTabbedPane1.addTab("tab8", Reportpan);

        createAccount.setBackground(new java.awt.Color(255, 255, 255));

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(252, 135, 32));
        jLabel89.setText("Name");

        AccName.setBackground(new java.awt.Color(214, 217, 223));
        AccName.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(252, 135, 32));
        jLabel90.setText("Email");

        AccEmail.setBackground(new java.awt.Color(214, 217, 223));
        AccEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        AccMobile.setBackground(new java.awt.Color(214, 217, 223));
        AccMobile.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(252, 135, 32));
        jLabel91.setText("Mobile");

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(252, 135, 32));
        jLabel92.setText("Role");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(252, 135, 32));
        jLabel93.setText("Password");

        accPassword.setBackground(new java.awt.Color(214, 217, 223));
        accPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(252, 135, 32));
        jLabel94.setText("Confirm Pass");

        jTextField16.setBackground(new java.awt.Color(214, 217, 223));
        jTextField16.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(252, 135, 32));
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("Create Account");
        jLabel97.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton5.setBackground(new java.awt.Color(255, 153, 51));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Sign In");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 153, 51));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Log In");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        CreateRole.setBackground(new java.awt.Color(214, 217, 223));
        CreateRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Stock Keeper", "" }));
        CreateRole.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        javax.swing.GroupLayout createAccountLayout = new javax.swing.GroupLayout(createAccount);
        createAccount.setLayout(createAccountLayout);
        createAccountLayout.setHorizontalGroup(
            createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAccountLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createAccountLayout.createSequentialGroup()
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(createAccountLayout.createSequentialGroup()
                        .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(createAccountLayout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createAccountLayout.createSequentialGroup()
                                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(createAccountLayout.createSequentialGroup()
                                        .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(createAccountLayout.createSequentialGroup()
                                        .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(createAccountLayout.createSequentialGroup()
                                        .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(createAccountLayout.createSequentialGroup()
                                        .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                        .addGap(21, 21, 21)))
                                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AccName, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton5)
                                        .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(AccMobile, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CreateRole, javax.swing.GroupLayout.Alignment.LEADING, 0, 231, Short.MAX_VALUE)
                                            .addComponent(accPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField16, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AccEmail, javax.swing.GroupLayout.Alignment.LEADING))))))
                        .addGap(31, 31, 31))))
        );
        createAccountLayout.setVerticalGroup(
            createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createAccountLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CreateRole, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(createAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        accTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        accTable.setForeground(new java.awt.Color(255, 102, 0));
        accTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Email", "Mobile", "Role"
            }
        ));
        accTable.setRowHeight(25);
        accTable.setShowGrid(true);
        jScrollPane6.setViewportView(accTable);

        jButton1.setBackground(new java.awt.Color(255, 153, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Edit Account");

        jButton26.setBackground(new java.awt.Color(255, 153, 51));
        jButton26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton26.setForeground(new java.awt.Color(255, 255, 255));
        jButton26.setText("Delete Account");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 153, 51));
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setText("Accounts");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jButton20.setBackground(new java.awt.Color(255, 153, 51));
        jButton20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton20.setForeground(new java.awt.Color(255, 255, 255));
        jButton20.setText("Change Password");

        javax.swing.GroupLayout AccountpanLayout = new javax.swing.GroupLayout(Accountpan);
        Accountpan.setLayout(AccountpanLayout);
        AccountpanLayout.setHorizontalGroup(
            AccountpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountpanLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(createAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AccountpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addGroup(AccountpanLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton20)
                        .addGap(74, 74, 74)
                        .addComponent(jButton26))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        AccountpanLayout.setVerticalGroup(
            AccountpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccountpanLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addGroup(AccountpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AccountpanLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AccountpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(AccountpanLayout.createSequentialGroup()
                        .addComponent(createAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(78, 78, 78))
        );

        jTabbedPane1.addTab("tab9", Accountpan);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        jLabel151.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 153, 0));
        jLabel151.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel151.setText("BILL SEARCH");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/search (2).png"))); // NOI18N
        jButton17.setText("Search Bill");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel152.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(255, 153, 0));
        jLabel152.setText("By Invoice");

        jLabel153.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(255, 153, 0));
        jLabel153.setText("By Date");

        jTable3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice"
            }
        ));
        jTable3.setRowHeight(30);
        jTable3.setShowGrid(true);
        jScrollPane12.setViewportView(jTable3);

        jpinvoice1.setBackground(new java.awt.Color(255, 255, 255));
        jpinvoice1.setForeground(new java.awt.Color(255, 255, 255));
        jpinvoice1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel154.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel154.setText("Invoice:");
        jLabel154.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice1.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 17, 91, 32));

        jLabel155.setText("Date               :");
        jLabel155.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice1.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 65, 91, -1));

        jLabel156.setText("Client Name :");
        jLabel156.setMaximumSize(new java.awt.Dimension(70, 15));
        jLabel156.setMinimumSize(new java.awt.Dimension(72, 15));
        jLabel156.setName(""); // NOI18N
        jLabel156.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice1.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 114, 80, 20));

        jLabel157.setText("Address         :");
        jLabel157.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice1.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 137, 87, 30));

        jLabel158.setText("Mobile NO     :");
        jLabel158.setPreferredSize(new java.awt.Dimension(70, 15));
        jpinvoice1.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 168, 78, -1));

        jLabel159.setText("Shop Name  :");
        jpinvoice1.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 86, 87, -1));

        bill1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Quantity", "Total"
            }
        ));
        bill1.setGridColor(new java.awt.Color(102, 102, 102));
        bill1.setIntercellSpacing(new java.awt.Dimension(1, 1));
        bill1.setRowHeight(25);
        bill1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        bill1.setSelectionForeground(new java.awt.Color(51, 51, 51));
        bill1.setShowHorizontalLines(true);
        bill1.setShowVerticalLines(true);
        jScrollPane15.setViewportView(bill1);
        if (bill1.getColumnModel().getColumnCount() > 0) {
            bill1.getColumnModel().getColumn(0).setPreferredWidth(100);
            bill1.getColumnModel().getColumn(0).setMaxWidth(100);
            bill1.getColumnModel().getColumn(1).setPreferredWidth(250);
            bill1.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        jpinvoice1.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 187, 710, 370));

        jLabel160.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(102, 102, 102));
        jLabel160.setText("Discount %");
        jpinvoice1.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 580, -1, 30));

        jTextField17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(102, 102, 102));
        jTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField17KeyReleased(evt);
            }
        });
        jpinvoice1.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 580, 30, 30));

        jLabel161.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(102, 102, 102));
        jLabel161.setText("Total Amount:");
        jpinvoice1.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 100, 32));

        jLabel162.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(102, 102, 102));
        jLabel162.setText("Net Amount:");
        jpinvoice1.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 580, -1, 30));
        jpinvoice1.add(address1, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 143, 190, 24));
        jpinvoice1.add(shopname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 86, 160, 22));
        jpinvoice1.add(Mobile1, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 169, 160, 14));

        total1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        total1.setForeground(new java.awt.Color(102, 102, 102));
        jpinvoice1.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 580, 110, 32));

        net1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        net1.setForeground(new java.awt.Color(102, 102, 102));
        jpinvoice1.add(net1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 580, 120, 30));
        jpinvoice1.add(lbldate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 60, 160, 30));

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel163.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/logo.png"))); // NOI18N
        jpinvoice1.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 171, 51));

        jPanel37.setBackground(new java.awt.Color(204, 204, 204));
        jPanel37.setPreferredSize(new java.awt.Dimension(3, 165));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        jpinvoice1.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 15, -1, 160));

        jLabel164.setText("Address : Corporate office Miazi Manjil, Tusardhara,");
        jpinvoice1.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 287, 24));

        jLabel165.setText("Sector-3, Road-3, Kadamtali Thana, Dhaka -1362");
        jpinvoice1.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 114, 290, 30));

        jLabel166.setText("Mobile    :  01726688322");
        jpinvoice1.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 150, -1));

        jLabel167.setText("Email     :  pixelelectricbd@gmail.com");
        jpinvoice1.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 211, -1));

        jPanel38.setBackground(new java.awt.Color(204, 204, 204));
        jPanel38.setPreferredSize(new java.awt.Dimension(660, 3));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jpinvoice1.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 565, 680, 3));

        per1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        per1.setForeground(new java.awt.Color(153, 153, 153));
        jpinvoice1.add(per1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 580, 90, 30));

        jLabel168.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel168.setForeground(new java.awt.Color(153, 153, 153));
        jLabel168.setText("=");
        jpinvoice1.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 10, 30));

        jLabel169.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel169.setForeground(new java.awt.Color(102, 102, 102));
        jLabel169.setText("TK");
        jpinvoice1.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, 30, 30));

        jLabel170.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel170.setForeground(new java.awt.Color(102, 102, 102));
        jLabel170.setText("TK");
        jpinvoice1.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 580, 40, 30));

        jLabel171.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(102, 102, 102));
        jLabel171.setText("TK");
        jpinvoice1.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 580, 50, 30));
        jpinvoice1.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 160, 30));

        jLabel173.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jpinvoice1.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(559, 16, 150, 40));

        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/printer (1).png"))); // NOI18N
        jButton16.setText("Print Bill");
        jButton16.setIconTextGap(8);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BillpanLayout = new javax.swing.GroupLayout(Billpan);
        Billpan.setLayout(BillpanLayout);
        BillpanLayout.setHorizontalGroup(
            BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillpanLayout.createSequentialGroup()
                .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BillpanLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(BillpanLayout.createSequentialGroup()
                                    .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jDateChooser6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(BillpanLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jpinvoice1, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        BillpanLayout.setVerticalGroup(
            BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillpanLayout.createSequentialGroup()
                .addGap(0, 110, Short.MAX_VALUE)
                .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpinvoice1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillpanLayout.createSequentialGroup()
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField6)
                            .addComponent(jLabel152, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BillpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("tab10", Billpan);

        jLabel179.setText("Search Items");

        javax.swing.GroupLayout searchProductLayout = new javax.swing.GroupLayout(searchProduct);
        searchProduct.setLayout(searchProductLayout);
        searchProductLayout.setHorizontalGroup(
            searchProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProductLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel179, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(879, Short.MAX_VALUE))
        );
        searchProductLayout.setVerticalGroup(
            searchProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProductLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel179, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(650, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab11", searchProduct);

        javax.swing.GroupLayout newaddStrockLayout = new javax.swing.GroupLayout(newaddStrock);
        newaddStrock.setLayout(newaddStrockLayout);
        newaddStrockLayout.setHorizontalGroup(
            newaddStrockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        newaddStrockLayout.setVerticalGroup(
            newaddStrockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab12", newaddStrock);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, -33, 1110, 780));

        jPanel7.setBackground(new java.awt.Color(247, 146, 30));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashBoard.setBackground(new java.awt.Color(247, 146, 30));
        DashBoard.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        DashBoard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DashBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashBoardMouseClicked(evt);
            }
        });
        DashBoard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/inventory_1_50x50.png"))); // NOI18N
        DashBoard.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Dash Board");
        DashBoard.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(DashBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 90, 270, 60));

        AddStock.setBackground(new java.awt.Color(247, 146, 30));
        AddStock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        AddStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddStockMouseClicked(evt);
            }
        });
        AddStock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/inventory_3_50x50.png"))); // NOI18N
        AddStock.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Add Stock");
        AddStock.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(AddStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 150, 270, 60));

        showStock.setBackground(new java.awt.Color(247, 146, 30));
        showStock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        showStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        showStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showStockMouseClicked(evt);
            }
        });
        showStock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/exchange_50x50.png"))); // NOI18N
        showStock.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("M. Concern");
        showStock.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(showStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 210, 270, 60));

        addCategory.setBackground(new java.awt.Color(247, 146, 30));
        addCategory.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        addCategory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCategoryMouseClicked(evt);
            }
        });
        addCategory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/inventory_4_50x50.png"))); // NOI18N
        addCategory.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Category");
        addCategory.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(addCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 270, 270, 60));

        Billpage.setBackground(new java.awt.Color(247, 146, 30));
        Billpage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        Billpage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Billpage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BillpageMouseClicked(evt);
            }
        });
        Billpage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/bill_50x50.png"))); // NOI18N
        Billpage.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel126.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Bill Search");
        Billpage.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(Billpage, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 450, 270, 60));

        CatCutomer.setBackground(new java.awt.Color(247, 146, 30));
        CatCutomer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        CatCutomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CatCutomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CatCutomerMouseClicked(evt);
            }
        });
        CatCutomer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/crm_50x50.png"))); // NOI18N
        CatCutomer.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Customer");
        CatCutomer.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(CatCutomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 510, 270, 60));

        bill_Deposite.setBackground(new java.awt.Color(247, 146, 30));
        bill_Deposite.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        bill_Deposite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bill_Deposite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bill_DepositeMouseClicked(evt);
            }
        });
        bill_Deposite.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/investment_50x50.png"))); // NOI18N
        bill_Deposite.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Deposit");
        bill_Deposite.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(bill_Deposite, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 390, 270, 60));

        Account.setBackground(new java.awt.Color(247, 146, 30));
        Account.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        Account.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccountMouseClicked(evt);
            }
        });
        Account.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixel_inventory/Image/account_1_50x50.png"))); // NOI18N
        Account.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Account");
        Account.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(Account, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 630, 270, 60));

        Logout.setBackground(new java.awt.Color(247, 146, 30));
        Logout.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        Logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });
        Logout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/logout_1_50x50.png"))); // NOI18N
        Logout.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel112.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setText("Logout");
        Logout.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(Logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 690, 270, 60));

        Reports.setBackground(new java.awt.Color(247, 146, 30));
        Reports.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        Reports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportsMouseClicked(evt);
            }
        });
        Reports.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/analytics_50x50.png"))); // NOI18N
        Reports.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Report");
        Reports.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(Reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 570, 270, 60));

        invoice.setBackground(new java.awt.Color(247, 146, 30));
        invoice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        invoice.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invoiceMouseClicked(evt);
            }
        });
        invoice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menueImage/inventory_5_1_50x50.png"))); // NOI18N
        invoice.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 1, 75, 58));

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Invoice");
        invoice.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 7, 140, 46));

        jPanel7.add(invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 330, 270, 60));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -3, 270, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents
void loadCategoryTable(){
 try {
            connection();
            String clgshowQuery="select id,category_name,description from add_category";
            ResultSet rs= state.executeQuery(clgshowQuery);
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Category");
            header.add("Description");            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("category_name"));
                row.add(rs.getString("description"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    void loadUserAccount(){
        try {
            connection();
            String clgshowQuery="select 	user_name,email,mobile,role from login_details";
            ResultSet rs= state.executeQuery(clgshowQuery);

            Vector<String> header = new Vector<String>();
            header.add("Name");
            header.add("Email");
            header.add("Mobile");
            header.add("Role");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("user_name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("mobile"));
                row.add(rs.getString("role"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) accTable.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            connection();
            String categoeyName=jTextField2.getText();
            String Description=jTextArea1.getText();
            String clginsQuery="INSERT INTO `add_category`(`category_name`, `description`) VALUES ('"+categoeyName+"','"+Description+"')";
            state.executeUpdate(clginsQuery);
            JOptionPane.showMessageDialog(null, "Your Category Save Successfully!");
            state.close();
            connec.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadCategoryTable();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        try {
            connection();
            String pId=jTextField1.getText();
            String pName=jTextField10.getText();
            Double Stock=Double.parseDouble(jTextField3.getText());
            String Category=String.valueOf(jComboBox2.getSelectedItem());
            Double Price=Double.parseDouble(jTextField5.getText());
            String SName=SallNCombo.getSelectedItem().toString();
            String SCountry=SellerCountry.getSelectedItem().toString();
            String Brand=PBrand.getSelectedItem().toString();
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser2.getDate();
            String StockDate=sdp.format(gdate);
            String clginsQuery="INSERT INTO `add_stock`(`product_id`, `product_name`, `stock`, `category`, `price`,`Seller_name`, `Seller_Country`, `Brand`, `Date`) VALUES ('"+pId+"','"+pName+"',"+Stock+",'"+Category+"',"+Price+",'"+SName+"','"+SCountry+"','"+Brand+"','"+StockDate+"')";
            state.executeUpdate(clginsQuery);
            JOptionPane.showMessageDialog(null, "Your Product Added Successfully!");
            state.close();
            connec.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showStock();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void deposittextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deposittextFocusGained

    }//GEN-LAST:event_deposittextFocusGained

    private void deposittextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deposittextFocusLost

    }//GEN-LAST:event_deposittextFocusLost

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
    try {
            String sqls = "select id, category_name,description from add_category";
            Statement state=connec.createStatement();
            state.executeQuery(sqls);
            int index = jTable2.getSelectedRow();
            jTextField19.setText(jTable2.getValueAt(index, 0).toString());
            jTextField2.setText(jTable2.getValueAt(index, 1).toString());
            jTextArea1.setText(jTable2.getValueAt(index, 2).toString());
        } catch (SQLException exp){
            System.out.println("exp");
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField10.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
         
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
      
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
//        try {
//            connection();
//            int invid=Integer.parseInt(invno1.getText());
//            String pId=invproId.getText();
//            String pName = invProductCombo.getSelectedItem().toString();
//            int Stock=Integer.parseInt(InvStock.getText());
//            int Quentity=Integer.parseInt(invquantity.getText());
//            int Price=Integer.parseInt(InvPrice.getText());
//            int totalPrice=Integer.parseInt(invTotal.getText());
//            
//            //System.out.println(invid);
//            String invQuery="INSERT INTO `invoice`(inv_id, product_id1, product_name1, stock1, quentity, price1, total_price) VALUES ("+invid+",'"+pId+"','"+pName+"',"+Stock+","+Quentity+","+Price+","+totalPrice+")";
//             System.out.println(invQuery);
//            
////String clginsQuery="INSERT INTO add_category VALUES('"+categoeyName+"','"+Description+"')";
//            state.executeUpdate(invQuery);
//            
//           
//            
//            JOptionPane.showMessageDialog(null, "Your Invoice Save Successfully!");
//            state.close();
//            connec.close();
//            
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        
    }//GEN-LAST:event_jButton15ActionPerformed

    private void customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_customerMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            String cusCode, name, designation, shopName,mobile,address;
            name=cliname.getText();
            designation=cusDesignation.getSelectedItem().toString();
            shopName=custshopName.getText();
            mobile=custmobileNo.getText();
            address=cstAddress.getText();
            cusCode=custCode.getText();
            connection();
            String custQuery="INSERT INTO `customer`(cust_code,`name`,`designation`, `shop_name`, `mobile`, `address`) VALUES ('"+cusCode+"','"+name+"','"+designation+"','"+shopName+"','"+mobile+"','"+address+"')";
            state.executeUpdate(custQuery);
            JOptionPane.showMessageDialog(null, "Your Customer added Successfully!");
            state.close();
            connec.close();   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showCustomerTable();
    }//GEN-LAST:event_jButton8ActionPerformed
   
    void showCustomerBill(){
        try {
            connection();
            String custshowQuery="select * from customer";
            ResultSet rs= state.executeQuery(custshowQuery);
            Vector<String> header = new Vector<String>();
            
            header.add("Invoice");
            header.add("Name");
            header.add("Mobile");
            header.add("Net Ammount");
            header.add("Date");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("name"));
                row.add(rs.getString("designation"));
                row.add(rs.getString("shop_name"));
                row.add(rs.getString("mobile"));
                row.add(rs.getString("address"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) customer.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        } 
    

    
    }
    
    
    
    void showCustomerTable(){
        try {
            connection();
            String depositshowQuery="SELECT id,cust_code,`name`, `designation`, `shop_name`, `mobile`, `address` FROM `customer`";
            ResultSet rs= state.executeQuery(depositshowQuery);
            Vector<String> header = new Vector<String>();
            //showCustomerTable();
            header.add("ID");
            header.add("Code");
            header.add("Name");
            header.add("Custommer Type");
            header.add("Shop Name");
            header.add("Mobile");
            header.add("Address");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("cust_code"));
                row.add(rs.getString("name"));
                row.add(rs.getString("designation"));
                row.add(rs.getString("shop_name"));
                row.add(rs.getString("mobile"));
                row.add(rs.getString("address"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) customer.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        showCustomerTable();
        
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        String Query = "SELECT * FROM customer WHERE name='"+String.valueOf(jComboBox1.getSelectedItem())+"'";
        try {
            connection();
            Statement state=connec.createStatement();
           ResultSet rs = state.executeQuery(Query);
            while (rs.next()) {
                Mobile.setText(rs.getString("mobile"));
                shopname.setText(rs.getString("shop_name"));
                address.setText(rs.getString("address"));
                namelbl.setText(rs.getString("name"));
            }
        } catch (ClassNotFoundException|SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
   
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        try {
            connection();
            String invshowQuery="select * from invoice";
            ResultSet rs= state.executeQuery(invshowQuery);
            Vector<String> header = new Vector<String>();
            header.add("INV NO");
            header.add("ID");
            header.add("Name");
            header.add("Stock");
            header.add("Quentity");
            header.add("Price");
            header.add("Total");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("inv_id"));
                row.add(rs.getString("product_id1"));
                row.add(rs.getString("product_name1"));
                row.add(rs.getString("stock1"));
                row.add(rs.getString("quentity"));
                row.add(rs.getString("price1"));
                row.add(rs.getString("total_price"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) deposit_table.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        // TODO add your handling code here:
        
        
        if(jTextField15.getText().equals("")){
             JOptionPane.showMessageDialog(rootPane, "Please Enter Discount Amount");
             net.setText("");
            }else{
            double amount=Double.valueOf(total.getText());
            double parcent=Double.valueOf(jTextField15.getText());
            double formula=(amount/100)*parcent;
            double discountAmount=amount-formula;
//            net.setText(String.valueOf(discountAmount));
//            per.setText(String.valueOf(formula));
            net.setText(String.valueOf(discountAmount));
            per.setText(String.valueOf(formula));
        }   
    }//GEN-LAST:event_jTextField15KeyReleased

    public void printMethod(JPanel panel){
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");

        job.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.LANDSCAPE);
                pf.equals(pf);
                
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.82, 0.82);
                

                panel.print(g2);

                return Printable.PAGE_EXISTS;

            }

        });
        boolean ok = job.printDialog();
        if (ok) {
            try {

                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    
    
    
    }
    
    private void DashBoardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashBoardMouseClicked
        jTabbedPane1.setSelectedIndex(0);
        //DashBoard.setBackground(Color.green);
        setBackgroundEfact(DashBoard);
    }//GEN-LAST:event_DashBoardMouseClicked

    private void AddStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddStockMouseClicked
        jTabbedPane1.setSelectedIndex(11);
//        
//        if(roleUser.equals("Admin")){
//        loadCombo();
//        loadsellerCombo();
//        loadBrandCombo();
//        showStock();
//        jTabbedPane1.setSelectedIndex(1); 
//        
//        }
//        
//         
//   


    }//GEN-LAST:event_AddStockMouseClicked
    
    void showSellerTable(){
        try {
            connection();
            String sellershowQuery="SELECT `Id`, `saller_name`, `mobile`, `factory_name`, `country` FROM `add_seller`";
           
            ResultSet rs= state.executeQuery(sellershowQuery);

            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Seller Name");
            header.add("Mobile");
            header.add("Factory");
            header.add("Country");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("Id"));
                row.add(rs.getString("saller_name"));
                row.add(rs.getString("mobile"));
                row.add(rs.getString("factory_name"));
                row.add(rs.getString("country"));
                
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    private void showStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showStockMouseClicked
        jTabbedPane1.setSelectedIndex(2);
        showSellerTable(); 
        setBackgroundEfact(showStock);
    }//GEN-LAST:event_showStockMouseClicked
    
    private void addCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCategoryMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        loadBrandTable();
        try {
            connection();
            String clgshowQuery="select id,category_name,description from add_category";
            ResultSet rs= state.executeQuery(clgshowQuery);

            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Category");
            header.add("Description");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("category_name"));
                row.add(rs.getString("description"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }//GEN-LAST:event_addCategoryMouseClicked

    private void invoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoiceMouseClicked
        
        loadCombo1();
        loadCombo2();
        jTabbedPane1.setSelectedIndex(4);
        lbldate.setText(java.time.LocalDate.now().toString());
        jLabel175.setText(userLogin);
        
    }//GEN-LAST:event_invoiceMouseClicked
    
    void showdeposittable(){
            try {
            connection();
            String depositshowQuery="SELECT `id`, `custommer_name`, `due`, `deposit`, `date` FROM `deposit`";
            ResultSet rs= state.executeQuery(depositshowQuery);
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Customer Name");
            header.add("Total Amount");
            header.add("Deposit");
            header.add("Date");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("custommer_name"));
                row.add(rs.getString("due"));
                row.add(rs.getString("deposit"));
                row.add(rs.getString("date"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) deposit_table.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    void Showbill(){
    
    try {
            connection();
            String custshowQuery="SELECT `invoice_no`, `customer_name`, `mobile`, `total_ammount`, `discount`, `net_amount`, `date` FROM `bill`";
            ResultSet rs= state.executeQuery(custshowQuery);
            Vector<String> header = new Vector<String>();
            
            header.add("Invoice");
            header.add("Name");
            header.add("Mobile");
            header.add("Net Ammount");
            header.add("Date");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("invoice_no"));
                row.add(rs.getString("customer_name"));
                row.add(rs.getString("mobile"));
                row.add(rs.getString("net_amount"));
                row.add(rs.getString("date"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) deposit_table1.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    
    }
    
    private void bill_DepositeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bill_DepositeMouseClicked
        if(roleUser.equals("Admin")){
            jTabbedPane1.setSelectedIndex(5);
            showdeposittable();
            Showbill();
        }
        
        
    }//GEN-LAST:event_bill_DepositeMouseClicked

    private void CatCutomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CatCutomerMouseClicked
        jTabbedPane1.setSelectedIndex(6);
        showCustomerTable();
    }//GEN-LAST:event_CatCutomerMouseClicked

    private void AccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountMouseClicked
//        Inventory.roleUser="";
//        LogIn log= new LogIn();
//        log.setVisible(true);
//        //setVisible(false);
//        log.account();
        if(roleUser.equals("Admin")){
            loadUserAccount();
            jTabbedPane1.setSelectedIndex(8);
        }
    

        
 
        
    }//GEN-LAST:event_AccountMouseClicked

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        
        int des = JOptionPane.showConfirmDialog(null, "Do you want to LogOut?","LogOut Confirmatoin", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if(des==0){
            dispose();
            LogIn log=new LogIn();
            log.show();
        }
       
    }//GEN-LAST:event_LogoutMouseClicked

    private void invTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_invTotalActionPerformed

    private void jDateChooser1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyReleased
      
    }//GEN-LAST:event_jDateChooser1KeyReleased

    private void invProductComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_invProductComboItemStateChanged
        String Query = "SELECT * FROM add_stock WHERE product_id='"+String.valueOf(invProductCombo.getSelectedItem())+"'";
        try {
            connection();
            Statement state=connec.createStatement();
           ResultSet rs = state.executeQuery(Query);
            while (rs.next()) {
                invproname.setText(rs.getString("product_name"));
                InvStock.setText(rs.getString("stock"));
                InvPrice.setText(rs.getString("price"));
                stockProduct=Double.parseDouble(rs.getString("stock"));
            }
        } catch (ClassNotFoundException|SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }     
    }//GEN-LAST:event_invProductComboItemStateChanged

    private void invquantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invquantityKeyReleased
        double price,Quantity, total, newStock, stock1;
        Quantity = Double.parseDouble(invquantity.getText());
        stock1=Double.parseDouble(InvStock.getText());
        if(invquantity.getText().isEmpty()){
            
            
        }else if(Quantity>stock1){
         JOptionPane.showMessageDialog(null, "Limited Stock");
            
        }else{
        //stockProduct = Integer.parseInt(jTextField7.getText());
        price = Double.parseDouble(InvPrice.getText()); 
        Quantity = Double.parseDouble(invquantity.getText());
        
        total = price * Quantity;
        invTotal.setText(Double.toString(total));
        newStock = stockProduct - Quantity;
        InvStock.setText(Double.toString(newStock)); 
        }
        
    }//GEN-LAST:event_invquantityKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       invproname.setText("");
       invProductCombo.setSelectedItem("");
       InvStock.setText("");
       invquantity.setText("");
       InvPrice.setText("");
       invTotal.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed
    
    
    /*
    public void PieChart() {

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();
        try {
            dbCon.myDriver();
            String query = "select book_name, count(*) as issue_count from issue_book_details group by book_id";
            stm = dbCon.con.createStatement();
            rs = stm.executeQuery(query);
            while (rs.next()) {
                barDataset.setValue(rs.getString("book_name"), new Double(rs.getDouble("issue_count")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Issue Book Details ", barDataset, false, true, false);//explain

        PiePlot piePlot = (PiePlot) piechart.getPlot();

        //changing pie chart blocks colors
        piePlot.setSectionPaint("", new Color(255, 255, 102));
        piePlot.setSectionPaint("", new Color(102, 255, 102));
        piePlot.setSectionPaint("", new Color(255, 102, 153));
        piePlot.setSectionPaint("", new Color(0, 204, 204));

        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelPiChart.removeAll();
        panelPiChart.add(barChartPanel, BorderLayout.CENTER);
        panelPiChart.validate();
    }
    
    */
    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart chart = ChartFactory.createBarChart("Annual Sales By Amount","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        jPanel26.removeAll();
        jPanel26.add(barpChartPanel, BorderLayout.CENTER);
        jPanel26.validate();
    }
    
    public void showLineChart(){
        //create dataset for the graph
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Annual Sales","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
       // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.white);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(204,0,51);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        jPanel2.removeAll();
        jPanel2.add(lineChartPanel, BorderLayout.CENTER);
        jPanel2.validate();
    }

    public void showHistogram(){
        
         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
        
         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
            XYPlot plot= chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
        chartimport.removeAll();
        chartimport.add(barpChartPanel2, BorderLayout.CENTER);
        chartimport.validate();
    }
    
    public void showPieChart(){
      //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      try {
            connection();
            String query = "select product_name1, count(*) as issue_count from invoice group by product_id1";
            result= state.executeQuery(query);
            while (result.next()) {
                barDataset.setValue(result.getString("product_name1"), new Double(result.getDouble("issue_count")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
      
       //create chart
       JFreeChart piechart = ChartFactory.createPieChart("Product Sales Parcentage",barDataset, false,true,false);
       //explain
        PiePlot piePlot =(PiePlot) piechart.getPlot();
        piePlot.setSectionPaint("IPhone 5s", new Color(255,255,102));
        piePlot.setSectionPaint("SamSung Grand", new Color(102,255,102));
        piePlot.setSectionPaint("MotoG", new Color(255,102,153));
        piePlot.setSectionPaint("Nokia Lumia", new Color(0,204,204));
        piePlot.setBackgroundPaint(Color.white);
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelPiChart.removeAll();
        panelPiChart.add(barChartPanel, BorderLayout.CENTER);
        panelPiChart.validate();
    }
    void topSalingPro(){
        try {
            connection();
            String tspquery="SELECT `products_id`,`products_name`, COUNT(`quantity`) as 'Order Count',sum(quantity) as 'Total Sales' FROM new_invoice GROUP BY products_name ORDER by COUNT(quantity) DESC";
            ResultSet rs= state.executeQuery(tspquery);
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Product Name");
            header.add("Order Count");
            header.add("Total Sales Item");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("products_id"));
                row.add(rs.getString("products_name"));
                row.add(rs.getString("Order Count"));
                row.add(rs.getString("Total Sales"));
                data.add(row);
            }
            DefaultTableModel model = (DefaultTableModel) topSalingProduct.getModel();
            model.setDataVector(data, header);
            
        
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportsMouseClicked
        jTabbedPane1.setSelectedIndex(7);
        showPieChart();
        showBarChart();
        showLineChart();
        topSalingPro();
    }//GEN-LAST:event_ReportsMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            connection();
            String userName, Email, mobile, role, password;
            userName = AccName.getText();
            Email = AccEmail.getText();
            role = CreateRole.getSelectedItem().toString();
            password = accPassword.getText();
            mobile = AccMobile.getText();
            String quary = "INSERT INTO login_details (user_name,email, mobile, role, password) VALUES ('" + userName + "','" + Email + "','" + mobile + "','" + role + "','" + password + "')";
            state.executeUpdate(quary);
            JOptionPane.showMessageDialog(null, "Your User Account Save Successfully!");
            state.close();
            connec.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadUserAccount();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        LogIn log=new LogIn(); 
        log.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed
    
    void selectTabeleItem(){
        try {
            String sqls = "SELECT `product_id`,`product_name`,`stock`,`price` FROM `add_stock`";
            Statement state=connec.createStatement();
            state.executeQuery(sqls);
            int index = showProducts.getSelectedRow();
            
            jTextField1.setText(showProducts.getValueAt(index, 1).toString());
            jTextField10.setText(showProducts.getValueAt(index, 2).toString());
            jTextField3.setText(showProducts.getValueAt(index, 3).toString());        
            jTextField5.setText(showProducts.getValueAt(index, 4).toString());
        } catch (SQLException exp) {
            System.out.println("exp");
        }
  
    
    }
    private void showProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showProductsMouseClicked
        selectTabeleItem();
    }//GEN-LAST:event_showProductsMouseClicked
    
    void deleteStock(){
        try {
            String sL="SL";
            connection();
            int index = showProducts.getSelectedRow();
            String query="DELETE FROM `add_stock` WHERE "+sL+"="+showProducts.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Product Delete Successfully");  
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showStock();
    }
    
    
    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        int des = JOptionPane.showConfirmDialog(null, "Do you want to Delete this Product?","Delete Cinfirmation Confirmatoin", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if(des==0){
             deleteStock();
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
      
        int index = showProducts.getSelectedRow();
        try {
            connection();
            String pId=jTextField1.getText();
            String pName=jTextField10.getText();
            Double Stock=Double.parseDouble(jTextField3.getText());
            String Category=String.valueOf(jComboBox2.getSelectedItem());
            Double Price=Double.parseDouble(jTextField5.getText());
            String SName=SallNCombo.getSelectedItem().toString();
            String SCountry=SellerCountry.getSelectedItem().toString();
            String Brand=PBrand.getSelectedItem().toString();
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser2.getDate();
            String StockDate=sdp.format(gdate);
            
            int des = JOptionPane.showConfirmDialog(null, "Do you want to Edit this Product?","Edit Cinfirmation Confirmatoin", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if(des==0){
            String UpQuery="UPDATE `add_stock` SET `product_id`='"+pId+"',`product_name`='"+pName+"',`stock`='"+Stock+"',`category`='"+Category+"',`price`="+Price+",`Seller_name`='"+SName+"',`Seller_Country`='"+SCountry+"',`Brand`='"+Brand+"',`Date`='"+StockDate+"' WHERE SL="+showProducts.getValueAt(index, 0);
            state.executeUpdate(UpQuery);
            JOptionPane.showMessageDialog(null, "Your Product Updated Successfully!");
        
        }
          
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showStock();
    }//GEN-LAST:event_jButton29ActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
       DefaultTableModel dm= (DefaultTableModel) bill.getModel();
       int removeitem=bill.getSelectedRow();
       if(removeitem>=0){
           dm.removeRow(removeitem);
       }
    }//GEN-LAST:event_removeActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        try {
            connection();
            int index = jTable2.getSelectedRow();
            String query="DELETE FROM `add_category` WHERE id="+jTable2.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Category Delete Successfully");
            loadCategoryTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        String Query = "SELECT * FROM customer WHERE name='"+String.valueOf(jComboBox6.getSelectedItem())+"'";
        
        
        try {
            connection();
            Statement state=connec.createStatement();
           ResultSet rs = state.executeQuery(Query);
            while (rs.next()) {
                
                cusIdDepo.setText(rs.getString("id"));
                custypedepo.setText(rs.getString("designation"));
                cusadddepo.setText(rs.getString("address"));
                getTotalAmount();
            }
            
            jLabel7.setText(jComboBox6.getSelectedItem().toString());
            
        } catch (ClassNotFoundException|SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
        getTotalAmount();
    }//GEN-LAST:event_jComboBox6ItemStateChanged
    
    void getDue(){
//        String invcalQuery="select SUM(total_price) AS TotalPrice from new_invoice where invoice_id="+invid;
//        ResultSet rs1= state.executeQuery(invcalQuery);
//        rs1 .next();
//        int count1 = rs1 .getInt("TotalPrice");
//        rs1 .close();
//        total.setText(String.valueOf(count1));
    
    
    
    }
    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        jComboBox6.setSelectedItem("");
        cusIdDepo.setText("");
        deposittext.setText("");
        custypedepo.setText("");
        cusadddepo.setText("");
        jLabel11.setText("");
        jLabel114.setText("");
        jLabel127.setText("");
        
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        try {
            connection();
            String brandName=brand_name.getText();
            String bDescription=barndDescription.getText();
            String braninsQuery="INSERT INTO `add_brand`(`brand_name`, `brand_description`) VALUES ('"+brandName+"','"+bDescription+"')";
            state.executeUpdate(braninsQuery);
            JOptionPane.showMessageDialog(null, "Your Brand Save Successfully!");
            state.close();
            connec.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MouseClicked
    void loadBrandTable(){
        try {
            connection();
            String clgshowQuery="select id,brand_name,brand_description from add_brand";
            ResultSet rs= state.executeQuery(clgshowQuery);
            
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Brand Name");
            header.add("Description");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("brand_name"));
                row.add(rs.getString("brand_description"));
                data.add(row);
            }
            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        loadBrandTable();
    }//GEN-LAST:event_jButton36ActionPerformed
    
    void updateBrand(){
        try {
            connection();
            int index = jTable5.getSelectedRow();
            String BName=jTable5.getValueAt(index, 1).toString();
            String Bdescrip=jTable5.getValueAt(index, 2).toString();
            String query="UPDATE add_brand SET brand_name='"+BName+"',brand_description='"+Bdescrip+"' WHERE id="+jTable5.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Brand Update Successfully");
            loadCategoryTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        updateBrand();
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable6MouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        try {
            connection();
            String sellerName=SellName.getText();
            String mobile=sellMobile.getText();
            String factroy=Sellfactory.getText();
            String country=sellcounCombo.getSelectedItem().toString();
            String clginsQuery="INSERT INTO `add_seller`(`saller_name`, `mobile`, `factory_name`, `country`) VALUES ('"+sellerName+"','"+mobile+"','"+factroy+"','"+country+"')";
            state.executeUpdate(clginsQuery);
            JOptionPane.showMessageDialog(null, "Seller info Save Successfully!");
            state.close();
            connec.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showSellerTable();
    }//GEN-LAST:event_jButton30ActionPerformed

    private void SallNComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SallNComboItemStateChanged
 
    }//GEN-LAST:event_SallNComboItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        try {
            connection();
            int index = jTable2.getSelectedRow();
            String CName=jTable2.getValueAt(index, 1).toString();
            String Cdescrip=jTable2.getValueAt(index, 2).toString();
            String query="UPDATE add_category SET category_name='"+CName+"',description='"+Cdescrip+"' WHERE id="+jTable2.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Category Update Successfully");
            loadCategoryTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton40ActionPerformed
    
    void deleteSeller(){
        try {
            connection();
            int index = jTable1.getSelectedRow();
            String query="DELETE FROM `add_seller` WHERE Id="+jTable1.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Seller Delete Successfully");  
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showSellerTable();
    }
    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        deleteSeller();
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        try {
            connection();
            int index = jTable1.getSelectedRow();
            String SName=jTable1.getValueAt(index, 1).toString();
            String Mobile=jTable1.getValueAt(index, 2).toString();
            String Factory=jTable1.getValueAt(index, 3).toString();
            String Country=jTable1.getValueAt(index, 4).toString();
            String query="UPDATE `add_seller` SET `saller_name`='"+SName+"',`mobile`='"+Mobile+"',`factory_name`='"+Factory+"',`country`='"+Country+"' WHERE Id="+jTable1.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Category Update Successfully");
            showSellerTable();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser1.getDate();
            
        try {
            DefaultTableModel model = (DefaultTableModel) bill.getModel();  
            connection();
                String invDate=sdp.format(gdate);
                int invid=Integer.parseInt(invno1.getText());
                String cName=jComboBox6.getSelectedItem().toString();
            
                for(int i = 0; i < model.getRowCount(); i++){
                String pid =model.getValueAt(i, 0).toString();
                String pname = model.getValueAt(i, 1).toString();
                int quentiry = Integer.valueOf(model.getValueAt(i, 3).toString());
                Double totalPrice = Double.valueOf(model.getValueAt(i, 4).toString());
                
               
                Statement state=connec.createStatement();
                String sqlQuery="INSERT INTO `new_invoice`(`invoice_id`, `products_id`, `products_name`, `quantity`, `total_price`, `client_name`, `date`) VALUES ("+invid+",'"+pid+"','"+pname+"',"+quentiry+", "+totalPrice+",'"+cName+"','"+invDate+"')";
                //state.addBatch(sqlQuery);
                state.executeUpdate(sqlQuery);
                
                Statement state1=connec.createStatement();
                String getStock="SELECT stock FROM add_stock WHERE product_id='"+model.getValueAt(i, 0)+"'";
               
                ResultSet rs=state1.executeQuery(getStock);
                //System.out.println("Stock :"+getStock);
                int newStock=0;
                while(rs.next()){
                    newStock=rs.getInt("stock");
                }
                
                Statement state2=connec.createStatement();
                int newStock1=newStock-quentiry;
                String sqlQuery1="UPDATE `add_stock` SET `stock`="+newStock1+" WHERE `product_id`='"+model.getValueAt(i, 0)+"'";
                state2.executeUpdate(sqlQuery1);    
            }
                String invcalQuery="select SUM(total_price) AS TotalPrice from new_invoice where invoice_id="+invid;
                ResultSet rs1= state.executeQuery(invcalQuery);
                rs1 .next();
                Double count1 = rs1 .getDouble("TotalPrice");
                rs1 .close();
                total.setText(String.valueOf(count1));
            JOptionPane.showMessageDialog(null, "Your Invoice Save Succesfully");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String pId=invProductCombo.getSelectedItem().toString();
        String pName = invproname.getText();
        int Quentity=Integer.parseInt(invquantity.getText());
        Double Price=Double.parseDouble(InvPrice.getText());
        Double totalPrice=Double.parseDouble(invTotal.getText());
        Object[] row={pId,pName,Price,Quentity,totalPrice};
        DefaultTableModel model=(DefaultTableModel) bill.getModel();
        model.addRow(row);
    }//GEN-LAST:event_jButton12ActionPerformed
    void showdepositTable(){
        try {
            connection();
            String invshowQuery="select * from invoice";
            ResultSet rs= state.executeQuery(invshowQuery);
            Vector<String> header = new Vector<String>();
            header.add("INV NO");
            header.add("ID");
            header.add("Name");
            header.add("Stock");
            header.add("Quentity");
            header.add("Price");
            header.add("Total");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()){
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("inv_id"));
                row.add(rs.getString("product_id1"));
                row.add(rs.getString("product_name1"));
                row.add(rs.getString("stock1"));
                row.add(rs.getString("quentity"));
                row.add(rs.getString("price1"));
                row.add(rs.getString("total_price"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) deposit_table.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    void getTotalAmount(){
            
            
        try {
            connection();
                String custName=jComboBox6.getSelectedItem().toString();
                String getdueQuery="select SUM(total_price) AS Due from new_invoice where                  client_name='"+custName+"'";
                ResultSet rs1= state.executeQuery(getdueQuery);
                rs1 .next();
                int count1 = rs1 .getInt("Due");
                rs1 .close();
                duelbl.setText(String.valueOf(count1));
                jLabel11.setText(String.valueOf(count1));
                
                String getDipoQuery="select SUM(deposit) AS Deposit from deposit where                  custommer_name='"+custName+"'";
                ResultSet rs2= state.executeQuery(getDipoQuery);
                rs2 .next();
                int count = rs2 .getInt("Deposit");
                rs1 .close();
                jLabel114.setText(String.valueOf(count));
                
                int Totaldue=count1-count;
                jLabel127.setText(String.valueOf(Totaldue));
             
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    
    
    }
    
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        try {
            connection();
            
            //Deposit Save Query
            String custName=jComboBox6.getSelectedItem().toString();
            int id= Integer.parseInt(cusIdDepo.getText());
            int dueamm=Integer.parseInt(duelbl.getText());
            int deposit=Integer.parseInt(deposittext.getText());
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser3.getDate();
            String StockDate=sdp.format(gdate);
            
            String clginsQuery="INSERT INTO deposit(id, custommer_name, due, deposit, date) VALUES ("+id+",'"+custName+"',"+dueamm+","+deposit+",'"+StockDate+"')";
            state.executeUpdate(clginsQuery);
            JOptionPane.showMessageDialog(null, "Deposit added Successfully!");
            state.close();
            connec.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showdeposittable();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jLabel130MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel130MouseClicked
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser4.getDate();
            String dateto=sdp.format(gdate);
            
            Date gdate1=(Date) jDateChooser5.getDate();
            String datefrom=sdp.format(gdate1);
        
        try {
            connection();
            String querybydate="SELECT `id`, `custommer_name`, `due`, `deposit`, `date` FROM `deposit` WHERE `date` BETWEEN '"+dateto+"' AND '"+datefrom+"'";
            ResultSet rs= state.executeQuery(querybydate);
            
            Vector<String> header = new Vector<String>();
            header.add("ID");
            header.add("Customer Name");
            header.add("Total Amount");
            header.add("Deposit");
            header.add("Date");

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("id"));
                row.add(rs.getString("custommer_name"));
                row.add(rs.getString("due"));
                row.add(rs.getString("deposit"));
                row.add(rs.getString("date"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) deposit_table.getModel();
            model.setDataVector(data, header);
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jLabel130MouseClicked

    private void topSalingProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topSalingProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_topSalingProductMouseClicked

    private void BillpageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BillpageMouseClicked
        jTabbedPane1.setSelectedIndex(9);
    }//GEN-LAST:event_BillpageMouseClicked

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try {
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser6.getDate();
            String invDate=sdp.format(gdate);
            connection();
            String invidshow="SELECT `invoice_id`FROM `new_invoice` WHERE `date`='"+invDate+"'";
            ResultSet rs= state.executeQuery(invidshow);
            
            Vector<String> header = new Vector<String>();
            header.add("INVOICE NO");
            
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("invoice_id"));
                data.add(row);
            }
            
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setDataVector(data, header);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTextField17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField17KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17KeyReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
       try {
            // TODO add your handling code here:
            
            String billNo=jTextField6.getText();
            
            if(billNo.equals("")){
             JOptionPane.showMessageDialog(rootPane, "Please Enter Bill no");
            
            }else{
                connection();
                jLabel173.setText(billNo);
                
                String invshowQuery="select * from new_invoice where invoice_id="+billNo+"";
                ResultSet rs= state.executeQuery(invshowQuery);
                Vector<String> header = new Vector<String>();
                //header.add("BIll NO");
                header.add("ID");
                header.add("Name");
                //header.add("Stock");
                //header.add("Price");
                header.add("Quentity");
                header.add("Total");

                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> row = new Vector<Object>();
                    //row.add(rs.getString("inv_id"));
                    row.add(rs.getString("products_id"));
                    row.add(rs.getString("products_name"));
                    //row.add(rs.getString("stock1"));
                    //row.add(rs.getString("price1"));
                    row.add(rs.getString("quantity"));
                    row.add(rs.getString("total_price"));
                    data.add(row);
                    jLabel172.setText(rs.getString("client_name"));
                    lbldate1.setText(rs.getString("date"));
                }

                DefaultTableModel model = (DefaultTableModel) bill1.getModel();
                model.setDataVector(data, header);
                
                //set Text with Text field
                String invnamesetQuery="SELECT  `shop_name`, `mobile`, `address` FROM `customer` WHERE `name`='"+jLabel172.getText()+"'";
                ResultSet rs2= state.executeQuery(invnamesetQuery);
                
                while(rs2.next()){
                    shopname1.setText(rs2.getString("shop_name"));
                    address1.setText(rs2.getString("mobile"));
                    Mobile1.setText(rs2.getString("address"));
                    
                }
                //invoice calculation
                String invcalQuery="select SUM(total_price) AS TotalPrice from new_invoice where invoice_id="+billNo+"";
                ResultSet rs1= state.executeQuery(invcalQuery);
                rs1 .next();
                int count1 = rs1 .getInt("TotalPrice");
                rs1 .close();
                total1.setText(String.valueOf(count1));
            }  
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jTextField6KeyReleased

    private void invno1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invno1KeyReleased
        jLabel174.setText(invno1.getText());
    }//GEN-LAST:event_invno1KeyReleased

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
       printMethod(jpinvoice);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        printMethod(jpinvoice1);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");

        job.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.LANDSCAPE);
                pf.equals(pf);
                
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.82, 0.82);
                deposit_table.print(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
                                 
        try {
            int invoiceNo=Integer.parseInt(jLabel174.getText());
            String custName=jComboBox1.getSelectedItem().toString();
            String mNumber=Mobile.getText();
            int totalAmmount=Integer.parseInt(total.getText());
            int discount=Integer.parseInt(jTextField15.getText());
            Double netAmmount=Double.parseDouble(net.getText());
            SimpleDateFormat sdp=new SimpleDateFormat("YYYY-MM-dd");
            Date gdate=(Date) jDateChooser1.getDate();
            String invDate=sdp.format(gdate);

            connection();
            Statement state=connec.createStatement();
            String sqlQuery="INSERT INTO `bill`(`invoice_no`, `customer_name`, `mobile`, `total_ammount`, `discount`, `net_amount`, `date`) VALUES ("+invoiceNo+",'"+custName+"','"+mNumber+"',"+totalAmmount+","+discount+","+netAmmount+",'"+invDate+"')";
            state.executeUpdate(sqlQuery);

            JOptionPane.showMessageDialog(null, "Your Invoice Ammount save Successfully");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        try {
            connection();
            int index = customer.getSelectedRow();
            String cust_id=customer.getValueAt(index, 0).toString();
            String customer_code=customer.getValueAt(index, 1).toString();
            String client_name=customer.getValueAt(index, 2).toString();
            String customer_designation=customer.getValueAt(index, 3).toString();
            
            String shop_name=customer.getValueAt(index, 4).toString();
            String custMobile=customer.getValueAt(index, 5).toString();
            String cust_address=customer.getValueAt(index, 6).toString();
            
            String custUpQuery="UPDATE `customer` SET`cust_code`='"+customer_code+"',`name`='"+client_name+"',`designation`='"+customer_designation+"',`shop_name`='"+shop_name+"',`mobile`='"+custMobile+"',`address`='"+cust_address+"' WHERE id="+cust_id+"";
            state.executeUpdate(custUpQuery);
            JOptionPane.showMessageDialog(null, "Your Customer Updated Successfully!");
            state.close();
            connec.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        showCustomerTable();
    }//GEN-LAST:event_jButton23ActionPerformed
    
    void deleteCustomer(){
        try {
            connection();
            int index = customer.getSelectedRow();
            String query="DELETE FROM `customer` WHERE id="+customer.getValueAt(index, 0);
            Statement state=connec.createStatement();
            state.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Your Customer Delete Successfully");  
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
       deleteCustomer();
       showCustomerTable();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        jTabbedPane1.setSelectedIndex(10);
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
      jTabbedPane1.setSelectedIndex(11);
    }//GEN-LAST:event_jButton43ActionPerformed

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
            java.util.logging.Logger.getLogger(Inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AccEmail;
    private javax.swing.JTextField AccMobile;
    private javax.swing.JTextField AccName;
    private javax.swing.JPanel Account;
    private javax.swing.JPanel Accountpan;
    private javax.swing.JPanel AddCategory;
    private javax.swing.JPanel AddCategory1;
    private javax.swing.JPanel AddStock;
    private javax.swing.JPanel Billpage;
    private javax.swing.JPanel Billpan;
    private javax.swing.JPanel CatCutomer;
    private javax.swing.JComboBox<String> CreateRole;
    private javax.swing.JPanel Customerpan;
    private javax.swing.JPanel DashBoard;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JTextField InvPrice;
    private javax.swing.JTextField InvStock;
    private javax.swing.JPanel Invoicepan;
    private javax.swing.JPanel Logout;
    private javax.swing.JLabel Mobile;
    private javax.swing.JLabel Mobile1;
    private javax.swing.JComboBox<String> PBrand;
    private javax.swing.JPanel Reportpan;
    private javax.swing.JPanel Reports;
    private javax.swing.JComboBox<String> SallNCombo;
    private javax.swing.JTextField SellName;
    private javax.swing.JComboBox<String> SellerCountry;
    private javax.swing.JPanel Sellerinfo;
    private javax.swing.JTextField Sellfactory;
    private javax.swing.JTextField accPassword;
    private javax.swing.JTable accTable;
    private javax.swing.JPanel addCategory;
    private javax.swing.JPanel addStockPan;
    private javax.swing.JLabel address;
    private javax.swing.JLabel address1;
    private javax.swing.JTextArea barndDescription;
    private javax.swing.JTable bill;
    private javax.swing.JTable bill1;
    private javax.swing.JPanel bill_Deposite;
    private javax.swing.JTextField brand_name;
    private javax.swing.JPanel chartimport;
    private javax.swing.JTextField cliname;
    private javax.swing.JPanel createAccount;
    private javax.swing.JTextArea cstAddress;
    private javax.swing.JComboBox<String> cusDesignation;
    private javax.swing.JTextField cusIdDepo;
    private javax.swing.JLabel cusadddepo;
    private javax.swing.JTextField custCode;
    private javax.swing.JTextField custmobileNo;
    private javax.swing.JTable customer;
    private javax.swing.JTextField custshopName;
    private javax.swing.JLabel custypedepo;
    private javax.swing.JPanel deposit;
    private javax.swing.JTable deposit_table;
    private javax.swing.JTable deposit_table1;
    private javax.swing.JTextField deposittext;
    private javax.swing.JLabel duelbl;
    private javax.swing.JComboBox<String> invProductCombo;
    private javax.swing.JTextField invTotal;
    private javax.swing.JTextField invno1;
    private javax.swing.JPanel invoice;
    private javax.swing.JTextField invproname;
    private javax.swing.JTextField invquantity;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private com.toedter.calendar.JDateChooser jDateChooser6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JPanel jpinvoice;
    private javax.swing.JPanel jpinvoice1;
    private javax.swing.JLabel lbldate;
    private javax.swing.JLabel lbldate1;
    private javax.swing.JLabel namelbl;
    private javax.swing.JLabel net;
    private javax.swing.JLabel net1;
    private javax.swing.JPanel newaddStrock;
    private javax.swing.JPanel panelPiChart;
    private javax.swing.JLabel per;
    private javax.swing.JLabel per1;
    private javax.swing.JButton remove;
    private javax.swing.JPanel searchProduct;
    private javax.swing.JTextField sellMobile;
    private javax.swing.JComboBox<String> sellcounCombo;
    private javax.swing.JLabel shopname;
    private javax.swing.JLabel shopname1;
    private javax.swing.JTable showProducts;
    private javax.swing.JPanel showStock;
    private javax.swing.JTable topSalingProduct;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    // End of variables declaration//GEN-END:variables
}
