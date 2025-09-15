package com.project.Hotel.Management;

import com.project.Hotel.Management.Util.InputUtil;
import com.project.Hotel.Management.View.AdminMenu;
import com.project.Hotel.Management.View.UserMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static com.project.Hotel.Management.Util.ConsoleColor.*;

//@ComponentScan({"com.project.Hotel.Management", "View", "Service", "Repository", "Controller", "Model", "Util"})
@SpringBootApplication
public class HotelManagementApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(HotelManagementApplication.class);
		// Prevent Spring from closing after CommandLineRunner
		app.setRegisterShutdownHook(false);
		app.run(args);

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	@Autowired
	UserMenu userMenu;

	@Autowired
	AdminMenu adminMenu;

	@Bean
	public CommandLineRunner run() {

		return args -> {
			boolean exit = false;



			System.out.println(BOLD + WHITE+"-----------------------------------"+RESET);
			System.out.println(BOLD + BLUE +"  Welcome to Hotel Booking System"+RESET);
			System.out.println(BOLD + WHITE+"-----------------------------------"+RESET);

			while (!exit) {
				System.out.println(BOLD + CYAN+"\n--- Main Menu ---"+RESET);
				System.out.println("1. Login as Admin");
				System.out.println("2. Continue as Guest");
				System.out.println("3. Exit");
				int choice = InputUtil.getInt("Enter your choice: ");

				switch (choice) {
					case 1:
						adminMenu.showAdminMenu();
						break;
					case 2:
						userMenu.showUserMenu();
						break;
					case 3:
						exit = true;
						System.out.println("Thank you for using our Hotel Booking System!");
						break;
					default:
						System.out.println(RED+"Invalid choice. Please try again."+RESET);
				}
			}
		};
	}
}
