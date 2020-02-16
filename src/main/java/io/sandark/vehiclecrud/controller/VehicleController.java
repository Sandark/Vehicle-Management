package io.sandark.vehiclecrud.controller;

import io.sandark.vehiclecrud.dao.VehicleRepository;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.RecordNotFoundException;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicle")
@Api(value = "vehicle", tags = {"Vehicle REST"})
@SwaggerDefinition(tags = {
        @Tag(name = "Vehicle REST", description = "CRUD operations for handling vehicles")
})
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/list")
    @ApiOperation(value = "Retrieves the list of all existing vehicles", response = Vehicle.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves the vehicle record by id if exist", response = Vehicle.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved vehicle record"),
            @ApiResponse(code = 404, message = "The record is not found")
    })
    public Vehicle findVehicleById(@PathVariable(value = "id") Long id) throws RecordNotFoundException {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Vehicle with id %d is not found.", id)));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Creates new vehicle record with provided body", response = Vehicle.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New record is created")
    })
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle persistedVehicle = vehicleRepository.save(vehicle);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedVehicle.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistedVehicle);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Updates existing vehicle by provided id and updated vehicle body", response = Vehicle.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vehicle record is updated"),
            @ApiResponse(code = 404, message = "The record is not found")
    })
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable(value = "id") Long id,
                                                 @Valid @RequestBody Vehicle vehicle) throws RecordNotFoundException {
        vehicleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Vehicle with id %d is not found.", id)));

        vehicle.setId(id);
        Vehicle persistedVehicle = vehicleRepository.save(vehicle);

        return ResponseEntity.ok(persistedVehicle);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Deletes vehicle record by provided id", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The record is not found")
    })
    public Map<String, Boolean> deleteVehicle(@PathVariable(value = "id") Long id) throws RecordNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Vehicle with id %d is not found.", id)));

        vehicleRepository.delete(vehicle);

        return Collections.singletonMap("deleted", Boolean.TRUE);
    }
}
