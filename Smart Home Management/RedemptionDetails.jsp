<%@page import="java.sql.*"%>
<%
    String prizeid = request.getParameter("prizeid");    
    String cid = request.getParameter("cid");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
        conn = DriverManager.getConnection(url, "rkoppolu", "aptooshi");
        stmt = conn.createStatement();
        String query = "SELECT p.prize_id, p.p_description, p.points_needed, to_char(r.r_date, 'DD-MON-YY'), r.prize_id, center_name, r.cid FROM Prizes p JOIN redemption_history r ON p.prize_id = r.Prize_id AND p.prize_id = '" + prizeid + "' JOIN exchgcenters e ON r.center_id = e.center_id AND r.cid = '" + cid + "'";
        rs = stmt.executeQuery(query);

        String output = "";
        while (rs.next()) {
            output += rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(4) + "," + rs.getObject(6) + "#";
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
