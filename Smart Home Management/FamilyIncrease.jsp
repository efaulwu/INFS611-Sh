<%@page import="java.sql.*"%>
<%
  String cid = request.getParameter("cid");
  String fid = request.getParameter("fid");  
  String npoints = request.getParameter("npoints");
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;
  try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
      conn = DriverManager.getConnection(url,"rkoppolu","aptooshi");
      stmt = conn.createStatement();
      rs = stmt.executeQuery("update point_accounts set num_of_points = num_of_points + '"+npoints+"' where family_id =  '"+fid+"' and cid !=  '"+cid+"'");

      String output = "";
      if(rs.next()){
          out.print("Point accounts of the family members are updated successfully");
      }
      else if(output.trim().isEmpty()){
          out.print("No records got updated");
      }
  } catch (SQLException e) {
      out.print("SQL ERROR");
  } finally {
      if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
      if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignored */ }
      if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignored */ }
  }
%>
