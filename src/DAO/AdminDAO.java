package DAO;

import Util.DbConnection;

import java.sql.*;

public class AdminDAO {

    public static boolean validateAdmin(String username, String password) throws SQLException {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";

        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        pst.setString(2,password);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
}
