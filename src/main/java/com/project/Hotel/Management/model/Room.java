package com.project.Hotel.Management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean isAvailable;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Room(int roomNumber, String type, double price, boolean b) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.type = type;
        this.isAvailable = b;
    }

    public Room(int roomNumber, boolean b) {
        this.roomNumber = roomNumber;
        this.isAvailable = b;
    }

    public void setAvailable(boolean b, int roomNumber) {
        this.isAvailable = b;
        this.roomNumber = roomNumber;
    }
}
