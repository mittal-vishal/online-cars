package com.intuit.craft.onlinecars.repository;

import com.intuit.craft.onlinecars.model.Car;
import com.intuit.craft.onlinecars.model.CarEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, CarEntityKey> {

    @Query(value = "SELECT * FROM car where id != ?1", nativeQuery = true)
    List<Car> findAllExceptCurrentID(String id);

    @Query(value = "DELETE FROM car where id != ?1", nativeQuery = true)
    void deleteCarByID(String id);

    @Query(value = "SELECT * FROM car where id = ?1", nativeQuery = true)
    Optional<Car> getCarByID(String id);

}
