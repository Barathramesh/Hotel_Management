package com.project.Hotel.Management.Util;

import java.util.Scanner;
import java.util.regex.Pattern;

import static com.project.Hotel.Management.Util.ConsoleColor.*;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt(String input) {
        while (true) {
            System.out.print(BOLD+PURPLE+input+RESET);
            try {
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println(RED+"Invalid input. Please enter a valid number."+RESET);
            }
        }
    }

    public static String getString(String input) {
        System.out.print(BOLD+PURPLE+input+RESET);
        return scanner.nextLine();
    }

    public static double getDouble(String prompt) {
        while (true) {
            System.out.print(BOLD+PURPLE+prompt+RESET);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED+"Invalid input. Please enter a valid decimal number."+RESET);
            }
        }
    }

    public static long getLong(String prompt) {
        while (true) {
            System.out.print(BOLD+PURPLE+prompt+RESET);
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED+"Invalid input. Please enter a valid long number."+RESET);
            }
        }
    }

    public static String getValidEmail(String msg) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail.com$";
        while (true) {
            String email = getString(msg);
            if (Pattern.matches(emailRegex, email)) {
                return email;
            } else {
                System.out.println(RED+"Invalid email format. Try again."+RESET);
            }
        }
    }

    public static long getValidPhone(String msg) {
        while (true) {
            long phone = getLong(msg);
            String phoneStr = Long.toString(phone);
            if (phoneStr.matches("^[6-9]\\d{9}$")) {
                return phone;
            } else {
                System.out.println(RED+"Invalid phone number. It must be 10 digits starting with 6-9."+RESET);
            }
        }
    }

}
