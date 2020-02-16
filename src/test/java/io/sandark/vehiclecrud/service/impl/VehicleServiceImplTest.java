package io.sandark.vehiclecrud.service.impl;

import io.sandark.vehiclecrud.dao.VehicleRepository;
import io.sandark.vehiclecrud.entity.Vehicle;
import io.sandark.vehiclecrud.exceptions.NonUniqueVinException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static io.sandark.vehiclecrud.builder.VehicleBuilder.aVehicle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @InjectMocks
    private VehicleServiceImpl service;
    @Mock
    private VehicleRepository vehicleRepository;

    @Test
    void findAll() {
        List<Vehicle> list = Lists.newArrayList(new Vehicle());
        Mockito.when(vehicleRepository.findAll()).thenReturn(list);

        List<Vehicle> resultList = service.findAll();

        verify(vehicleRepository).findAll();
        assertThat(list).isEqualTo(resultList);
    }

    @Test
    void findById() {
        Optional<Vehicle> returnMock = Optional.of(new Vehicle());
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(returnMock);

        Optional<Vehicle> vehicle = service.findById(1L);

        verify(vehicleRepository).findById(1L);
        assertThat(vehicle.isPresent()).isTrue();
        assertThat(vehicle).isEqualTo(returnMock);
    }

    @Test
    void create() throws NonUniqueVinException {
        Vehicle vehicleToPersist = aVehicle().withVin("UNIQUEVIN0001").build();
        Vehicle persistedVehicle = aVehicle().withId(1L).withVin("UNIQUEVIN0001").build();

        when(vehicleRepository.findByVin(vehicleToPersist.getVin())).thenReturn(Optional.empty());
        when(vehicleRepository.save(vehicleToPersist)).thenReturn(persistedVehicle);

        Vehicle result = service.create(vehicleToPersist);

        verify(vehicleRepository).findByVin(vehicleToPersist.getVin());
        verify(vehicleRepository).save(vehicleToPersist);

        assertThat(result).isEqualTo(persistedVehicle);
        assertThat(result.getId()).isNotNull();
    }


    @Test
    void create_throwsExceptionIfNonUniqueVin() {
        Vehicle vehicleToPersist = aVehicle().withVin("UNIQUEVIN0001").build();

        when(vehicleRepository.findByVin(vehicleToPersist.getVin())).thenReturn(Optional.of(aVehicle().withId(1L).build()));

        NonUniqueVinException caughtException =
                assertThrows(NonUniqueVinException.class, () -> service.create(vehicleToPersist));

        verify(vehicleRepository).findByVin(vehicleToPersist.getVin());
        verify(vehicleRepository, never()).save(vehicleToPersist);

        assertThat(caughtException).hasMessage("VIN %s already exist, please use another one", vehicleToPersist.getVin());
    }

    @Test
    void delete() {
        Vehicle vehicle = aVehicle().withId(1L).build();

        service.delete(vehicle);

        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    void update() throws NonUniqueVinException {
        Vehicle vehicleToPersist = aVehicle().withId(1L).withVin("UNIQUEVIN0001").build();
        Vehicle persistedVehicle = aVehicle().withId(1L).withVin("UNIQUEVIN0001").build();

        when(vehicleRepository.findByVin(vehicleToPersist.getVin())).thenReturn(Optional.empty());
        when(vehicleRepository.save(vehicleToPersist)).thenReturn(persistedVehicle);

        Vehicle result = service.update(vehicleToPersist);

        verify(vehicleRepository).findByVin(vehicleToPersist.getVin());
        verify(vehicleRepository).save(vehicleToPersist);

        assertThat(result).isEqualTo(persistedVehicle);
        assertThat(result.getId()).isNotNull();
    }


    @Test
    void update_throwsExceptionIfNonUniqueVin() {
        Vehicle vehicleToPersist = aVehicle().withVin("UNIQUEVIN0001").build();

        when(vehicleRepository.findByVin(vehicleToPersist.getVin())).thenReturn(Optional.of(aVehicle().withId(1L).build()));

        NonUniqueVinException caughtException =
                assertThrows(NonUniqueVinException.class, () -> service.update(vehicleToPersist));

        verify(vehicleRepository).findByVin(vehicleToPersist.getVin());
        verify(vehicleRepository, never()).save(vehicleToPersist);

        assertThat(caughtException).hasMessage("VIN %s already exist, please use another one", vehicleToPersist.getVin());
    }

    @Test
    void update_noExceptionIfVinIsUnique() throws NonUniqueVinException {
        Vehicle vehicleToPersist = aVehicle().withId(1L).withVin("UNIQUEVIN0001").build();
        Vehicle persistedVehicle = aVehicle().withId(1L).withVin("UNIQUEVIN0001").build();

        when(vehicleRepository.findByVin(vehicleToPersist.getVin())).thenReturn(Optional.of(persistedVehicle));
        when(vehicleRepository.save(vehicleToPersist)).thenReturn(persistedVehicle);

        Vehicle result = service.update(vehicleToPersist);

        verify(vehicleRepository).findByVin(vehicleToPersist.getVin());
        verify(vehicleRepository).save(vehicleToPersist);

        assertThat(result).isEqualTo(persistedVehicle);
        assertThat(result.getId()).isNotNull();    }
}