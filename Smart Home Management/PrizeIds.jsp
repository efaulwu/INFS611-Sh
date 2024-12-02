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
      rs = stmt.executeQuery("SELECT P.prize_id, C.cid FROM Customers C JOIN redemption_history R ON C.cid = R.cid and R.cid = '" + cid + "' JOIN prizes P ON R.prize_id = P.Prize_id");

      String output = "";
      while (rs.next()) {
          output += rs.getObject(1) + "#";
      }

      if (!(output.trim().isEmpty())) {
          out.print(output);
      } else {
          out.print("No records found");
      }
  } catch (Exception e) {
        out.print("SQLERROR");
  } finally {
      if (rs != null) try { rs.close(); } catch (SQLException ignored) { }
      if (stmt != null) try { stmt.close(); } catch (SQLException ignored) { }
      if (conn != null) try { conn.close(); } catch (SQLException ignored) { }
  }
%>

