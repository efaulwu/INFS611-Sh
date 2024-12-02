<%@page import="java.sql.*"%>
<%
  String cid = request.getParameter("cid");
  String tref = request.getParameter("tref");  
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;

  try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
      conn = DriverManager.getConnection(url, "rkoppolu", "aptooshi");
      stmt = conn.createStatement();
      String query = "SELECT PA.cid, PA.family_id, PA.percent_added, T.cid, T.tref, T.t_points FROM Point_Accounts PA JOIN Transactions T ON PA.cid = T.cid AND PA.cid = '" + cid + "' AND T.tref = '" + tref + "'";
      rs = stmt.executeQuery(query);

      String output = "";
      while (rs.next()) {
          output += rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(6) + "#";
      }

      if (!(output.trim().isEmpty())) {
          out.print(output);
      } else {
          out.print("No records found");
      }
  } catch (Exception e) {
      out.print("SQL Error");
  } finally {
      if (rs != null) try { rs.close(); } catch (SQLException ignored) { }
      if (stmt != null) try { stmt.close(); } catch (SQLException ignored) { }
      if (conn != null) try { conn.close(); } catch (SQLException ignored) { }
  }
%>
