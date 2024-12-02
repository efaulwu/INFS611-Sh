<%@page import="java.sql.*"%>
<%
  String cid = request.getParameter("cid");
  String cidref = cid; // Seems redundant, you can directly use 'cid' in your query.
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;

  try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
      conn = DriverManager.getConnection(url, "rkoppolu", "aptooshi");
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT point_accounts.cid, point_accounts.NUM_OF_POINTS, Customers.cid, Customers.cname FROM point_accounts JOIN Customers ON point_accounts.cid = Customers.cid AND point_accounts.cid = '" + cidref + "' AND Customers.cid = '" + cidref + "'");

      String output = "";
      while (rs.next()) {
          output += rs.getObject(4) + "," + rs.getObject(2) + "#";
      }

      if (!output.trim().isEmpty()) {
          out.print(output);
      } else {
          out.print("No records found");
      }
  } catch (SQLException e) {
      out.print("SQL ERROR");
  } finally {
      if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
      if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignored */ }
      if (conn != null) try { conn.close(); } catch (SQLException e) { /* ignored */ }
  }
%>
