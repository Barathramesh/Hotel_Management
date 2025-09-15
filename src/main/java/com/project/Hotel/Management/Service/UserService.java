package com.project.Hotel.Management.Service;

import com.project.Hotel.Management.Repository.RoomRepository;
import com.project.Hotel.Management.Repository.UserRepository;
import com.project.Hotel.Management.model.Room;
import com.project.Hotel.Management.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import static com.project.Hotel.Management.Util.ConsoleColor.*;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingService bookingService;

    // Constructor injection
    public UserService(UserRepository userRepository, RoomRepository roomRepository, BookingService bookingService) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }

    public boolean login(String username, String password) throws SQLException {
        return userRepository.existsByUsername(username);
    }

    public boolean SignupUser(String username, String password) throws SQLException {
        if(!userRepository.existsByUsername(username)) {
            User user = new User(username,password);
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }


    public void viewAvailableRooms() throws SQLException {
        System.out.println(BOLD + CYAN+"\n--- Available Rooms ---"+RESET);
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

    public void bookRoom() throws SQLException {
        bookingService.bookRoom();
    }

    public void viewMyBooking() throws SQLException {
        bookingService.viewBooking();
    }

    public void makePayment() throws  SQLException {
        bookingService.makePayment();
    }

    public void cancelMyroom() throws SQLException {
        bookingService.cancelMyroom();
    }
}
