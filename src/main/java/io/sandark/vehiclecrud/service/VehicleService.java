package io.sandark.vehiclecrud.service;

import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.NonUniqueVinException;

import java.util.List;
import java.util.Optional;

/**
 * Vehicle service is responsible for validation, business logic and persistence of Vehicle entity
 */
public interface VehicleService {

    List<Vehicle> findAll();

    Optional<Vehicle> findById(long id);

    /**
     * Creates vehicle from provided entity. If vehicle VIN already exists in DB
     * then NonUniqueVinException is thrown.
     * @param vehicleToCreate entity to persist
     * @return persisted entity with id
     */
    Vehicle create(Vehicle vehicleToCreate) throws NonUniqueVinException;

    void delete(Vehicle vehicle);

    /**
     * Updates vehicle from provided entity. If vehicle VIN already exists in DB
     * then NonUniqueVinException is thrown.
     * @param vehicleToCreate entity to persist
     * @return persisted entity with id
     */
    Vehicle update(Vehicle vehicle) throws NonUniqueVinException;
}
