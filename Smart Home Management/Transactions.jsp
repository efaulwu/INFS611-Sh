<%@page import="java.sql.*"%>
<%
  String cid = request.getParameter("cid");
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;

  try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
      conn = DriverManager.getConnection(url, "rkoppolu", "aptooshi");
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT cid, tref, TO_CHAR(t_date, 'DD-MON-YY'), t_points, amount FROM Transactions WHERE Transactions.cid = '" + cid + "'");

      String output = "";
      while (rs.next()) {
          output += rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(4) + "," + "$" + rs.getObject(5) + "#";
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

