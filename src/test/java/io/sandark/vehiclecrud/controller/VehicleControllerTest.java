package io.sandark.vehiclecrud.controller;

import io.sandark.vehiclecrud.dao.VehicleRepository;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;
    @Mock
    private VehicleRepository vehicleRepository;

    @Test
    void findAllVehicles() {
        vehicleController.findAllVehicles();

        verify(vehicleRepository).findAll();
    }

    @Test
    void findVehicleById() throws EntityNotFoundException {
        Vehicle expectedVehicle = new Vehicle();
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(expectedVehicle));

        Vehicle foundedVehicle = vehicleController.findVehicleById(vehicleId);

        verify(vehicleRepository).findById(vehicleId);
        assertThat(foundedVehicle).isEqualTo(expectedVehicle);
    }

    @Test
    void findVehicleById_throwsExceptionIfNotFound() {
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        EntityNotFoundException caughtException = assertThrows(EntityNotFoundException.class,
                () -> vehicleController.findVehicleById(vehicleId));

        assertThat(caughtException.getMessage()).isEqualTo("Vehicle with id %d is not found.", vehicleId);
    }

    @Test
    void createVehicle() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Vehicle persistedVehicle = new Vehicle();
        long vehicleId = 100L;
        persistedVehicle.setId(vehicleId);

        Vehicle toPersist = new Vehicle();

        Mockito.when(vehicleRepository.save(toPersist)).thenReturn(persistedVehicle);

        ResponseEntity<Vehicle> responseEntity = vehicleController.createVehicle(toPersist);

        verify(vehicleRepository).save(toPersist);
        assertThat(responseEntity.getBody()).isEqualTo(persistedVehicle);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/%d", vehicleId);
    }

    @Test
    void deleteVehicle() throws EntityNotFoundException {
        Vehicle expectedVehicle = new Vehicle();
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(expectedVehicle));

        Map<String, Boolean> response = vehicleController.deleteVehicle(vehicleId);

        verify(vehicleRepository).findById(vehicleId);
        verify(vehicleRepository).delete(expectedVehicle);

        assertThat(response.get("deleted")).isTrue();
    }

    @Test
    void deleteVehicle_throwsExceptionIfNotFound() {
        Vehicle expectedVehicle = new Vehicle();
        long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        EntityNotFoundException caughtException = assertThrows(EntityNotFoundException.class,
                () -> vehicleController.deleteVehicle(vehicleId));

        verify(vehicleRepository).findById(vehicleId);
        verify(vehicleRepository, never()).delete(any(Vehicle.class));

        assertThat(caughtException.getMessage()).isEqualTo("Vehicle with id %d is not found.", vehicleId);

    }
}