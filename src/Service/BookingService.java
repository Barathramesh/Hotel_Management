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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime checkIn;
                LocalDateTime checkOut;

                while (true) {
                    try {
                        String checkInStr = InputUtil.getString("Enter check-in datetime (DD-MM-YYYY HH:MM): ");
                        checkIn = LocalDateTime.parse(checkInStr, formatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("nvalid date-time format. Try again.");
                    }
                }

                while (true) {
                    try {
                        String checkOutStr = InputUtil.getString("Enter check-out datetime (DD-MM-YYYY HH:MM): ");
                        checkOut = LocalDateTime.parse(checkOutStr, formatter);

                        if (!checkOut.isAfter(checkIn)) {
                            System.out.println("Check-out must be after check-in (" + checkIn.format(formatter) + ")");
                        } else {
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date-time format. Try again.");
                    }
                }
            System.out.println(java.time.Duration.between(checkIn, checkOut).toHours());

            RoomDAO.setAvailable(false,roomNumber);

            BookingDAO.addBooking(name, email, roomNumber, phone, room.getType(), room.getPrice(), checkIn, checkOut);

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

    public static void makePayment() throws SQLException {
        String email = InputUtil.getString("Enter your email: ");
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
                System.out.println("Cash payment received. Thank you for staying with us!");
            } else if (choice == 2) {
                System.out.println("Redirecting to Online Payment Gateway...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paymentMethod = "Online";
                paymentSuccess = true;
                System.out.println("Online payment successful. Thank you for using our service!");
            } else {
                System.out.println("Invalid payment option. Payment failed.");
            }

            if (paymentSuccess) {
                boolean row = BookingDAO.insertTransaction(email, booking.getRoomNumber(), total_price, paymentMethod);
                if (row) {
                    RoomDAO.setAvailable(true, booking.getRoomNumber());
                    System.out.println("Room has been checked out. Visit again!");
                } else {
                    System.out.println("Invalid details");
                }

            } else {
                System.out.println("No booking found with that email.");

            }
        }

    }

    public static void cancelMyroom() throws  SQLException {
        int roomNumber = InputUtil.getInt("Enter room number:");
        String email = InputUtil.getString("Enter your email:");

        if(BookingDAO.cancelMyroom(roomNumber,email)) {
            System.out.println("Room cancelled successfully");
        } else {
            System.out.println("Invalid details!!!");
        }
    }
}