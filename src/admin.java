import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.sql.*;
import javax.swing.event.*;
/*
 * Created by JFormDesigner on Tue Apr 14 14:17:42 IST 2020
 */


/**
 * @author rakesh
 */
public class admin extends JFrame {
    public admin() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    public static void main(String[] args)
    {
        admin ad=new admin();
        ad.setVisible(true);
    }
    private void button2MouseClicked(MouseEvent e) {
        int ok=0;
        if (java.util.Arrays.equals(passwordField1.getPassword(),passwordField2.getPassword())) {
            Statement stmt = null;
            Connection conn = null;
            int count = 0;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:"+textField4.getText(),textField2.getText(),textField3.getText());
                //("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                stmt = conn.createStatement();
                System.out.println("this is great");
                ok=1;
            } catch (Exception x) {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null, x);
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                    if (stmt != null)
                        stmt.close();
                } catch (Exception ex) {
                }

            }
        }
        else
        {
            System.out.println("password miss match");
        }
        if(ok==1) {
            //try

            //if (key == 0) {
                Statement stmt = null;
                Connection conn = null;
                //System.out.println(qr);
                String qr = "insert into plogin values ('" + textField1.getText() + "','" + new String(passwordField1.getPassword()) + "','" +
                        textField4.getText() + "','" +
                        textField2.getText() + "','" +
                        textField3.getText() + "','" +
                        comboBox1.getSelectedItem() + "')";
                System.out.println(qr);
                //int count = 0;
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
                    stmt = conn.createStatement();
                    stmt.execute(qr);
                    JOptionPane.showMessageDialog(null, "User created!!");
                } catch (Exception x) {
                    //System.out.println(x);
                    JOptionPane.showMessageDialog(null, x);
                } finally {
                    try {
                        if (conn != null)
                            conn.close();
                        if (stmt != null)
                            stmt.close();
                    } catch (Exception ex) {
                    }

                }


        }
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        passwordField2.setText("");
        passwordField1.setText("");

    }

    private void panel1MouseEntered(MouseEvent e) {
    }

    private void spinner1FocusGained(FocusEvent e) {
       // SpinnerNumberModel model1=new SpinnerNumberModel(1,0,5,1);
       // spinner1.setModel(model1);
    }

    private void spinner1StateChanged(ChangeEvent e) {

    }

    private void spinner1MouseEntered(MouseEvent e) {
        // TODO add your code here
        //SpinnerNumberModel model1=new SpinnerNumberModel(1,0,5,1);
        //spinner1.setModel(model1);
    }

    private void spinner1InputMethodTextChanged(InputMethodEvent e) {
       // SpinnerNumberModel model1=new SpinnerNumberModel(1,0,5,1);
       // spinner1.setModel(model1);
    }

    private void textField4FocusLost(FocusEvent e) {
       // SpinnerNumberModel model1=new SpinnerNumberModel(1,0,5,1);
       // spinner1.setModel(model1);
    }

    private void textField4FocusGained(FocusEvent e) {

    }

    private void thisWindowOpened(WindowEvent e) {
        String qr="select * from db_session";
        //System.out.println(qr);
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
            PreparedStatement pss=null;
            pss=connn.prepareStatement(qr);
            //System.out.println("try area");
            ResultSet rss=pss.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rss));
            connn.close();
            rss.close();
        }
        catch (Exception x) {
            JOptionPane.showMessageDialog(null, x);
        }
    }

    private void button1MouseClicked(MouseEvent e) {
        int row;
        Object sid;
        try {
            row = table1.getSelectedRow();
            sid = table1.getModel().getValueAt(row, 0);
            System.out.println(sid);
            new logs(sid).setVisible(true);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Select a row and click");
        }

        //System.out.println(qr);

    }

   

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - saravanan
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        passwordField1 = new JPasswordField();
        label3 = new JLabel();
        passwordField2 = new JPasswordField();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button2 = new JButton();
        label7 = new JLabel();
        comboBox1 = new JComboBox<>();
        scrollPane2 = new JScrollPane();
        scrollPane3 = new JScrollPane();
        panel2 = new JPanel();
        scrollPane4 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        var contentPane = getContentPane();

        //======== tabbedPane1 ========
        {

            //======== scrollPane1 ========
            {

                //======== panel1 ========
                {
                    panel1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel1MouseEntered(e);
                        }
                    });
                    panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
                    swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border
                    . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
                    ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel1. getBorder
                    ( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
                    .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
                    ( ); }} );

                    //---- label1 ----
                    label1.setText("USERNAME");

                    //---- label2 ----
                    label2.setText("PASSWORD");

                    //---- label3 ----
                    label3.setText("RE-ENTER PASSWORD");

                    //---- label4 ----
                    label4.setText("ORACLE USER NAME");

                    //---- label5 ----
                    label5.setText("ORACLE PASSWORD");

                    //---- label6 ----
                    label6.setText("CONNECTION STRING");

                    //---- textField4 ----
                    textField4.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            textField4FocusGained(e);
                        }
                        @Override
                        public void focusLost(FocusEvent e) {
                            textField4FocusLost(e);
                        }
                    });

                    //---- button2 ----
                    button2.setText("ADD");
                    button2.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button2MouseClicked(e);
                        }
                    });

                    //---- label7 ----
                    label7.setText("USER TYPE");

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                        "admin",
                        "normal"
                    }));

                    GroupLayout panel1Layout = new GroupLayout(panel1);
                    panel1.setLayout(panel1Layout);
                    panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label6, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(label7, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                .addGap(51, 51, 51)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(textField4, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(textField3, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(textField2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(passwordField2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(passwordField1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(textField1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                        .addComponent(button2, GroupLayout.Alignment.LEADING))
                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(320, Short.MAX_VALUE))
                    );
                    panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(button2)
                                .addGap(20, 20, 20))
                    );
                }
                scrollPane1.setViewportView(panel1);
            }
            tabbedPane1.addTab("ADD USER", scrollPane1);

            //======== scrollPane2 ========
            {

                //======== scrollPane3 ========
                {

                    //======== panel2 ========
                    {

                        //======== scrollPane4 ========
                        {
                            scrollPane4.setViewportView(table1);
                        }

                        //---- button1 ----
                        button1.setText("DETAILS");
                        button1.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                button1MouseClicked(e);
                            }
                        });

                        GroupLayout panel2Layout = new GroupLayout(panel2);
                        panel2.setLayout(panel2Layout);
                        panel2Layout.setHorizontalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 611, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(button1)
                                    .addGap(0, 7, Short.MAX_VALUE))
                        );
                        panel2Layout.setVerticalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 141, Short.MAX_VALUE))
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addGap(222, 222, 222)
                                    .addComponent(button1)
                                    .addContainerGap(278, Short.MAX_VALUE))
                        );
                    }
                    scrollPane3.setViewportView(panel2);
                }
                scrollPane2.setViewportView(scrollPane3);
            }
            tabbedPane1.addTab("LOG", scrollPane2);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - saravanan
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JPasswordField passwordField1;
    private JLabel label3;
    private JPasswordField passwordField2;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button2;
    private JLabel label7;
    private JComboBox<String> comboBox1;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane3;
    private JPanel panel2;
    private JScrollPane scrollPane4;
    private JTable table1;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
