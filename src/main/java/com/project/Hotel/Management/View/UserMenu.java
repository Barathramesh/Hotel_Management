package com.project.Hotel.Management.View;

import com.project.Hotel.Management.Service.UserService;
import com.project.Hotel.Management.Util.InputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import static com.project.Hotel.Management.Util.ConsoleColor.*;

@Component
public class UserMenu {

    @Autowired
    UserService userService;


    public void showUserMenu() throws SQLException {
        while(true) {
            System.out.println(BOLD + CYAN+"\n--- User Login ---"+RESET);
            System.out.println("1. Log in.");
            System.out.println("2. Sign up.");

            int choice = InputUtil.getInt("Enter your choice:");
            if(choice == 1) {
                String username = InputUtil.getString("Username: ");
                String password = InputUtil.getString("Password: ");

                if (!userService.login(username, password)) {
                    System.out.println(RED+"Login failed.Invalid username or password!!!"+RESET);
                } else {
                    break;
                }
            } else if (choice == 2) {
                String username = InputUtil.getString("Enter your name: ");
                String password = InputUtil.getString("Enter your password: ");

                if(userService.SignupUser(username,password))
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

            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> userService.viewAvailableRooms();
                case 2 -> userService.bookRoom();
                case 3 -> userService.viewMyBooking();
                case 4 -> userService.makePayment();
                case 5 -> userService.cancelMyroom();
                case 6 -> flag = true;
                default -> System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }
}
