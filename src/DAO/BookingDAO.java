package DAO;

import Model.Booking;
import Model.Room;
import Util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private static final List<Booking> bookings = new ArrayList<>();
    private static int bookingCounter = 1;

    public static void addBooking(String name, String email, int roomNumber, Long phoneNumber, String type, Double price ) throws SQLException {
        String query = "Insert into booking (room_number,type,price,name,mobile_number,email) values (?,?,?,?,?,?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, roomNumber);
        pst.setString(2, type);
        pst.setDouble(3, price);
        pst.setString(4, name);
        pst.setLong(5, phoneNumber);
        pst.setString(6, email);
        pst.executeUpdate();
    }

    public static List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookingdao = new ArrayList<>();
        String query = "SELECT * FROM booking";
        Connection con = DbConnection.getConnection();
        Statement pst = con.createStatement();
        ResultSet rs = pst.executeQuery(query);


        while (rs.next()) {
            int roomNumber = rs.getInt(2);
            String type = rs.getString(3);
            double price = rs.getDouble(4);
            String name = rs.getString(5);
            Long mobileNum = rs.getLong(6);
            String curr_email = rs.getString(7);
            Booking booking  = new Booking(roomNumber, type, price, name, mobileNum, curr_email);
            bookingdao.add(booking);
        }
        return bookingdao;
    }

    public static Booking getBookingByEmail(String email) throws SQLException {
        String query = "SELECT * FROM booking WHERE email = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();

        Booking booking = null;
        while (rs.next()) {
            int roomNumber = rs.getInt(2);
            String type = rs.getString(3);
            double price = rs.getDouble(4);
            String name = rs.getString(5);
            Long mobileNum = rs.getLong(6);
            String curr_email = rs.getString(7);
            booking = new Booking(roomNumber, type, price, name, mobileNum, curr_email);
        }
        return booking;
    }
}