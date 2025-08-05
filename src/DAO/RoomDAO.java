package DAO;

import Model.Room;
import Util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final List<Room> roomList = new ArrayList<>();

    public static int addRoom(Room room) throws SQLException {
        String query = "Insert into rooms(room_number,type,price,is_available) values (?,?,?,?)";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, room.getRoomNumber());
        pst.setString(2, room.getType());
        pst.setDouble(3, room.getPrice());
        pst.setBoolean(4, room.isAvailable());

        return pst.executeUpdate();
    }

    public static boolean removeRoom(int roomNumber) throws SQLException {
        String query = "DELETE FROM rooms WHERE room_number = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, roomNumber);
        int rowsAffected = pst.executeUpdate();

        return rowsAffected > 0;
    }

    public static List<Room> getAllRooms() throws SQLException {
        List<Room> allrooms = new ArrayList<>();
        String query = "Select * FROM rooms";
        Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            int roomNumber = rs.getInt(1);
            String type = rs.getString(2);
            double price = rs.getDouble(3);
            boolean isAvailable = rs.getBoolean(4);

            Room room = new Room(roomNumber, type, price, isAvailable);
            allrooms.add(room);
        }
        return allrooms;
    }

    public static List<Room> getAvailableRooms() throws SQLException {
        List<Room> available = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE is_available = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setBoolean(1, true);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int roomNumber = rs.getInt(1);
            String type = rs.getString(2);
            double price = rs.getDouble(3);
            boolean isAvailable = rs.getBoolean(4);
            Room room = new Room(roomNumber, type, price, isAvailable);
            available.add(room);
        }

       return available;
    }


//    public static Room getRoomByNumber(int roomNumber) {
//
//        for (Room room : roomList) {
//            if (room.getRoomNumber() == roomNumber) return room;
//        }
//        return null;
//    }

    public static Room getRoomByNumber(int roomNumber) throws SQLException {
        String query = "SELECT * FROM rooms WHERE room_number = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, roomNumber);
        ResultSet rs = pst.executeQuery();

        Room room = null;
        while (rs.next()) {
            int roomNum = rs.getInt(1);
            String type = rs.getString(2);
            double price = rs.getDouble(3);
            boolean isAvailable = rs.getBoolean(4);
            room = new Room(roomNum, type, price, isAvailable);
        }
        return room;
    }

    public static void setAvailable(int roomNumber) throws SQLException {
        String query = "Update rooms SET is_available = ? WHERE room_number = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setBoolean(1, false);
        pst.setInt(2, roomNumber);
        pst.executeUpdate();
    }
}