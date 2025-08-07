package Service;

import DAO.AdminDAO;
import DAO.UserDAO;

import java.sql.SQLException;

public class UserService {

    public static boolean login(String username, String password) throws SQLException {
        return UserDAO.validateUser(username, password);
    }

    public static boolean SignupUser(String username, String password) throws SQLException {
        if(!UserDAO.existingUserorNot(username,password))
           return UserDAO.SignupUser(username,password);
        else
            return false;
    }

    public static void viewAvailableRooms() throws SQLException {
        System.out.println("\n--- Available Rooms ---");
        for (var room : DAO.RoomDAO.getAvailableRooms()) {
            if(room != null)
                System.out.println(room);
            else
                System.out.println("Sorry currently no rooms available!!!");
        }
    }

    public static void bookRoom() throws SQLException {
        Service.BookingService.bookRoom();
    }

    public static void viewMyBooking() throws SQLException {
        Service.BookingService.viewBooking();
    }

    public static void makePayment() throws  SQLException {
        Service.BookingService.makePayment();
    }

    public static void cancelMyroom() throws  SQLException {
        Service.BookingService.cancelMyroom();
    }

}