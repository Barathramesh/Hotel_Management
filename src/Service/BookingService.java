package Service;

import DAO.BookingDAO;
import DAO.RoomDAO;
import Model.Booking;
import Model.Room;
import Util.InputUtil;

import java.sql.SQLException;

public class BookingService {

    public static void bookRoom() throws SQLException {
        System.out.println("\n--- Room Booking ---");
        for (Room room : RoomDAO.getAvailableRooms()) {
            System.out.println(room);
        }

        int roomNumber = InputUtil.getInt("Enter room number to book: ");

        Room room = RoomDAO.getRoomByNumber(roomNumber);

        if(room == null || !room.isAvailable()){
               System.out.println(" Room not available or doesn't exist.");
        } else {
            String name = InputUtil.getString("Enter your name: ");
            String email = InputUtil.getString("Enter your email: ");
            Long phone = InputUtil.getLong("Enter your phone: ");

            RoomDAO.setAvailable(roomNumber);

            BookingDAO.addBooking(name, email, roomNumber, phone, room.getType(), room.getPrice());

            System.out.println("Booking successful!");

        }

    }

    public static void viewBooking() throws SQLException {
        String email = InputUtil.getString("Enter your email: ");
        Booking booking = BookingDAO.getBookingByEmail(email);
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println("No booking found with that email.");
        }
    }
}