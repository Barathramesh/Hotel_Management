package com.project.Hotel.Management.View;

import com.project.Hotel.Management.Service.AdminService;
import com.project.Hotel.Management.Util.InputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static com.project.Hotel.Management.Util.ConsoleColor.*;

@Component
public class AdminMenu {

    @Autowired
    AdminService adminService;

    public void showAdminMenu() throws SQLException {

        System.out.println(BOLD + CYAN+"\n--- Admin Login ---"+RESET);
        String username = InputUtil.getString("Username: ");
        String password = InputUtil.getString("Password: ");

        if (!adminService.login(username, password)) {
            System.out.println(RED+"Login failed.Invalid username or password!!!"+RESET);
            return;
        }

        boolean flag = false;
        while (!flag) {
            System.out.println(BOLD + CYAN+"\n------ Admin Menu -----"+RESET);
            System.out.println("1. Add Room");
            System.out.println("2. Remove Room");
            System.out.println("3. View All Rooms");
            System.out.println("4. View All Bookings");
            System.out.println("5. Logout");
            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> adminService.addRoom();
                case 2 -> adminService.removeRoom();
                case 3 -> adminService.viewAllRooms();
                case 4 -> adminService.viewAllBookings();
                case 5 -> flag = true;
                default -> System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }
}
