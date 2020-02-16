package io.sandark.vehiclecrud.service.impl;

import io.sandark.vehiclecrud.dao.VehicleRepository;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.NonUniqueVinException;
import io.sandark.vehiclecrud.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final String NON_UNIQUE_VIN_MESSAGE = "VIN %s already exist, please use another one";
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle create(Vehicle vehicleToCreate) throws NonUniqueVinException {
        validate(vehicleToCreate);
        return vehicleRepository.save(vehicleToCreate);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) throws NonUniqueVinException {
        validate(vehicle);
        return vehicleRepository.save(vehicle);
    }

    private void validate(Vehicle vehicleToCreate) throws NonUniqueVinException {
        Optional<Vehicle> vehicleByVin = vehicleRepository.findByVin(vehicleToCreate.getVin());
        if (vehicleByVin.isPresent() && !Objects.equals(vehicleByVin.get().getId(), vehicleToCreate.getId())) {
            throw new NonUniqueVinException(String.format(NON_UNIQUE_VIN_MESSAGE, vehicleToCreate.getVin()));
        }
    }
}
