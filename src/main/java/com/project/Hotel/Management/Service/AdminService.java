package com.project.Hotel.Management.Service;

import com.project.Hotel.Management.Repository.AdminRepository;
import com.project.Hotel.Management.Repository.BookingRepository;
import com.project.Hotel.Management.Repository.RoomRepository;
import com.project.Hotel.Management.Util.InputUtil;
import com.project.Hotel.Management.model.Booking;
import com.project.Hotel.Management.model.Room;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static com.project.Hotel.Management.Util.ConsoleColor.*;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    //Construction injection
    public AdminService(AdminRepository adminRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.adminRepository = adminRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public boolean login(String username, String password) throws SQLException {
        return adminRepository.existsByUsername(username);
    }

    public void addRoom() throws SQLException {
        int roomNumber = InputUtil.getInt("Enter room number: ");
        String type = InputUtil.getString("Enter room type: ");
        double price = InputUtil.getDouble("Enter price: ");

        Room room = new Room(roomNumber, type, price, true);
        try {
            roomRepository.save(room);
            System.out.println(GREEN+"Room added successfully"+RESET);
        } catch (Exception e) {
            System.out.println(RED+"Error occurred in the db "+e.getMessage()+RESET);
        }

    }

    public void removeRoom() throws SQLException {
        int roomNumber = InputUtil.getInt("Enter room number to remove: ");
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            roomRepository.deleteByRoomNumber(roomNumber);
            System.out.println(GREEN + "Room removed successfully" + RESET);
        } else {
            System.out.println(RED + "Invalid room number!!!" + RESET);
        }
    }

    public void viewAllRooms() throws SQLException {
        System.out.println(BOLD + CYAN + "\n--- Available Rooms ---" + RESET);

        List<Room> availableRooms = roomRepository.findByisAvailableTrue();
        if (availableRooms.isEmpty()) {
            System.out.println(RED + "Sorry, currently no rooms available!!!" + RESET);
        } else {
            for (Room room : availableRooms) {
                System.out.println(
                        YELLOW + "Room Number: " + RESET + room.getRoomNumber() + ", " +
                                YELLOW + "Type: " + RESET + room.getType() + ", " +
                                YELLOW + "Price: " + RESET + room.getPrice()
                );
            }
        }
    }


    public void viewAllBookings() throws SQLException {
        System.out.println(BOLD + CYAN+"\n--- All Bookings ---"+RESET);

        List<Booking> availableBookings = bookingRepository.findAll();
        if (availableBookings.isEmpty()) {
            System.out.println(RED + "Sorry, currently no bookings available!!!" + RESET);
        } else {
            for (Booking booking : availableBookings) {
                System.out.println(
                                YELLOW+"Customer Name: "+RESET+booking.getCustomerName()+", "
                                +YELLOW+"Room Number: "+RESET+booking.getRoomNumber()+", "
                                +YELLOW+"Booking Number: "+RESET+booking.getBookingNumber()+", "
                                +YELLOW+"Email: "+RESET+booking.getEmail()+", "
                                +YELLOW+"Type: "+RESET+booking.getType()+", "
                                +YELLOW+"Price: "+RESET+booking.getPrice()+", "
                                +YELLOW+"Phone Number: "+RESET+booking.getPhoneNumber()+", "
                                +YELLOW+"Check in: "+RESET+booking.getCheckIn()+", "
                                +YELLOW+"Check out: "+RESET+booking.getCheckOut()
                        );
            }
        }
    }
}
