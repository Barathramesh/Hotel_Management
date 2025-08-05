package View;

import Util.InputUtil;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        boolean exit = false;

        System.out.println("===================================");
        System.out.println("  Welcome to Hotel Booking System");
        System.out.println("===================================");

        while (!exit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login as Admin");
            System.out.println("2. Continue as Guest");
            System.out.println("3. Exit");
            int choice = InputUtil.getInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    AdminMenu.showAdminMenu();
                    break;
                case 2:
                    UserMenu.showUserMenu();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Thank you for using our Hotel Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
