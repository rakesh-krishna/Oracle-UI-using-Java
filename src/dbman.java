import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
import net.proteanit.sql.DbUtils;
/*
 * Created by JFormDesigner on Sat Apr 11 15:51:47 IST 2020
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.miginfocom.swing.*;


/**
 * @author rakesh
 */
public class dbman extends JFrame {

    // This is krishna's variable list
    public String tab_col = "";
    public String header = "create table ";
    public int table_undercreation_flag = 0;
    //public String cre = "create table ";
    public int no_of_cols=0;
    public String ins_qr="";
    public int up_i=0;
    public String[] u_name=new String[100];
    public  String[] u_type=new String[100];
    public String uqry="";
    public int ldr1=1;
    
    static java.util.List<JLabel> cl_name = new ArrayList<JLabel>();
    static java.util.List<JTextField> cl_val= new ArrayList<JTextField>();
    static java.util.List<JComboBox> up_name=new ArrayList<JComboBox>();
    public int ins_index=0;
    


    public dbman() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    public static void main(String[] args) {
        dbman a = new dbman();
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        if(coltype.getSelectedItem().equals("varchar")&&len.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"enter the lenght of varchar");
        }
        else {
            if (table_undercreation_flag == 1)
                tab_col = tab_col + ",\n";
            table_undercreation_flag = 1;
            if ("".equals(len.getText())) {
                if (!"null".equals(contype.getSelectedItem())) {
                    tab_col = tab_col + colname.getText() + " " + coltype.getSelectedItem() + " " + contype.getSelectedItem();
                } else {
                    tab_col = tab_col + colname.getText() + " " + coltype.getSelectedItem();
                }
            } else {
                if (!"null".equals(contype.getSelectedItem())) {
                    tab_col = tab_col + colname.getText() + " " + coltype.getSelectedItem() + "(" + len.getText() + ") " + contype.getSelectedItem();
                } else {
                    tab_col = tab_col + colname.getText() + " " + coltype.getSelectedItem() + "(" + len.getText() + ")";
                }
            }
            textPane1.setText(tab_col);
            colname.setText("");
            coltype.setSelectedIndex(0);
            len.setText("");
            contype.setSelectedIndex(0);
            System.out.println("This is the message" + tab_col);
        }
    }

    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here

        header = header + tabname.getText() + " (\n" + tab_col + " )";
        System.out.println(header);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(header);
            ResultSet rs = ps.executeQuery();
            conn.close();
            rs.close();
            log.logg("TABLE CREATION","success","table "+tabname.getText()+" created");
        } catch (Exception x) {
            //System.out.println(x);
            JOptionPane.showMessageDialog(null, x);
            log.logg("TABLE CREATION","failed",String.valueOf(x));
        }
        JOptionPane.showMessageDialog(null, tabname.getText() + " table created successfully");
        textPane1.setText("");
        tabname.setText("");
        table_undercreation_flag=0;
        tab_col="";
        header="create table ";
    }

    private void button3ActionPerformed(ActionEvent e) {
        // TODO add your code here
        String qur = "drop ";
        qur = qur + comboBox1.getSelectedItem() + " " + textField1.getText();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(qur);
            ResultSet rs = ps.executeQuery();
            conn.close();
            rs.close();
            log.logg("DROP","SUCCESS",comboBox1.getSelectedItem()+" "+textField1.getText()+" dropped");
        } catch (Exception x) {
            //System.out.println(x);
            JOptionPane.showMessageDialog(null, x);
            log.logg("DROP","failed",String.valueOf(x));
        }
        JOptionPane.showMessageDialog(null, comboBox1.getSelectedItem() + " " + textField1.getText() + "successfully dropped");
        textField1.setText("");
    }

    private void textField2FocusLost(FocusEvent e) {
        // TODO add your code here
    }

    private void comboBox2ActionPerformed(ActionEvent e) {
        //String qry="alter table "+textField2.getText()+" ";
        if (comboBox2.getSelectedItem() == "add") {
            label8.setText("column name");
            label9.setText("column type");
        } else if (comboBox2.getSelectedItem() == "modify") {
            label8.setText("column name");
            label9.setText("new column type");
            label9.setVisible(true);
            textField4.setVisible(true);
        } else if (comboBox2.getSelectedItem() == "drop column") {
            label8.setText("column name");
            label9.setVisible(false);
            textField4.setVisible(false);
        } else if (comboBox2.getSelectedItem() == "rename column") {
            label8.setText("old column name");
            label9.setText("new column name");
            label9.setVisible(true);
            textField4.setVisible(true);
        } else if (comboBox2.getSelectedItem() == "rename to") {
            label8.setText("new table name");
            //label9.setText("column type");
            //label8.setText("column name");
            label9.setVisible(false);
            textField4.setVisible(false);
        }
    }

    private void button4ActionPerformed(ActionEvent e) {
        String qry = "alter table " + String.valueOf(comboBox12.getSelectedItem()).toUpperCase() + " " + comboBox2.getSelectedItem() + " " + textField3.getText() + " ";
        if (comboBox2.getSelectedItem() == "rename column") {
            qry = qry + " to " + textField4.getText();
        } else {
            qry = qry + " " + textField4.getText();
        }
        System.out.println(qry);
        textField4.setText("");
        textField3.setText("");
       // textField2.setText("");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            conn.close();
            rs.close();
            log.logg("AlTER","SUCCESS",comboBox12.getSelectedItem()+" table is altered");
        } catch (Exception x) {
            //System.out.println(x);
            JOptionPane.showMessageDialog(null, x);
            log.logg("AlTER","Failed",String.valueOf(x));
        }
        JOptionPane.showMessageDialog(null, "Table altered successfully");
    }

    private void textField5FocusLost(FocusEvent e) {
        String f = ftab.getText();
        //String s=stab.getText();
        f = f.toUpperCase();
        String[] cls = new String[100];
        String[] type = new String[100];
        int i = 0;
        String qry = "select column_name,data_type from all_tab_columns where table_name='";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(qry + f + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //int i=0;
                cls[i] = new String(rs.getString(1));
                type[i] = new String(rs.getString(2));
                i++;
            }
            conn.close();
            rs.close();
            if (i == 0) {
                JOptionPane.showMessageDialog(null, f + " this table doesn't exsist");
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, x);
        }
        fvar.setModel(new DefaultComboBoxModel<>(cls));

    }

    private void stabFocusLost(FocusEvent e) {
        String s = stab.getText();
        s = s.toUpperCase();
        String[] cls = new String[100];
        String[] type = new String[100];
        int i = 0;
        String qry = "select column_name,data_type from all_tab_columns where table_name='";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(qry + s + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //int i=0;
                cls[i] = new String(rs.getString(1));
                type[i] = new String(rs.getString(2));
                i++;
                //System.out.println(cls[i-1]+" type "+type[i-1]);
            }
            if (i == 0) {
                JOptionPane.showMessageDialog(null, s + " this table doesn't exsist");
            }
            conn.close();
            rs.close();
        } catch (Exception x) {
            JOptionPane.showMessageDialog(null, x);
        }

        svar.setModel(new DefaultComboBoxModel<>(cls));

    }

    private void button5ActionPerformed(ActionEvent e) {
        String qry = " alter table " + ftab.getText() + " add constraint " + textField6.getText() + " foreign key (" + fvar.getSelectedItem() + ") references " + stab + " (" + svar.getSelectedItem() + ")";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
            PreparedStatement ps = null;
            ps = conn.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            conn.close();
            rs.close();
            log.logg("FOREIGN KEY","SUCCESS","foreign key for table "+ftab.getText()+" called "+fvar.getSelectedItem());
        } catch (Exception x) {
            //System.out.println(x);
            JOptionPane.showMessageDialog(null, x);
            log.logg("FOREIGN KEY","FAILED",String.valueOf(x));
        }
        JOptionPane.showMessageDialog(null, "Foreign key added");
    }


        private void panel4MouseEntered(MouseEvent e) {
            String qry="select table_name from user_tables";
            String[] name=new String[100];
            int i=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qry);
                ResultSet rs = ps.executeQuery();
                //comboBox4.removeAllItems();
                while(rs.next())
                {

                    name[i]=new String(rs.getString(1));
                    comboBox4.addItem(name[i]);
                    i++;
                }
                conn.close();
                rs.close();
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }

            //comboBox4.setModel(new DefaultComboBoxModel<>(name));
            //comboBox4=new JComboBox(name);

        }

        private void button6KeyPressed(KeyEvent e) {

        }

        private void button6ActionPerformed(ActionEvent e) 
        {
        String qr="select * from "+((String)comboBox4.getSelectedItem()).toUpperCase();
        //System.out.println(qr);
        try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection connn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                    PreparedStatement pss=null;
                    pss=connn.prepareStatement(qr);
                    //System.out.println("try area");
                    ResultSet rss=pss.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rss));
                    connn.close();
                    rss.close();
            log.logg("VIEW","SUCCESS","table "+comboBox4.getSelectedItem()+" is viewed");
                }
                catch (Exception x) {
                    JOptionPane.showMessageDialog(null, x);
                    log.logg("VIEW","FAILED",String.valueOf(x));
                }
        }

        private void button6MouseExited(MouseEvent e) {
        /*
        int i=no_of_cols;
        String view="";
        String qr="select * from "+((String)comboBox4.getSelectedItem()).toUpperCase();
        //System.out.println(qr);
        try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection connn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                    PreparedStatement pss=null;
                    pss=connn.prepareStatement(qr);
                    //System.out.println("try area");
                    ResultSet rss=pss.executeQuery();
                    while(rss.next())
                    {
                        //System.out.println("the number"+i);
                        //System.out.println(rss.getString(1)+"then view is"+view);
                       for(int j=1;j<=i;j++)
                       {
                           //System.out.println(rss.getString(j)+"then view is"+view);
                           view=view+rss.getString(j);
                           view=view+"     ";
                       }
                       view=view+"\n";
                    }
                    connn.close();
                    rss.close();
                }
                catch (Exception x) {
                    JOptionPane.showMessageDialog(null, x);
                }
               textPane2.setText(textPane2.getText()+view);

        */
        }

        private void panel5MouseEntered(MouseEvent e) {
        String qry="select table_name from user_tables";
                    String[] name=new String[100];
                    int i=0;
                    try{
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                        PreparedStatement ps = null;
                        ps = conn.prepareStatement(qry);
                        ResultSet rs = ps.executeQuery();
                        //comboBox3.removeAllItems();
                        while(rs.next())
                        {
                            //int i=0;
                            name[i]=new String(rs.getString(1));
                            comboBox3.addItem(name[i]);
                            i++;
                        }
                        conn.close();
                        rs.close();
                    }
                    catch(Exception x)
                    {
                        //System.out.println(x);
                        JOptionPane.showMessageDialog(null,x);
                    }

                    //comboBox3.setModel(new DefaultComboBoxModel<>(name));
        }

        private void comboBox3ActionPerformed(ActionEvent e) {
                    /*System.out.println("it has entereed");
                    ipan.removeAll();
                    String qry = "select column_name from all_tab_columns where table_name='" + comboBox4.getSelectedItem() + "'";
                            String[] name = new String[100];
                            int i = 0;

                            try {
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                                PreparedStatement ps = null;
                                ps = conn.prepareStatement(qry);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {

                                    name[i] = new String(rs.getString(1));
                                    i++;
                                }
                                conn.close();
                                rs.close();
                            } catch (Exception x) {
                                JOptionPane.showMessageDialog(null, x);
                            }
                     for(int j=0;j<i;j++)
                     {
                         cl_name.add(new JLabel(name[j]));
                         cl_val.add(new JTextField());
                     }
                     GridBagConstraints tfcons=new GridBagConstraints();
                     GridBagConstraints lbcons=new GridBagConstraints();

                     for(int j=0;j<i;j++)
                     {
                         tfcons.gridx=7;
                         tfcons.gridy=i;

                         lbcons.gridx=5;
                         lbcons.gridy=i;

                         ipan.add(cl_name.get(j),lbcons);
                         ipan.add(cl_val.get(j),tfcons);
                     }         */
        }

        private void comboBox3ItemStateChanged(ItemEvent e) {


                    System.out.println("it has entereed");
                    ipan.removeAll();
                    
                    String qry = "select column_name from all_tab_columns where table_name='" + ((String)comboBox3.getSelectedItem()).toUpperCase() + "'";
                            String[] name = new String[100];
                            int i = 0;
                            try {
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                                PreparedStatement ps = null;
                                ps = conn.prepareStatement(qry);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                   name[i] = new String(rs.getString(1));
                                    i++;
                                }
                                conn.close();
                                rs.close();
                            } catch (Exception x) {
                                JOptionPane.showMessageDialog(null, x);
                            }
                            
                     for(int j=0;j<i;j++)
                     {
                         cl_name.add(new JLabel(name[j]));
                         cl_val.add(new JTextField("enter your value here"));
                     }
                     GridBagConstraints tfcons=new GridBagConstraints();
                     GridBagConstraints lbcons=new GridBagConstraints();
                     for(int j=0;j<i;j++)
                     {
                         tfcons.gridx=2;
                         tfcons.gridy=j;
                         lbcons.gridx=1;
                         lbcons.gridy=j;
                         //tfcons.gridheight=10;
                         //tfcons.gridwidth=5;

                        //cl_val.get(j).setSize(100,80);
                         //cl_name.get(j).setBounds(45,j,100,80);
                         //cl_val.get(j).setSize(100,50);
                         ipan.add(cl_name.get(j),lbcons);
                         ipan.add(cl_val.get(j),tfcons);
                     }            
        }

        private void comboBox3MouseClicked(MouseEvent e) {
            ipan.removeAll(); 
        }

        private void button7ActionPerformed(ActionEvent e) {
                    String qry = "select column_name,data_type from all_tab_columns where table_name='" + ((String)comboBox3.getSelectedItem()).toUpperCase() + "'";
                            String[] name = new String[100];
                            String[] type =new String[100];
                           int i = 0;
                            try {
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                                PreparedStatement ps = null;
                                ps = conn.prepareStatement(qry);
                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                                   name[i] = new String(rs.getString(1));
                                   type[i] = new String(rs.getString(2));
                                    i++;
                                }
                                conn.close();
                                rs.close();
                            } catch (Exception x) {
                                JOptionPane.showMessageDialog(null, x);
                            }

                      String qr="insert into "+comboBox3.getSelectedItem()+" values (";
                            for(int j=0;j<i;j++)
                            {
                                if(j!=0)
                                    qr=qr+",";
                                System.out.println("This type is "+type[j]+"aa");
                                if(type[j].equals("VARCHAR") | type[j].equals("DATE") | type[j].equals("TIMESTAMP") | type[j].equals("CHAR") | type[j].equals("VARCHAR2"))
                                {
                                    qr=qr+"'"+cl_val.get(j).getText()+"'";
                                }
                                else
                                {
                                    qr=qr+cl_val.get(j).getText();
                                }
                            }
                            qr=qr+")";
                       System.out.println(qr);
                           ins_qr=qr;
                           
                           //insertion();
        }
        void insertion()
        {
                            try {
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");
                                PreparedStatement pss = null;
                                pss = con.prepareStatement(ins_qr);
                                ResultSet rss = pss.executeQuery();
                                con.close();
                                rss.close();
                                log.logg("VIEW","SUCCESS","table "+comboBox4.getSelectedItem()+" is viewed");
                            } catch (Exception x) {
                                JOptionPane.showMessageDialog(null, x);
                            }
        }

        private void button7MouseReleased(MouseEvent e) {
            insertion();
        }

        private void tabbedPane1FocusGained(FocusEvent e) {
            String qry="select table_name from user_tables";
            //String[] name=new String[100];
            int i=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qry);
                ResultSet rs = ps.executeQuery();
                //comboBox5.removeAllItems();
                while(rs.next())        
                {
                    comboBox5.addItem((String)rs.getString(1));
                    //int i=0;
                    //name[i]=new String(rs.getString(1));
                    i++;
                }
                conn.close();
                rs.close();
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }


        }

        private void button8ActionPerformed(ActionEvent e) {
                            String qry = "select column_name,data_type from all_tab_columns where table_name='" + ((String)comboBox5.getSelectedItem()).toUpperCase() + "'";
                            String[] name = new String[100];
                            String[] type=new String[100];
                            JComboBox a=new JComboBox();
                           int i = 0;        
                            try {        
                                Class.forName("oracle.jdbc.driver.OracleDriver");        
                                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "krishna", "rakesh");        
                                PreparedStatement ps = null;        
                                ps = conn.prepareStatement(qry);        
                                ResultSet rs = ps.executeQuery();        
                                while (rs.next()) {        
                                   name[i] = new String(rs.getString(1)); 
                                   type[i] = new String(rs.getString(2));
                                   u_name=name.clone();
                                   u_type=type.clone();
                                   a.addItem(name[i]);
                                    i++;        
                                }        
                                conn.close();        
                                rs.close();        
                            } catch (Exception x) {        
                                JOptionPane.showMessageDialog(null, x);        
                            }        

                      if(up_i<i) {
                          up_name.add(new JComboBox(name));
                          cl_val.add(new JTextField("enter your value here"));
                          upan.removeAll();
                          GridBagConstraints tfcons = new GridBagConstraints();
                          GridBagConstraints lbcons = new GridBagConstraints();
                          for (int j = 0; j <= up_i; j++) {
                              tfcons.gridx = 2;
                              tfcons.gridy = j;
                              lbcons.gridx = 1;
                              lbcons.gridy = j;

                              upan.add(up_name.get(j), lbcons);
                              upan.add(cl_val.get(j), tfcons);
                          }
                          up_i++;
                          upan.setVisible(true);
                      }
        }


        private void button9ActionPerformed(ActionEvent e) {
              String qry="update "+comboBox5.getSelectedItem()+" set ";
              for(int j=0;j<up_i;j++)
              {
                  if(j!=0)
                      qry=qry+",";
                  if(u_type[j].equals("VARCHAR")|u_type.equals("VARCHAR2")|u_type.equals("CHAR")|u_type.equals("DATE")|u_type.equals("TIMESTAMP"))
                  {
                      qry=qry+up_name.get(j).getSelectedItem()+" = '"+cl_val.get(j).getText()+"'";
                  }
                  else
                  {
                      qry=qry+up_name.get(j).getSelectedItem()+" = "+cl_val.get(j).getText();
                  }
              }
              qry=qry+" where "+uwhere.getText();
              System.out.println(qry);
              //runner(qry);
                uqry=qry;
            int count=0;
            java.sql.Statement stmt=null;
            Connection conn=null;
            System.out.println("rnner entered");
            try{

                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                //PreparedStatement ps = null;
                stmt=conn.createStatement();

                count=stmt.executeUpdate(qry);
                log.logg("UPDATE","SUCCESS",count+"ROWS UPDATED on table "+comboBox5.getSelectedItem());
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
                log.logg("UPDATE","FAILED",String.valueOf(x));
            }
            finally {
                try {
                    if (stmt != null)
                        stmt.close();
                    conn.close();
                }catch (Exception ex)
                {}
            }
            JOptionPane.showMessageDialog(null,count+"ROWS UPDATED");
            uwhere.setText("");
        }

        private void comboBox5ItemStateChanged(ItemEvent e) {
            // TODO add your code here
        }

        private void button2KeyPressed(KeyEvent e) {
        }
        void runner(String qr)
        {
            System.out.println("rnner entered");
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qr);
                ResultSet rs = ps.executeQuery();
                conn.close();
                rs.close();
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }
            JOptionPane.showMessageDialog(null,"Query successfully run");
        }
        private void button2KeyReleased(KeyEvent e) {
            //runner(uqry);
        }

        private void button9KeyPressed(KeyEvent e) {
            /*String qry="update "+comboBox5.getSelectedItem()+" set ";
            for(int j=0;j<up_i;j++)
            {
                if(j!=0)
                    qry=qry+",";
                if(u_type[j].equals("VARCHAR")|u_type.equals("VARCHAR2")|u_type.equals("CHAR")|u_type.equals("DATE")|u_type.equals("TIMESTAMP"))
                {
                    qry=qry+up_name.get(j).getSelectedItem()+" = '"+cl_val.get(j).getText()+"'";
                }
                else
                {
                    qry=qry+up_name.get(j).getSelectedItem()+" = "+cl_val.get(j).getText();
                }
            }
            qry=qry+" where "+uwhere.getText();
            System.out.println(qry);
            runner(qry);*/
            //runner(uqry);
        }

        private void button9KeyReleased(KeyEvent e) {

        }

        private void panel7FocusGained(FocusEvent e) {
            /*int i=0;
            */
        }

        private void panel7MouseEntered(MouseEvent e) {
            String qry="select table_name from user_tables";
            int i=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qry);
                ResultSet rs = ps.executeQuery();
                //comboBox6.removeAllItems();
                while(rs.next())
                {
                    comboBox6.addItem((String)rs.getString(1));
                    //int i=0;
                    //name[i]=new String(rs.getString(1));
                    i++;
                }
                conn.close();
                rs.close();
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }

        }

        private void button10ActionPerformed(ActionEvent e) {
            Connection conn=null;
            java.sql.Statement stmt=null;
            String qry="delete from "+comboBox6.getSelectedItem()+" where "+textField5.getText();
            int count=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                //PreparedStatement ps = null;
                stmt=conn.createStatement();
                count=stmt.executeUpdate(qry);
                log.logg("DELETE","SUCCESS",String.valueOf(count)+" rows updated in "+comboBox6.getSelectedItem());
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
                log.logg("AlTER","FAILED",String.valueOf(x));
            }
            finally {
                try {
                    if (stmt != null)
                        stmt.close();
                    conn.close();
                }catch (Exception ex)
                {}
            }
            JOptionPane.showMessageDialog(null,count+"ROWS UPDATED");
        }

        private void panel8MouseEntered(MouseEvent e) {
            String qry="select table_name from user_tables";
            int i=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qry);
                ResultSet rs = ps.executeQuery();
                //comboBox9.removeAllItems();
                while(rs.next())
                {
                    comboBox9.addItem((String)rs.getString(1));
                    //int i=0;
                    //name[i]=new String(rs.getString(1));
                    i++;
                }
                conn.close();
                rs.close();
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }


        }

        private void button11MouseClicked(MouseEvent e) {
            String qr="";
            if(textPane3.getText()=="declare here") {
                qr = "CREATE OR REPLACE  TRIGGER " + textField7.getText() + "  \n" + comboBox7.getSelectedItem() + "  " + comboBox8.getSelectedItem() +
                        " ON " + comboBox9.getSelectedItem() + " \n FOR EACH ROW \n DECLARE \n" + textPane3.getText() + "\n BEGIN\n" + textArea1.getText() + "\n END;\n";
            }
            else
            {
                qr = "CREATE OR REPLACE  TRIGGER " + textField7.getText() + "  \n" + comboBox7.getSelectedItem() + "  " + comboBox8.getSelectedItem() +
                        " ON " + comboBox9.getSelectedItem() + " \n FOR EACH ROW \n BEGIN\n" + textArea1.getText() + "\n END;\n";
            }
            java.sql.Statement stmt=null;
            Connection conn=null;

            System.out.println(qr);
            int count=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                stmt=conn.createStatement();
                stmt.execute(qr);
                JOptionPane.showMessageDialog(null,"Trigger created!!");
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }finally {
                try
                {
                    if(conn!=null)
                        conn.close();
                    if(stmt!=null)
                        stmt.close();
                }catch (Exception ex){

                }
            }

        }

        private void button12KeyPressed(KeyEvent e) {
           /* String str="";
            if(comboBox10.getSelectedItem().equals("FUNCTION"))
            {
                str="CREATE OR REPLACE FUNCTION "+textField8.getText()+"\n" +
                        " "+textField9.getText()+"\n" +
                        "\n" +
                        "   RETURN "+textField10.getText()+"\n" +
                        comboBox11.getSelectedItem()+"\n" +
                        "\n" +
                        textPane4.getText()+"\n" +
                        "\n" +
                        "BEGIN\n" +
                        textPane5.getText()+
                        "\n" +
                        "END;";
            }
            else
            {
                str="CREATE OR REPLACE  PROCEDURE"+textField8.getText()+"\n" +
                        " "+textField9.getText()+"\n" +
                        "\n" +
                        comboBox11.getSelectedItem()+"\n" +
                        "\n" +
                        textPane4.getText()+"\n" +
                        "\n" +
                        "BEGIN\n" +
                        textPane5.getText()+
                        "\n" +
                        "END;";
            }
            System.out.println(str);*/
        }

        private void button12MouseClicked(MouseEvent e) {
            String str="";
            System.out.println("absure");
            if(comboBox10.getSelectedItem().equals("FUNCTION"))
            {
                str="CREATE OR REPLACE FUNCTION "+textField8.getText()+"\n" +
                        " "+textField9.getText()+"\n" +
                        "\n" +
                        "   RETURN "+textField10.getText()+"\n" +
                        comboBox11.getSelectedItem()+"\n" +
                        "\n" +
                        textPane4.getText()+"\n" +
                        "\n" +
                        "BEGIN\n" +
                        textPane5.getText()+
                        "\n" +
                        "END;";
            }
            else
            {
                str="CREATE OR REPLACE  PROCEDURE"+textField8.getText()+"\n" +
                        " "+textField9.getText()+"\n" +
                        "\n" +
                        comboBox11.getSelectedItem()+"\n" +
                        "\n" +
                        textPane4.getText()+"\n" +
                        "\n" +
                        "BEGIN\n" +
                        textPane5.getText()+
                        "\n" +
                        "END;";
            }
            System.out.println(str);

            java.sql.Statement stmt=null;
            Connection conn=null;

            System.out.println(str);
            int count=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                stmt=conn.createStatement();
                stmt.execute(str);
                JOptionPane.showMessageDialog(null,"Trigger created!!");
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }finally {
                try
                {
                    if(conn!=null)
                        conn.close();
                    if(stmt!=null)
                        stmt.close();
                }catch (Exception ex){

                }
            }

        }

        private void tabnameCaretUpdate(CaretEvent e) {
            String str=new String(tabname.getText());
            if(str.length()>30)
            {
                JOptionPane.showMessageDialog(null,"table name is too long");
            }
        }

        private void comboBox1ActionPerformed(ActionEvent e) {
            
        }

        private void panel2PropertyChange(PropertyChangeEvent e) {
            //JOptionPane.showMessageDialog(null,"hi");
        }

        private void panel2FocusGained(FocusEvent e) {
            //JOptionPane.showMessageDialog(null,"hi");
        }

        private void panel2MouseEntered(MouseEvent e) {
            if(ldr1==1)
            {
                ldr1=0;
                String qry="select table_name from user_tables";
                String[] name=new String[100];
                int i=0;
                try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                    PreparedStatement ps = null;
                    ps = conn.prepareStatement(qry);
                    ResultSet rs = ps.executeQuery();
                    //comboBox3.removeAllItems();
                    while(rs.next())
                    {
                        //int i=0;
                        name[i]=new String(rs.getString(1));
                        comboBox12.addItem(name[i]);
                        i++;
                    }
                    conn.close();
                    rs.close();
                }
                catch(Exception x)
                {
                    //System.out.println(x);
                    JOptionPane.showMessageDialog(null,x);
                }
            }
        }

        private void comboBox1ItemStateChanged(ItemEvent e) {
            
        }

        private void comboBox1FocusLost(FocusEvent e) {
            String aa=String.valueOf(comboBox1.getSelectedItem()).toUpperCase();
            String qry="select object_name from all_objects where object_type='"+aa+"' and owner=USER";
            System.out.println(qry);
            String[] name=new String[100];
            int i=0;
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","krishna","rakesh");
                PreparedStatement ps = null;
                ps = conn.prepareStatement(qry);
                ResultSet rs = ps.executeQuery();
                comboBox3.removeAllItems();
                while(rs.next())
                {
                    //int i=0;
                    name[i]=new String(rs.getString(1));
                    comboBox12.addItem(name[i]);
                    i++;
                }
                conn.close();
                rs.close();
                comboBox12.setVisible(true);
            }
            catch(Exception x)
            {
                //System.out.println(x);
                JOptionPane.showMessageDialog(null,x);
            }
        }

        private void thisWindowClosing(WindowEvent e) {
            log.senEnd();
            log.setDur();
            System.out.println("session ender");
        }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - saravanan
        tabbedPane1 = new JTabbedPane();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
        label3 = new JLabel();
        tabname = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        button1 = new JButton();
        colname = new JTextField();
        coltype = new JComboBox<>();
        len = new JTextField();
        contype = new JComboBox<>();
        scrollPane2 = new JScrollPane();
        textPane1 = new JTextPane();
        button2 = new JButton();
        scrollPane4 = new JScrollPane();
        panel3 = new JPanel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        label14 = new JLabel();
        ftab = new JTextField();
        fvar = new JComboBox();
        stab = new JTextField();
        svar = new JComboBox();
        button5 = new JButton();
        label15 = new JLabel();
        textField6 = new JTextField();
        label16 = new JLabel();
        scrollPane7 = new JScrollPane();
        panel5 = new JPanel();
        label18 = new JLabel();
        comboBox3 = new JComboBox<>();
        button7 = new JButton();
        scrollPane8 = new JScrollPane();
        ipan = new JPanel();
        scrollPane9 = new JScrollPane();
        panel6 = new JPanel();
        label19 = new JLabel();
        comboBox5 = new JComboBox();
        scrollPane10 = new JScrollPane();
        upan = new JPanel();
        button8 = new JButton();
        scrollPane11 = new JScrollPane();
        uwhere = new JEditorPane();
        button9 = new JButton();
        scrollPane12 = new JScrollPane();
        panel7 = new JPanel();
        label20 = new JLabel();
        comboBox6 = new JComboBox();
        textField5 = new JTextField();
        label21 = new JLabel();
        button10 = new JButton();
        scrollPane13 = new JScrollPane();
        panel8 = new JPanel();
        label22 = new JLabel();
        textField7 = new JTextField();
        comboBox7 = new JComboBox<>();
        comboBox8 = new JComboBox<>();
        comboBox9 = new JComboBox();
        label23 = new JLabel();
        scrollPane14 = new JScrollPane();
        textPane3 = new JTextPane();
        label24 = new JLabel();
        scrollPane15 = new JScrollPane();
        textArea1 = new JTextArea();
        button11 = new JButton();
        scrollPane16 = new JScrollPane();
        panel9 = new JPanel();
        label25 = new JLabel();
        textField8 = new JTextField();
        comboBox10 = new JComboBox<>();
        label26 = new JLabel();
        label27 = new JLabel();
        textField9 = new JTextField();
        comboBox11 = new JComboBox<>();
        scrollPane17 = new JScrollPane();
        textPane4 = new JTextPane();
        scrollPane18 = new JScrollPane();
        textPane5 = new JTextPane();
        button12 = new JButton();
        label28 = new JLabel();
        textField10 = new JTextField();
        scrollPane5 = new JScrollPane();
        panel4 = new JPanel();
        label17 = new JLabel();
        comboBox4 = new JComboBox();
        button6 = new JButton();
        scrollPane19 = new JScrollPane();
        table1 = new JTable();
        scrollPane3 = new JScrollPane();
        panel2 = new JPanel();
        label1 = new JLabel();
        comboBox1 = new JComboBox<>();
        textField1 = new JTextField();
        button3 = new JButton();
        label2 = new JLabel();
        comboBox2 = new JComboBox<>();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button4 = new JButton();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        comboBox12 = new JComboBox();

        //======== this ========
        setTitle("DB KRISH");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //======== tabbedPane1 ========
        {
            tabbedPane1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    tabbedPane1FocusGained(e);
                    tabbedPane1FocusGained(e);
                    tabbedPane1FocusGained(e);
                }
            });

            //======== scrollPane1 ========
            {

                //======== panel1 ========
                {
                    panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
                    swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border
                    . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
                    ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel1. getBorder
                    ( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
                    .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
                    ( ); }} );

                    //---- label3 ----
                    label3.setText("TABLE NAME");

                    //---- tabname ----
                    tabname.addCaretListener(e -> tabnameCaretUpdate(e));

                    //---- label4 ----
                    label4.setText("COLUMN NAME");

                    //---- label5 ----
                    label5.setText("COLUMN TYPE");

                    //---- label6 ----
                    label6.setText("LENGTH");

                    //---- label7 ----
                    label7.setText("CONSTRAINT");

                    //---- button1 ----
                    button1.setText("ADD COLUMN");
                    button1.addActionListener(e -> button1ActionPerformed(e));

                    //---- coltype ----
                    coltype.setModel(new DefaultComboBoxModel<>(new String[] {
                        "varchar",
                        "char",
                        "date",
                        "integer",
                        "number",
                        "timestamp",
                        "boolean"
                    }));

                    //---- contype ----
                    contype.setModel(new DefaultComboBoxModel<>(new String[] {
                        "null",
                        "not null",
                        "primary key",
                        "unique"
                    }));

                    //======== scrollPane2 ========
                    {
                        scrollPane2.setViewportView(textPane1);
                    }

                    //---- button2 ----
                    button2.setText("create table");
                    button2.addActionListener(e -> button2ActionPerformed(e));

                    GroupLayout panel1Layout = new GroupLayout(panel1);
                    panel1.setLayout(panel1Layout);
                    panel1Layout.setHorizontalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 653, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(button2)
                                        .addGap(0, 146, Short.MAX_VALUE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(colname)
                                            .addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(label4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(66, 66, 66)
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tabname, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(coltype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(38, 38, 38)
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(len, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                                        .addGap(61, 61, 61)
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(contype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(label7, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                .addGap(56, 56, 56)
                                                .addComponent(button1)))
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    );
                    panel1Layout.setVerticalGroup(
                        panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tabname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button1))
                                    .addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(colname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(coltype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contype, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(len, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(16, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                                        .addComponent(button2)
                                        .addGap(61, 61, 61))))
                    );
                }
                scrollPane1.setViewportView(panel1);
            }
            tabbedPane1.addTab("create table", scrollPane1);

            //======== scrollPane4 ========
            {

                //======== panel3 ========
                {

                    //---- label11 ----
                    label11.setText("frist table name");

                    //---- label12 ----
                    label12.setText("column name");

                    //---- label13 ----
                    label13.setText("second table name");

                    //---- label14 ----
                    label14.setText("column name");

                    //---- ftab ----
                    ftab.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            textField5FocusLost(e);
                        }
                    });

                    //---- stab ----
                    stab.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            stabFocusLost(e);
                            stabFocusLost(e);
                        }
                    });

                    //---- button5 ----
                    button5.setText("create");
                    button5.addActionListener(e -> button5ActionPerformed(e));

                    //---- label16 ----
                    label16.setText("foriegn key name");

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addContainerGap(740, Short.MAX_VALUE)
                                .addComponent(button5)
                                .addGap(77, 77, 77))
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(GroupLayout.Alignment.LEADING, panel3Layout.createSequentialGroup()
                                        .addGroup(panel3Layout.createParallelGroup()
                                            .addComponent(label11, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ftab, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                        .addGap(74, 74, 74)
                                        .addGroup(panel3Layout.createParallelGroup()
                                            .addComponent(fvar, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label12, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE))
                                    .addGroup(panel3Layout.createSequentialGroup()
                                        .addGroup(panel3Layout.createParallelGroup()
                                            .addGroup(panel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(label15))
                                            .addGroup(panel3Layout.createSequentialGroup()
                                                .addComponent(label16, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                        .addGap(83, 83, 83)))
                                .addGroup(panel3Layout.createParallelGroup()
                                    .addComponent(stab, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
                                .addGap(83, 83, 83)
                                .addGroup(panel3Layout.createParallelGroup()
                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(svar, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(154, Short.MAX_VALUE))
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label15)
                                    .addComponent(label16, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label12)
                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(ftab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fvar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(svar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                .addComponent(button5)
                                .addGap(65, 65, 65))
                    );
                }
                scrollPane4.setViewportView(panel3);
            }
            tabbedPane1.addTab("Foriegn Key", scrollPane4);

            //======== scrollPane7 ========
            {

                //======== panel5 ========
                {
                    panel5.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel5MouseEntered(e);
                        }
                    });

                    //---- label18 ----
                    label18.setText("TABLE NAME");

                    //---- comboBox3 ----
                    comboBox3.setModel(new DefaultComboBoxModel<>(new String[] {
                        "none"
                    }));
                    comboBox3.addActionListener(e -> {
			comboBox3ActionPerformed(e);
			comboBox3ActionPerformed(e);
			comboBox3ActionPerformed(e);
		});
                    comboBox3.addItemListener(e -> {
			comboBox3ItemStateChanged(e);
			comboBox3ItemStateChanged(e);
			comboBox3ItemStateChanged(e);
		});
                    comboBox3.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            comboBox3MouseClicked(e);
                        }
                    });

                    //---- button7 ----
                    button7.setText("INSERT");
                    button7.addActionListener(e -> button7ActionPerformed(e));
                    button7.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            button7MouseReleased(e);
                        }
                    });

                    //======== scrollPane8 ========
                    {
                        scrollPane8.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        scrollPane8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                        //======== ipan ========
                        {
                            ipan.setLayout(new GridBagLayout());
                            ((GridBagLayout)ipan.getLayout()).columnWidths = new int[] {0, 0, 0};
                            ((GridBagLayout)ipan.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                            ((GridBagLayout)ipan.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)ipan.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                        }
                        scrollPane8.setViewportView(ipan);
                    }

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGroup(panel5Layout.createParallelGroup()
                                    .addGroup(panel5Layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(label18)
                                        .addGap(67, 67, 67)
                                        .addComponent(comboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81)
                                        .addComponent(button7)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(panel5Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)))
                                .addContainerGap())
                    );
                    panel5Layout.setVerticalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label18, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button7))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane8, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                .addContainerGap())
                    );
                }
                scrollPane7.setViewportView(panel5);
            }
            tabbedPane1.addTab("Insert Values", scrollPane7);

            //======== scrollPane9 ========
            {

                //======== panel6 ========
                {

                    //---- label19 ----
                    label19.setText("Table Name");

                    //---- comboBox5 ----
                    comboBox5.addItemListener(e -> {
			comboBox5ItemStateChanged(e);
			comboBox5ItemStateChanged(e);
		});

                    //======== scrollPane10 ========
                    {

                        //======== upan ========
                        {
                            upan.setLayout(new GridBagLayout());
                            ((GridBagLayout)upan.getLayout()).columnWidths = new int[] {0, 0, 0};
                            ((GridBagLayout)upan.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                            ((GridBagLayout)upan.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)upan.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                        }
                        scrollPane10.setViewportView(upan);
                    }

                    //---- button8 ----
                    button8.setText("Add column");
                    button8.addActionListener(e -> button8ActionPerformed(e));

                    //======== scrollPane11 ========
                    {
                        scrollPane11.setViewportView(uwhere);
                    }

                    //---- button9 ----
                    button9.setText("Update");
                    button9.addActionListener(e -> button9ActionPerformed(e));
                    button9.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            button9KeyPressed(e);
                        }
                        @Override
                        public void keyReleased(KeyEvent e) {
                            button9KeyReleased(e);
                        }
                    });

                    GroupLayout panel6Layout = new GroupLayout(panel6);
                    panel6.setLayout(panel6Layout);
                    panel6Layout.setHorizontalGroup(
                        panel6Layout.createParallelGroup()
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addGroup(panel6Layout.createParallelGroup()
                                    .addGroup(panel6Layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(label19, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel6Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(panel6Layout.createParallelGroup()
                                            .addComponent(button8)
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(scrollPane10, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
                                                .addGap(56, 56, 56)
                                                .addComponent(scrollPane11, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(66, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                .addGap(0, 717, Short.MAX_VALUE)
                                .addComponent(button9, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87))
                    );
                    panel6Layout.setVerticalGroup(
                        panel6Layout.createParallelGroup()
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label19, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrollPane11, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(scrollPane10, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button8)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button9)
                                .addContainerGap(72, Short.MAX_VALUE))
                    );
                }
                scrollPane9.setViewportView(panel6);
            }
            tabbedPane1.addTab("Update", scrollPane9);

            //======== scrollPane12 ========
            {

                //======== panel7 ========
                {
                    panel7.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            panel7FocusGained(e);
                        }
                    });
                    panel7.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel7MouseEntered(e);
                        }
                    });

                    //---- label20 ----
                    label20.setText("Table name");

                    //---- label21 ----
                    label21.setText("Condition");

                    //---- button10 ----
                    button10.setText("delete");
                    button10.addActionListener(e -> button10ActionPerformed(e));

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addComponent(label21, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label20, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
                                .addGap(75, 75, 75)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(button10)
                                        .addGap(0, 538, Short.MAX_VALUE))
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addGroup(panel7Layout.createParallelGroup()
                                            .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(393, Short.MAX_VALUE))))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label20, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label21, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addGap(79, 79, 79)
                                .addComponent(button10)
                                .addContainerGap(128, Short.MAX_VALUE))
                    );
                }
                scrollPane12.setViewportView(panel7);
            }
            tabbedPane1.addTab("Delete", scrollPane12);

            //======== scrollPane13 ========
            {

                //======== panel8 ========
                {
                    panel8.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel8MouseEntered(e);
                        }
                    });

                    //---- label22 ----
                    label22.setText("TRIGGER  NAME");

                    //---- comboBox7 ----
                    comboBox7.setModel(new DefaultComboBoxModel<>(new String[] {
                        "BEFORE",
                        "AFTER"
                    }));

                    //---- comboBox8 ----
                    comboBox8.setModel(new DefaultComboBoxModel<>(new String[] {
                        "INSERT",
                        "UPDATE",
                        "DELETE"
                    }));

                    //---- label23 ----
                    label23.setText("DECLARE");

                    //======== scrollPane14 ========
                    {

                        //---- textPane3 ----
                        textPane3.setText("declare here");
                        scrollPane14.setViewportView(textPane3);
                    }

                    //---- label24 ----
                    label24.setText("BEGIN");

                    //======== scrollPane15 ========
                    {
                        scrollPane15.setViewportView(textArea1);
                    }

                    //---- button11 ----
                    button11.setText("text");
                    button11.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button11MouseClicked(e);
                        }
                    });

                    GroupLayout panel8Layout = new GroupLayout(panel8);
                    panel8.setLayout(panel8Layout);
                    panel8Layout.setHorizontalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(panel8Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(panel8Layout.createParallelGroup()
                                    .addGroup(panel8Layout.createSequentialGroup()
                                        .addComponent(scrollPane14, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(570, Short.MAX_VALUE))
                                    .addGroup(panel8Layout.createSequentialGroup()
                                        .addGroup(panel8Layout.createParallelGroup()
                                            .addComponent(label22, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label23, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(panel8Layout.createParallelGroup()
                                            .addGroup(panel8Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                                .addGap(65, 65, 65)
                                                .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(87, 87, 87)
                                                .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(88, 88, 88)
                                                .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel8Layout.createSequentialGroup()
                                                .addGap(201, 201, 201)
                                                .addGroup(panel8Layout.createParallelGroup()
                                                    .addComponent(scrollPane15, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label24, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
                                        .addContainerGap(83, Short.MAX_VALUE))))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                .addContainerGap(694, Short.MAX_VALUE)
                                .addComponent(button11)
                                .addGap(123, 123, 123))
                    );
                    panel8Layout.setVerticalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(panel8Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(panel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label22, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(panel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label23, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label24, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrollPane14, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                    .addComponent(scrollPane15, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(button11)
                                .addGap(21, 21, 21))
                    );
                }
                scrollPane13.setViewportView(panel8);
            }
            tabbedPane1.addTab("Trigger", scrollPane13);

            //======== scrollPane16 ========
            {

                //======== panel9 ========
                {

                    //---- label25 ----
                    label25.setText("Name");

                    //---- comboBox10 ----
                    comboBox10.setModel(new DefaultComboBoxModel<>(new String[] {
                        "FUNCTION",
                        "PROCEDURE"
                    }));

                    //---- label26 ----
                    label26.setText("TYPE");

                    //---- label27 ----
                    label27.setText("ParaMeters");

                    //---- comboBox11 ----
                    comboBox11.setModel(new DefaultComboBoxModel<>(new String[] {
                        "IS",
                        "AS"
                    }));

                    //======== scrollPane17 ========
                    {
                        scrollPane17.setViewportView(textPane4);
                    }

                    //======== scrollPane18 ========
                    {
                        scrollPane18.setViewportView(textPane5);
                    }

                    //---- button12 ----
                    button12.setText("CREATE");
                    button12.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            button12KeyPressed(e);
                            button12KeyPressed(e);
                            button12KeyPressed(e);
                            button12KeyPressed(e);
                        }
                    });
                    button12.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button12MouseClicked(e);
                            button12MouseClicked(e);
                        }
                    });

                    //---- label28 ----
                    label28.setText("RETURN");

                    GroupLayout panel9Layout = new GroupLayout(panel9);
                    panel9.setLayout(panel9Layout);
                    panel9Layout.setHorizontalGroup(
                        panel9Layout.createParallelGroup()
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addGroup(panel9Layout.createSequentialGroup()
                                        .addComponent(scrollPane18, GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE)
                                        .addGap(90, 90, 90)
                                        .addComponent(button12)
                                        .addContainerGap(98, Short.MAX_VALUE))
                                    .addGroup(panel9Layout.createSequentialGroup()
                                        .addGroup(panel9Layout.createParallelGroup()
                                            .addComponent(label25, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label27, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(31, 31, 31)
                                        .addGroup(panel9Layout.createParallelGroup()
                                            .addGroup(panel9Layout.createSequentialGroup()
                                                .addComponent(textField8, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                                .addGap(61, 61, 61)
                                                .addComponent(label26, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32)
                                                .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel9Layout.createSequentialGroup()
                                                .addComponent(textField9, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(comboBox11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(52, 52, 52)
                                                .addComponent(scrollPane17, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(textField10, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(44, Short.MAX_VALUE))))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel9Layout.createSequentialGroup()
                                .addContainerGap(79, Short.MAX_VALUE)
                                .addComponent(label28, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                .addGap(730, 730, 730))
                    );
                    panel9Layout.setVerticalGroup(
                        panel9Layout.createParallelGroup()
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(panel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label25, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label26, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addGroup(panel9Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(panel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label27, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBox11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scrollPane17, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label28, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textField10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addComponent(scrollPane18, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(12, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel9Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                                        .addComponent(button12)
                                        .addGap(106, 106, 106))))
                    );
                }
                scrollPane16.setViewportView(panel9);
            }
            tabbedPane1.addTab("Function", scrollPane16);

            //======== scrollPane5 ========
            {

                //======== panel4 ========
                {
                    panel4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel4MouseEntered(e);
                        }
                    });

                    //---- label17 ----
                    label17.setText("Table Name");

                    //---- button6 ----
                    button6.setText("View");
                    button6.addActionListener(e -> button6ActionPerformed(e));
                    button6.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseExited(MouseEvent e) {
                            button6MouseExited(e);
                        }
                    });

                    //======== scrollPane19 ========
                    {
                        scrollPane19.setViewportView(table1);
                    }

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGroup(panel4Layout.createParallelGroup()
                                    .addGroup(panel4Layout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addComponent(label17, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                        .addGap(68, 68, 68)
                                        .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(button6))
                                    .addGroup(panel4Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(scrollPane19, GroupLayout.PREFERRED_SIZE, 644, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel4Layout.setVerticalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button6)
                                    .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label17, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane19, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(156, Short.MAX_VALUE))
                    );
                }
                scrollPane5.setViewportView(panel4);
            }
            tabbedPane1.addTab("View Tables", scrollPane5);

            //======== scrollPane3 ========
            {

                //======== panel2 ========
                {
                    panel2.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            panel2FocusGained(e);
                        }
                    });
                    panel2.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            panel2MouseEntered(e);
                        }
                    });

                    //---- label1 ----
                    label1.setText("DROP");

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                        "table",
                        "function",
                        "constraint",
                        "trigger",
                        "procedure"
                    }));
                    comboBox1.addActionListener(e -> comboBox1ActionPerformed(e));
                    comboBox1.addItemListener(e -> comboBox1ItemStateChanged(e));
                    comboBox1.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            comboBox1FocusLost(e);
                        }
                    });

                    //---- button3 ----
                    button3.setText("RUN");
                    button3.addActionListener(e -> button3ActionPerformed(e));

                    //---- label2 ----
                    label2.setText("ALTER     ");

                    //---- comboBox2 ----
                    comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
                        "add",
                        "modify",
                        "drop column",
                        "rename column",
                        "rename to"
                    }));
                    comboBox2.addActionListener(e -> comboBox2ActionPerformed(e));

                    //---- button4 ----
                    button4.setText("ALTER");
                    button4.addActionListener(e -> button4ActionPerformed(e));

                    //---- label8 ----
                    label8.setText("column name");

                    //---- label9 ----
                    label9.setText("column type");

                    //---- label10 ----
                    label10.setText("Table name");

                    GroupLayout panel2Layout = new GroupLayout(panel2);
                    panel2.setLayout(panel2Layout);
                    panel2Layout.setHorizontalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(panel2Layout.createParallelGroup()
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel2Layout.createParallelGroup()
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addGroup(panel2Layout.createParallelGroup()
                                            .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(63, 63, 63))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(panel2Layout.createParallelGroup()
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                        .addGap(118, 118, 118)
                                        .addComponent(button3)
                                        .addGap(149, 149, 149)
                                        .addComponent(button4)
                                        .addContainerGap(144, Short.MAX_VALUE))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                        .addGap(54, 54, 54)
                                        .addGroup(panel2Layout.createParallelGroup()
                                            .addComponent(label8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(panel2Layout.createSequentialGroup()
                                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 48, Short.MAX_VALUE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel2Layout.createParallelGroup()
                                            .addComponent(label9, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
                                        .addGap(241, 241, 241))))
                    );
                    panel2Layout.setVerticalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button3))
                                .addGap(29, 29, 29)
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label8, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label10, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label9, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button4)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(256, Short.MAX_VALUE))
                    );
                }
                scrollPane3.setViewportView(panel2);
            }
            tabbedPane1.addTab("Dropper", scrollPane3);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        setVisible(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - saravanan
    private JTabbedPane tabbedPane1;
    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JLabel label3;
    private JTextField tabname;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JButton button1;
    private JTextField colname;
    private JComboBox<String> coltype;
    private JTextField len;
    private JComboBox<String> contype;
    private JScrollPane scrollPane2;
    private JTextPane textPane1;
    private JButton button2;
    private JScrollPane scrollPane4;
    private JPanel panel3;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JLabel label14;
    private JTextField ftab;
    private JComboBox fvar;
    private JTextField stab;
    private JComboBox svar;
    private JButton button5;
    private JLabel label15;
    private JTextField textField6;
    private JLabel label16;
    private JScrollPane scrollPane7;
    private JPanel panel5;
    private JLabel label18;
    private JComboBox<String> comboBox3;
    private JButton button7;
    private JScrollPane scrollPane8;
    private JPanel ipan;
    private JScrollPane scrollPane9;
    private JPanel panel6;
    private JLabel label19;
    private JComboBox comboBox5;
    private JScrollPane scrollPane10;
    private JPanel upan;
    private JButton button8;
    private JScrollPane scrollPane11;
    private JEditorPane uwhere;
    private JButton button9;
    private JScrollPane scrollPane12;
    private JPanel panel7;
    private JLabel label20;
    private JComboBox comboBox6;
    private JTextField textField5;
    private JLabel label21;
    private JButton button10;
    private JScrollPane scrollPane13;
    private JPanel panel8;
    private JLabel label22;
    private JTextField textField7;
    private JComboBox<String> comboBox7;
    private JComboBox<String> comboBox8;
    private JComboBox comboBox9;
    private JLabel label23;
    private JScrollPane scrollPane14;
    private JTextPane textPane3;
    private JLabel label24;
    private JScrollPane scrollPane15;
    private JTextArea textArea1;
    private JButton button11;
    private JScrollPane scrollPane16;
    private JPanel panel9;
    private JLabel label25;
    private JTextField textField8;
    private JComboBox<String> comboBox10;
    private JLabel label26;
    private JLabel label27;
    private JTextField textField9;
    private JComboBox<String> comboBox11;
    private JScrollPane scrollPane17;
    private JTextPane textPane4;
    private JScrollPane scrollPane18;
    private JTextPane textPane5;
    private JButton button12;
    private JLabel label28;
    private JTextField textField10;
    private JScrollPane scrollPane5;
    private JPanel panel4;
    private JLabel label17;
    private JComboBox comboBox4;
    private JButton button6;
    private JScrollPane scrollPane19;
    private JTable table1;
    private JScrollPane scrollPane3;
    private JPanel panel2;
    private JLabel label1;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JButton button3;
    private JLabel label2;
    private JComboBox<String> comboBox2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button4;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JComboBox comboBox12;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
