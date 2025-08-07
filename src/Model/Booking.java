package Model;

import java.time.LocalDateTime;

public class Booking {
    private final String customerName;
    private final String email;
    private final int roomNumber;
    private final Long phoneNumber;
    private final String type;
    private final Double price;
    private LocalDateTime checkin;
    private LocalDateTime checkout;

    public Booking( int roomNumber, String type, Double price, String name, Long phoneNumber, String email,
                    LocalDateTime checkin, LocalDateTime checkout) {

        this.customerName = name;
        this.email = email;
        this.roomNumber = roomNumber;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.price = price;
        this.checkin = checkin;
        this.checkout = checkout;

    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public Double getPrice() {
        return price;
    }

    public String getEmail() {
        return email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Customer Name: " + customerName + ", Email: " + email + ", Phone Number: "
        + phoneNumber +", Room #: " + roomNumber +", Room Type: " + type + ",\nPrice: " + price
        + ", CheckIn: " + checkin + ", CheckOut: " + checkout;
    }
}