package Model;

public class Booking {
    private final String customerName;
    private final String email;
    private final int roomNumber;
    private final Long phoneNumber;
    private final String type;
    private final Double price;

    public Booking( int roomNumber, String type, Double price, String name, Long phoneNumber, String email) {

        this.customerName = name;
        this.email = email;
        this.roomNumber = roomNumber;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Email: " + email + ", Phone Number: "
        + phoneNumber +", Room #: " + roomNumber +", Room Type: " + type +", Price: " + price;
    }
}