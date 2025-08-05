package Service;

import DAO.AdminDAO;
import DAO.UserDAO;

import java.sql.SQLException;

public class UserService {

    public static boolean login(String username, String password) throws SQLException {
        return UserDAO.validateUser(username, password);
    }

    public static boolean SignupUser(String username, String password) throws SQLException {
        return UserDAO.SignupUser(username,password);
    }

    public static void viewAvailableRooms() throws SQLException {
        System.out.println("\n--- Available Rooms ---");
        for (var room : DAO.RoomDAO.getAvailableRooms()) {
            System.out.println(room);
        }
    }

    public static void bookRoom() throws SQLException {
        Service.BookingService.bookRoom();
    }

    public static void viewMyBooking() throws SQLException {
        Service.BookingService.viewBooking();
    }
}