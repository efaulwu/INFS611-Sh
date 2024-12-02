<%@page import="java.sql.*"%>
<%
  String tref = request.getParameter("tref");
  Connection conn = null;
  Statement stmt = null;
  ResultSet rs = null;

  try {
      DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
      conn = DriverManager.getConnection(url, "rkoppolu", "aptooshi");
      stmt = conn.createStatement();
      String query = "SELECT t.tref, TO_CHAR(t.t_date, 'DD-MON-YY'), t.t_points, p.prod_name, p.prod_points, tp.quantity FROM transactions t, transactions_products tp, products p WHERE t.tref = tp.tref AND p.prod_id = tp.prod_id AND t.tref = '" + tref + "'";
      rs = stmt.executeQuery(query);

      String output = "";
      while (rs.next()) {
          output += rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(4) + "," + rs.getObject(5) + "," + rs.getObject(6) + "#";
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
