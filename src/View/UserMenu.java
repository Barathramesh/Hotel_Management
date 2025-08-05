package View;

import Service.AdminService;
import Service.UserService;
import Util.InputUtil;

import java.sql.SQLException;

public class UserMenu {
    public static void showUserMenu() throws SQLException {
        while(true) {
            System.out.println("1. Log in.");
            System.out.println("2. Sign up.");
            int choice = InputUtil.getInt("Enter your choice:");
            if(choice == 1) {
                String username = InputUtil.getString("Username: ");
                String password = InputUtil.getString("Password: ");
                if (!UserService.login(username, password)) {
                    System.out.println("Login failed.Invalid username or password!!!");
                    continue;
                } else {
                    break;
                }
            } else if (choice == 2) {
                String username = InputUtil.getString("Enter your name: ");
                String password = InputUtil.getString("Enter your password: ");
                if(UserService.SignupUser(username,password))
                    System.out.println("You have successfully signed in now Login into the system.");
                else
                    System.out.println("Please try again later");
            }
        }

        boolean flag = false;
        while (!flag) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View My Booking");
            System.out.println("4. Exit to Main Menu");
            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> UserService.viewAvailableRooms();
                case 2 -> UserService.bookRoom();
                case 3 -> UserService.viewMyBooking();
                case 4 -> flag = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}