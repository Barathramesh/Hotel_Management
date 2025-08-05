package DAO;

import Util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean validateUser(String username, String password) throws SQLException {
        String query = "Select * from users where username = ?";
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

    public static boolean SignupUser(String username, String password) throws SQLException {
        String query = "Insert into users(username,password) values(?, ?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        pst.setString(2,password);
        int rows = pst.executeUpdate();

        return rows > 0;
    }

}
