package DAO;

import Util.DbConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            String hashedpassword = rs.getString(3);
            if(BCrypt.checkpw(password,hashedpassword))
                return true;
        }
        return false;
    }

    public static boolean SignupUser(String username, String password) throws SQLException {
        String hashedpassword = BCrypt.hashpw(password,BCrypt.gensalt());
        String query = "Insert into users(username,password) values(?, ?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        pst.setString(2,hashedpassword);
        int rows = pst.executeUpdate();

        return rows > 0;
    }

    public static boolean existingUserorNot(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username =  ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,username);
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            String hashedpassword = rs.getString(3);
            if(BCrypt.checkpw(password,hashedpassword))
                 return true;
        }
        return false;
    }

}
