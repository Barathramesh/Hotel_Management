package com.project.Hotel.Management.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private String bookingNumber;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String paymentMethod;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Transaction(int roomNumber, double totalPrice, String paymentMethod, String bookingNumber) {
        this.roomNumber = roomNumber;
        this.price = totalPrice;
        this.paymentMethod = paymentMethod;
        this.bookingNumber = bookingNumber;
    }
}
