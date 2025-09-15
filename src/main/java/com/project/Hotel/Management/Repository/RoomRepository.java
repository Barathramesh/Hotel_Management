package com.project.Hotel.Management.Repository;

import com.project.Hotel.Management.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Modifying
    @Transactional
    void deleteByRoomNumber(int roomNumber);
    boolean existsByRoomNumber(int roomNumber);

    List<Room> findByisAvailableTrue();

    Room findByRoomNumber(int roomNumber);

//    void findAllByRoomNumber();
}
