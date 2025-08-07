package View;

import Service.UserService;
import Util.InputUtil;

import java.sql.SQLException;

import static Util.ConsoleColor.*;

public class UserMenu {
    public static void showUserMenu() throws SQLException {
        while(true) {
            System.out.println(BOLD + CYAN+"\n--- User Login ---"+RESET);
            System.out.println("1. Log in.");
            System.out.println("2. Sign up.");
            int choice = InputUtil.getInt("Enter your choice:");
            if(choice == 1) {
                String username = InputUtil.getString("Username: ");
                String password = InputUtil.getString("Password: ");
                if (!UserService.login(username, password)) {
                    System.out.println(RED+"Login failed.Invalid username or password!!!"+RESET);
                    continue;
                } else {
                    break;
                }
            } else if (choice == 2) {
                String username = InputUtil.getString("Enter your name: ");
                String password = InputUtil.getString("Enter your password: ");
                if(UserService.SignupUser(username,password))
                    System.out.println(GREEN+"You have successfully signed in now Login into the system."+RESET);
                else
                    System.out.println(YELLOW+"You have already signed in please login!!"+RESET);
            }
        }

        boolean flag = false;
        while (!flag) {
            System.out.println(BOLD + CYAN+"\n--- User Menu ---"+RESET);
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room"+RESET);
            System.out.println("3. View My Booking"+RESET);
            System.out.println("4. Make Payment and Checkout"+RESET);
            System.out.println("5. Cancel My Booking"+RESET);
            System.out.println("6. Exit to Main Menu"+RESET);
            ;
            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> UserService.viewAvailableRooms();
                case 2 -> UserService.bookRoom();
                case 3 -> UserService.viewMyBooking();
                case 4 -> UserService.makePayment();
                case 5 -> UserService.cancelMyroom();
                case 6 -> flag = true;
                default -> System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }
}