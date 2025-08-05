package DAO;

import Util.DbConnection;

import java.sql.*;

public class AdminDAO {

    public static boolean validateAdmin(String username, String password) throws SQLException {
        String query = "Select * from admins where username = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            String Originalpsw = rs.getString(3);
            if(Originalpsw.equals(password)) {
                return true;
            }
        }
        return false;
    }
}
