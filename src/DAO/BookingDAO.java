package DAO;

import Model.Booking;
import Util.DbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static void addBooking(String name, String email, int roomNumber, Long phoneNumber, String type, Double price,
                                  LocalDateTime checkIn, LocalDateTime checkOut) throws SQLException {

        String query = "Insert into bookings (room_number,type,price,name,mobile_number,email,checkin,checkout) values (?,?,?,?,?,?,?,?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, roomNumber);
        pst.setString(2, type);
        pst.setDouble(3, price);
        pst.setString(4, name);
        pst.setLong(5, phoneNumber);
        pst.setString(6, email);
        pst.setTimestamp(7, Timestamp.valueOf(checkIn));
        pst.setTimestamp(8, Timestamp.valueOf(checkOut));
        pst.executeUpdate();
    }

    public static List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookingdao = new ArrayList<>();
        String query = "SELECT * FROM bookings";
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
            Timestamp checkinTS = rs.getTimestamp(8);
            Timestamp checkoutTS = rs.getTimestamp(9);

            LocalDateTime checkin = checkinTS.toLocalDateTime();
            LocalDateTime checkout = checkoutTS.toLocalDateTime();

            Booking booking  = new Booking(roomNumber, type, price, name, mobileNum, curr_email,checkin,checkout);
            bookingdao.add(booking);
        }
        return bookingdao;
    }

    public static Booking getBookingByEmail(String email) throws SQLException {
        String query = "SELECT * FROM bookings WHERE email = ?";
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
            Timestamp checkinTS = rs.getTimestamp(8);
            Timestamp checkoutTS = rs.getTimestamp(9);

            LocalDateTime checkin = checkinTS.toLocalDateTime();
            LocalDateTime checkout = checkoutTS.toLocalDateTime();

            booking = new Booking(roomNumber, type, price, name, mobileNum, curr_email,checkin,checkout);
        }
        return booking;
    }

    public static boolean insertTransaction(String email, int roomNumber, double price, String paymentMethod) throws SQLException {
        String query = "Insert into transactions(email, room_number, amount, payment_method, payment_time) " +
                "Values(?,?,?,?,?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1,email);
        pst.setInt(2,roomNumber);
        pst.setDouble(3,price);
        pst.setString(4,paymentMethod);
        pst.setTimestamp(5,Timestamp.valueOf(LocalDateTime.now()));

        return pst.executeUpdate() > 0;
    }

    public static boolean cancelMyroom(int roomNumber, String email) throws SQLException {
        String query = "Delete FROM bookings WHERE email = ? AND room_number = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, email);
        pst.setInt(2, roomNumber);
        int rows = pst.executeUpdate();
        return rows > 0;
    }


}