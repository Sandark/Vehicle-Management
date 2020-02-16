package io.sandark.vehiclecrud.controller;

import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.NonUniqueVinException;
import io.sandark.vehiclecrud.exceptions.RecordNotFoundException;
import io.sandark.vehiclecrud.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static io.sandark.vehiclecrud.builder.VehicleBuilder.aVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;
    @Mock
    private VehicleService vehicleService;

    @Test
    void findAllVehicles() {
        vehicleController.findAllVehicles();

        verify(vehicleService).findAll();
    }

    @Test
    void findVehicleById() throws RecordNotFoundException {
        Vehicle expectedVehicle = new Vehicle();
        long vehicleId = 1L;
        when(vehicleService.findById(vehicleId)).thenReturn(Optional.of(expectedVehicle));

        Vehicle foundedVehicle = vehicleController.findVehicleById(vehicleId);

        verify(vehicleService).findById(vehicleId);
        assertThat(foundedVehicle).isEqualTo(expectedVehicle);
    }

    @Test
    void findVehicleById_throwsExceptionIfNotFound() {
        long vehicleId = 1L;
        when(vehicleService.findById(vehicleId)).thenReturn(Optional.empty());

        RecordNotFoundException caughtException = assertThrows(RecordNotFoundException.class,
                () -> vehicleController.findVehicleById(vehicleId));

        assertThat(caughtException.getMessage()).isEqualTo("Vehicle with id %d is not found.", vehicleId);
    }

    @Test
    void createVehicle() throws NonUniqueVinException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        long vehicleId = 100L;
        Vehicle persistedVehicle = aVehicle().withId(vehicleId).build();
        Vehicle toPersist = aVehicle().withBrand("ZAZ").build();

        Mockito.when(vehicleService.create(toPersist)).thenReturn(persistedVehicle);

        ResponseEntity<Vehicle> responseEntity = vehicleController.createVehicle(toPersist);

        verify(vehicleService).create(toPersist);
        assertThat(responseEntity.getBody()).isEqualTo(persistedVehicle);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/%d", vehicleId);
    }

    @Test
    void deleteVehicle() throws RecordNotFoundException {
        Vehicle expectedVehicle = aVehicle().build();
        long vehicleId = 1L;
        when(vehicleService.findById(vehicleId)).thenReturn(Optional.of(expectedVehicle));

        Map<String, Boolean> response = vehicleController.deleteVehicle(vehicleId);

        verify(vehicleService).findById(vehicleId);
        verify(vehicleService).delete(expectedVehicle);

        assertThat(response.get("deleted")).isTrue();
    }

    @Test
    void deleteVehicle_throwsExceptionIfNotFound() {
        long vehicleId = 1L;
        when(vehicleService.findById(vehicleId)).thenReturn(Optional.empty());

        RecordNotFoundException caughtException = assertThrows(RecordNotFoundException.class,
                () -> vehicleController.deleteVehicle(vehicleId));

        verify(vehicleService).findById(vehicleId);
        verify(vehicleService, never()).delete(any(Vehicle.class));

        assertThat(caughtException.getMessage()).isEqualTo("Vehicle with id %d is not found.", vehicleId);

    }

    @Test
    void updateVehicle() throws RecordNotFoundException, NonUniqueVinException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long vehicleId = 100L;
        Vehicle persistedVehicle = aVehicle().withId(vehicleId).build();
        Vehicle toPersist = aVehicle().withBrand("VAZ").build();

        Mockito.when(vehicleService.findById(vehicleId)).thenReturn(Optional.of(new Vehicle()));
        Mockito.when(vehicleService.create(toPersist)).thenReturn(persistedVehicle);

        ResponseEntity<Vehicle> responseEntity = vehicleController.updateVehicle(vehicleId, toPersist);

        verify(vehicleService).findById(vehicleId);
        verify(vehicleService).create(toPersist);
        assertThat(responseEntity.getBody()).isEqualTo(persistedVehicle);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void updateVehicle_throwsExceptionIfNotFound() throws RecordNotFoundException, NonUniqueVinException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Long vehicleId = 100L;

        RecordNotFoundException caughtException = assertThrows(RecordNotFoundException.class,
                () -> vehicleController.updateVehicle(vehicleId, new Vehicle()));

        verify(vehicleService).findById(vehicleId);
        verify(vehicleService, never()).create(any(Vehicle.class));

        assertThat(caughtException.getMessage()).isEqualTo("Vehicle with id %d is not found.", vehicleId);
    }
}