package Service;

import DAO.BookingDAO;
import DAO.RoomDAO;
import Model.Booking;
import Model.Room;
import Util.InputUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import static Util.ConsoleColor.*;

public class BookingService {

    public static void bookRoom() throws SQLException {
        System.out.println(BOLD + CYAN+"\n--- Room Booking ---"+RESET);
        for (Room room : RoomDAO.getAvailableRooms()) {
            System.out.println(room);
        }

        int roomNumber = InputUtil.getInt("Enter room number to book: ");

        Room room = RoomDAO.getRoomByNumber(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println(RED+"Room not available or doesn't exist."+RESET);
        } else {
            String name = InputUtil.getString("Enter your name: ");
            String email = InputUtil.getValidEmail("Enter your email: ");
            Long phone = InputUtil.getValidPhone("Enter your phone: ");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime checkIn;
            LocalDateTime checkOut;

            while (true) {
                try {
                    String checkInStr = InputUtil.getString("Enter check-in datetime (DD-MM-YYYY HH:MM): ");
                    checkIn = LocalDateTime.parse(checkInStr, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println(RED+"Invalid date-time format. Try again."+RESET);
                }
            }

            while (true) {
                try {
                    String checkOutStr = InputUtil.getString("Enter check-out datetime (DD-MM-YYYY HH:MM): ");
                    checkOut = LocalDateTime.parse(checkOutStr, formatter);

                    if (!checkOut.isAfter(checkIn)) {
                        System.out.println(RED+"Check-out must be after check-in (" + checkIn.format(formatter) + ")"+RESET);
                    } else {
                        break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println(RED+"Invalid date-time format."+RESET);
                }
            }
            System.out.println(java.time.Duration.between(checkIn, checkOut).toHours());

            RoomDAO.setAvailable(false, roomNumber);

            BookingDAO.addBooking(name, email, roomNumber, phone, room.getType(), room.getPrice(), checkIn, checkOut);

            System.out.println(GREEN+"Booking successful!"+RESET);
        }
    }

    public static void viewBooking() throws SQLException {
        String email = InputUtil.getValidEmail("Enter your email: ");
        Booking booking = BookingDAO.getBookingByEmail(email);
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println(RED+"No booking found with that email."+RESET);
        }
    }

    public static void makePayment() throws SQLException {
        String email = InputUtil.getValidEmail("Enter your email: ");
        Booking booking = BookingDAO.getBookingByEmail(email);
        if (booking != null) {
            double price = booking.getPrice();
            LocalDateTime checkIn = booking.getCheckin();
            LocalDateTime checkOut = booking.getCheckout();

            long total_hours = java.time.Duration.between(checkIn, checkOut).toHours();
            double total_price = 0;
            double hourlyRate = price / 24.0;

            if (total_hours <= 24) {
                total_price = price;
            } else {
                total_price = price + (total_hours - 24) * hourlyRate;
            }

            System.out.println("Total hours you stayed: " + total_hours);
            System.out.println("Total amount to pay: ₹" + total_price);
            System.out.println("Choose a payment option:");
            System.out.println("1. Cash");
            System.out.println("2. Online Payment");

            int choice = InputUtil.getInt("Enter your choice: ");
            String paymentMethod = "";
            boolean paymentSuccess = false;

            if (choice == 1) {
                paymentMethod = "Cash";
                paymentSuccess = true;
                System.out.println(GREEN+"Cash payment received. Thank you for staying with us!"+RESET);
            } else if (choice == 2) {
                System.out.println(YELLOW+"Redirecting to Online Payment Gateway..."+RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paymentMethod = "Online";
                paymentSuccess = true;
                System.out.println(GREEN+"Online payment successful. Thank you for using our service!"+RESET);
            } else {
                System.out.println(RED+"Invalid payment option. Payment failed."+RESET);
            }

            if (paymentSuccess) {
                boolean row = BookingDAO.insertTransaction(email, booking.getRoomNumber(), total_price, paymentMethod);
                if (row) {
                    RoomDAO.setAvailable(true, booking.getRoomNumber());
                    System.out.println(GREEN+"Room has been checked out. Visit again!"+RESET);
                } else {
                    System.out.println(RED+"Invalid details"+RESET);
                }
            } else {
                System.out.println(RED+"No booking found with that email."+RESET);
            }
        }
    }

    public static void cancelMyroom() throws SQLException {
        int roomNumber = InputUtil.getInt("Enter room number:");
        String email = InputUtil.getValidEmail("Enter your email:");

        if (BookingDAO.cancelMyroom(roomNumber, email)) {
            System.out.println(GREEN+"Room cancelled successfully"+RESET);
        } else {
            System.out.println(RED+"Invalid details!!!"+RESET);
        }
    }
}