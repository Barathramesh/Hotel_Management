package View;

import Service.AdminService;
import Util.InputUtil;

import java.sql.SQLException;

public class AdminMenu {
    public static void showAdminMenu() throws SQLException {
        System.out.println("\n--- Admin Login ---");
        String username = InputUtil.getString("Username: ");
        String password = InputUtil.getString("Password: ");

        if (!AdminService.login(username, password)) {
            System.out.println("Login failed.Invalid username or password!!!");
            return;
        }

        boolean flag = false;
        while (!flag) {
            System.out.println("\n------ Admin Menu -----");
            System.out.println("1. Add Room");
            System.out.println("2. Remove Room");
            System.out.println("3. View All Rooms");
            System.out.println("4. View All Bookings");
            System.out.println("5. Logout");
            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1 -> AdminService.addRoom();
                case 2 -> AdminService.removeRoom();
                case 3 -> AdminService.viewAllRooms();
                case 4 -> AdminService.viewAllBookings();
                case 5 -> flag = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}