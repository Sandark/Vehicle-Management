package io.sandark.vehiclecrud.controller;

import io.sandark.vehiclecrud.dao.VehicleRepository;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle findVehicleById(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle with id %d is not found.", id)));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle persistedVehicle = vehicleRepository.save(vehicle);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedVehicle.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistedVehicle);
    }

    @DeleteMapping("/vehicles/{id}")
    public Map<String, Boolean> deleteVehicle(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Vehicle with id %d is not found.", id)));

        vehicleRepository.delete(vehicle);

        return Collections.singletonMap("deleted", Boolean.TRUE);
    }
}
