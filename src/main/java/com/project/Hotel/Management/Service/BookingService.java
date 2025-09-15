package com.project.Hotel.Management.Service;

import com.project.Hotel.Management.Repository.BookingRepository;
import com.project.Hotel.Management.Repository.RoomRepository;
import com.project.Hotel.Management.Repository.TransactionRepository;
import com.project.Hotel.Management.Util.InputUtil;
import com.project.Hotel.Management.model.Booking;
import com.project.Hotel.Management.model.Room;
import com.project.Hotel.Management.model.Transaction;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.project.Hotel.Management.Util.ConsoleColor.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final TransactionRepository transactionRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, TransactionRepository transactionRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.transactionRepository = transactionRepository;
    }


    public void bookRoom() throws SQLException {
        System.out.println(BOLD + CYAN+"\n--- Room Booking ---"+RESET);
        List<Room> availableRooms = roomRepository.findByisAvailableTrue();
        if (availableRooms.isEmpty()) {
            System.out.println(RED + "Sorry, currently no rooms available!!!" + RESET);
            return;
        } else {
            for (Room room : availableRooms) {
                System.out.println(
                        YELLOW + "Room Number: " + RESET + room.getRoomNumber() + ", " +
                                YELLOW + "Type: " + RESET + room.getType() + ", " +
                                YELLOW + "Price: " + RESET + room.getPrice()
                );
            }
        }

        int roomNumber = InputUtil.getInt("Enter room number to book: ");

        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println(RED+"Room not available or doesn't exist."+RESET);
        }
        else {
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

            room.setAvailable(false);
            roomRepository.save(room);

            String bookingnum = bookingNumber();

            Booking booking = new Booking();
            booking.setCustomerName(name);
            booking.setBookingNumber(bookingnum);
            booking.setEmail(email);
            booking.setPhoneNumber(phone);
            booking.setRoomNumber(roomNumber);
            booking.setType(room.getType());
            booking.setPrice(room.getPrice());
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);

            bookingRepository.save(booking);
            System.out.println(GREEN+"Your room booked successfully and your booking id is !"+RESET+bookingnum);
        }
    }

    public void viewBooking() throws SQLException {
        String bookingNumber = InputUtil.getString("Enter your booking number: ");
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber);
        if (booking != null) {
            System.out.println(YELLOW+"Customer Name: "+RESET+booking.getCustomerName()+", "
                    +YELLOW+"Room Number: "+RESET+booking.getRoomNumber()+", "
                    +YELLOW+"Booking Number: "+RESET+booking.getBookingNumber()+", "
                    +YELLOW+"Type: "+RESET+booking.getType()+", "
                    +YELLOW+"Price: "+RESET+booking.getPrice()+", "
                    +YELLOW+"Phone Number: "+RESET+booking.getPhoneNumber()+", "
                    +YELLOW+"Check in: "+RESET+booking.getCheckIn()+", "
                    +YELLOW+"Check out: "+RESET+booking.getCheckOut());

        } else {
            System.out.println(RED+"No booking found with that email."+RESET);
        }
    }

    public void makePayment() throws SQLException {
        String bookingNumber = InputUtil.getString("Enter your Booking id: ");

        Booking booking = bookingRepository.findByBookingNumber(bookingNumber);

        if (booking != null) {
            double price = booking.getPrice();
            LocalDateTime checkIn = booking.getCheckIn();
            LocalDateTime checkOut = booking.getCheckOut();

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
                try{
                    Transaction transaction = new Transaction(booking.getRoomNumber(), total_price, paymentMethod, bookingNumber);
                    transactionRepository.save(transaction);
                    Room room = new Room(booking.getRoomNumber(),true);
                    System.out.println(GREEN+"Room has been checked out. Visit again!"+RESET);
                } catch (Exception e) {
                    System.out.println(RED+"Invalid details"+RESET);
                }
            } else {
                System.out.println(RED+"No booking found with that booking id."+RESET);
            }
        }
    }

    public void cancelMyroom() throws SQLException {
        String bookingId = InputUtil.getString("Enter your booking number:");

        if (bookingRepository.deleteByBookingNumber(bookingId) > 0) {
            System.out.println(GREEN+"Room cancelled successfully"+RESET);
        } else {
            System.out.println(RED+"Invalid booking number or no record is found!!!"+RESET);
        }
    }

    private String bookingNumber() throws SQLException {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1);

        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(10));
        }

        if(bookingRepository.existsByBookingNumber(sb.toString())) {
            bookingNumber();
        }
        return sb.toString();

    }
}
