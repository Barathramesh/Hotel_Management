package Service;

import DAO.AdminDAO;
import DAO.BookingDAO;
import DAO.RoomDAO;
import Model.Room;
import Util.InputUtil;

import java.sql.SQLException;

public class AdminService {
    public static boolean login(String username, String password) throws SQLException {
        return AdminDAO.validateAdmin(username, password);
    }

    public static void addRoom() throws SQLException {
        int roomNumber = InputUtil.getInt("Enter room number: ");
        String type = InputUtil.getString("Enter room type: ");
        double price = InputUtil.getDouble("Enter price: ");
        Room room = new Room(roomNumber, type, price, true);
        int res = RoomDAO.addRoom(room);
        if(res > 0)
            System.out.println("Room added successfully");
        else
            System.out.println("Error occurred in the values!!!");
    }

    public static void removeRoom() throws SQLException {
        int roomNumber = InputUtil.getInt("Enter room number to remove: ");
        boolean res = RoomDAO.removeRoom(roomNumber);
        if(res)
            System.out.println("Room removed successfully");
        else
            System.out.println("Invalid room number!!!");
    }

    public static void viewAllRooms() throws SQLException {
        System.out.println("\n--- All Rooms ---");
        RoomDAO.getAllRooms();
        for (var rooms : RoomDAO.getAllRooms()) {
            System.out.println(rooms);
        }
    }

    public static void viewAllBookings() throws SQLException {
        System.out.println("\n--- All Bookings ---");
        for (var booking : BookingDAO.getAllBookings()) {
            System.out.println(booking);
        }
    }
}