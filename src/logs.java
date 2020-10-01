import java.util.*;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*
 * Created by JFormDesigner on Sat May 02 22:43:08 IST 2020
 */



/**
 * @author saravanan
 */
public class logs extends JFrame {
    public logs(Object sid) {
        initComponents();
        String qr="select * from db_logs where sid="+sid;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - saravanan
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        //======== this ========
        var contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 705, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(7, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - saravanan
    private JScrollPane scrollPane1;
    private JTable table1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
