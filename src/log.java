import java.sql.*;
import java.util.*;
import java.util.Date;

public class log {
 static java.sql.Timestamp start = null;
 static java.sql.Timestamp end = null;
 static String uname = "";
 static int senId;

 static void keepNote() {

 }

 static void senStart() {
  start = new Timestamp(new Date().getTime());
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
   Statement stmt = con.createStatement();
   String insert_query = "insert into db_session"
           + " (session_id, Session_start,username)"
           + " values (null,?,?)";
   PreparedStatement ps = con.prepareStatement(insert_query);
   ps.setTimestamp(1, start);
   ps.setString(2, uname);
   ps.executeUpdate();
   con.close();
  } catch (Exception ee) {
   System.out.print(ee);
  }
  //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
 }

 static void senEnd() {
  end = new Timestamp(new Date().getTime());
  Statement stmt = null;
  Connection conn = null;
  String qr = "update db_session set session_end=sysdate where session_id=(select MAX(session_id) from db_session)";
  //String qr="update cand set count=count+1 where name='"+jComboBox1.getSelectedItem()+"'";
  System.out.println(qr);
  int count = 0;
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
   stmt = conn.createStatement();

   stmt.executeUpdate(qr);
   //JOptionPane.showMessageDialog(null,"Trigger created!!");
  } catch (Exception x) {
   System.out.println(x);
   //JOptionPane.showMessageDialog(null,x);
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
 static void setDur()
 {
  Statement stmt = null;
  Connection conn = null;
  String qr = "update db_session set duration=(cast(session_end as date)-cast(session_start as date))*24*60 where session_id=(select MAX(session_id) from db_session)";
  //String qr="update cand set count=count+1 where name='"+jComboBox1.getSelectedItem()+"'";
  System.out.println(qr);
  int count = 0;
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
   stmt = conn.createStatement();

   stmt.executeUpdate(qr);
   //JOptionPane.showMessageDialog(null,"Trigger created!!");
  } catch (Exception x) {
   System.out.println(x);
   //JOptionPane.showMessageDialog(null,x);
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
 static void logg(String name,String sta,String des)
 {
  try {
   //User.se = new Timestamp(new Date().getTime());
   Class.forName("oracle.jdbc.driver.OracleDriver");
   Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "rakesh");
   Statement stmt = con.createStatement();
   String insert_query = "insert into db_logs"
           + " (sid, act_name, act_time,act_status,act_desciption)"
           + " values ((select max(session_id) from db_session), ?, sysdate, ?, ?)";
   PreparedStatement ps = con.prepareStatement(insert_query);
   ps.setString(1,name);
   ps.setString(2,sta);
   ps.setString(3,des);
   ps.executeUpdate();
   con.close();
   System.out.println("looged dude");
  } catch (Exception ee) {
   System.out.print(ee);
  }
  //JOptionPane.showMessageDialog(null, User.sb + " " + User.se);
 }


  public static void main (String[]args)
  {
   //System.out.println(a);
  }

 }

